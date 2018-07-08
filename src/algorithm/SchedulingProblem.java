/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import algorithm.dataRepresentation.SingleJob;
import algorithm.dataRepresentation.SingleProblem;
import algorithm.dataRepresentation.SingleTask;
import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPProblem;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author areks
 */
public class SchedulingProblem extends GPProblem implements SimpleProblemForm {

    // naprawic system checkpointow - obliczenia beda trwaly dlugo
    // zoptymalizowac ocene - usunac dodatkowe wartosci.
    // zoptymalizowac parametry
    
    private static final ArrayList< SingleProblem> problems = new ArrayList<>();

    //config variables
    private int fitnessType;
    private String DatasetName;
    private boolean useUpperBound;

    @Override
    public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state, base);

        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof TreeData)) {
            state.output.fatal("GPData class must subclass from " + TreeData.class, base.push(P_DATA), null);
        }

        //read path to png file from params
        fitnessType = state.parameters.getInt(base.push("fitnessType"), null, 1);
        DatasetName = state.parameters.getString(base.push("DatasetName"), null);
        useUpperBound = state.parameters.getBoolean(base.push("useUpperBound"), null, true);

        //parameters check
        if (fitnessType < 1 || fitnessType > 3) {
            throw new ExceptionInInitializerError("Parameter: eval.problem.fitnessType is missing or incorrect.");
        }
        if(DatasetName == null || DatasetName.isEmpty()){
            throw new ExceptionInInitializerError("Parameter: eval.problem.DatasetName is missing or incorrect.");
        }

        System.out.println("fitnessType: " + fitnessType);
        System.out.println("DatasetName: " + DatasetName);

        //load all problems
        File[] files = new File("src/data/" + DatasetName + "/").listFiles();
        for (int id = 0; id < files.length; id++) {
            if (files[id].isFile()) {
                SingleProblem sp = new SingleProblem();
                try {
                    sp.load(id, files[id].getPath(), useUpperBound, false);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    Logger.getLogger(SchedulingProblem.class.getName()).log(Level.SEVERE, null, ex);
                }
                problems.add(sp);
            }
        }
    }

    @Override
    public void evaluate(EvolutionState state, Individual ind, int i, int threadnum) {

        //skip already evaluated
        if (ind.evaluated == true) {
            return;
        }

        // base fitness value
        double fitness = 0.0;

        //get tree root
        GPIndividual GPInd = (GPIndividual) ind;
        GPNode root = GPInd.trees[0].child;

        //calculate for each problem
        for (SingleProblem problem : problems) {

            // storing calculated properties
            Priorities priorities = new Priorities(problem);

            //calculate priority
            calculatePriorities(priorities, ind, state, i);

            //search for longest time (makespan)
            int duration = calculateDuration(priorities);

            //save lowest duration for problem
            if (duration < problem.BEST_RESULT_SO_FAR) {
                System.out.println("MAKESPAN |" + problem.PROBLEM_NAME + "| " + problem.BEST_RESULT_SO_FAR + " -> " + duration);
                problem.BEST_RESULT_SO_FAR = duration;
            }

            switch (fitnessType) {
                case 1:
                    //calculate difference between best result from web
                    fitness += ((double) duration / problem.BEST_RESULT_FROM_WEB) - 1.0;
                    break;
                case 2:
                    fitness += (double) duration;
                    break;
                case 3:
                    fitness += ((double) duration / problem.BEST_RESULT_SO_FAR) - 1.0;
                    break;
                default:
                    break;
            }
        }

        switch (fitnessType) {
            case 1:
                // fitness -> % how much longer are durations than best from web
                fitness = (fitness * 100) / problems.size();
                //end when perfect fitness occurs - if miracle happend
                ((LowerBetterFitness) ind.fitness).setFitness(state, fitness, fitness == 0.0);
                break;
            case 2:
                fitness /= problems.size();
                ((LowerBetterFitness) ind.fitness).setFitness(state, fitness, false);
                break;
            case 3:
                // fitness -> % how much longer are durations than best from web
                fitness = (fitness * 100) / problems.size();
                ((LowerBetterFitness) ind.fitness).setFitness(state, fitness, false);
                break;
            default:
                break;
        }
        
        ind.evaluated = true;
    }

    private void calculatePriorities(Priorities priorities, Individual ind, EvolutionState state, int i) {
        //get tree root
        GPIndividual GPInd = (GPIndividual) ind;
        GPNode root = GPInd.trees[0].child;

        //calculate priority
        for (SingleJob job : priorities.problem.jobs) {
            for (int task = 0; task < job.size(); task++) {
                //make new TreeData
                TreeData treeData = new TreeData();
                treeData.set(job.get(task), job, priorities.problem);

                //evaluate tree
                root.eval(state, i, treeData, stack, GPInd, this);

                //save new priority
                priorities.setPriority(job.JOB_ID, task, treeData.value);
            }
        }
    }

    private int calculateDuration(Priorities priorities) {
        //helper arrays for keeping track on ending times
        ArrayList<Integer> machineEndingTime = new ArrayList<>(Collections.nCopies(priorities.problem.MACHINES_COUNT, 0));
        ArrayList<Integer> jobEndingTime = new ArrayList<>(Collections.nCopies(priorities.problem.jobs.size(), 0));

        while (priorities.isNotFinished()) {
            //get the best one
            SingleTask leader = priorities.getTaskWithGreatestPriority();
            // minus 1 because machines are numbered from 1
            Integer minMachineTime = machineEndingTime.get(leader.machineId - 1);
            Integer minJobTime = jobEndingTime.get(leader.jobId);
            //get minimal time when next task can be processed
            Integer timeAfterTask = Math.max(minMachineTime, minJobTime) + leader.duration;
            //set new ending times
            machineEndingTime.set(leader.machineId - 1, timeAfterTask);
            jobEndingTime.set(leader.jobId, timeAfterTask);
        }

        //search for longest time (makespan)
        return Math.max(Collections.max(machineEndingTime), Collections.max(jobEndingTime));
    }
}
