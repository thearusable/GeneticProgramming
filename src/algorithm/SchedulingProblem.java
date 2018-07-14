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
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author areks
 */
public class SchedulingProblem extends GPProblem implements SimpleProblemForm {

    // naprawic system checkpointow - obliczenia beda trwaly dlugo (pewnie porpawic czas trawania w statystykach)
    // zoptymalizowac ocene - usunac dodatkowe wartosci.
    // zoptymalizowac parametry
    // (??) poprawic 3 typ oceny

    // wykres boxowy z zestawieniem wszystkich wyników(wyuczone + losowe)(w matlabie -> boxplot)
    // przebadac wplyw parametrow na wyniki.
    // CEL - przygotowac tabelki i wykres z otrzymanymi danymi

    private static final long serialVersionUID = 1;
    
    private static final ArrayList< SingleProblem> learningProblems = new ArrayList<>();
    private static final ArrayList< SingleProblem> crossValidationProblems = new ArrayList<>();

    //config variables
    private int fitnessType;
    private int amountOfRandoms;
    private boolean useUpperBound;
    private String learningDatasets;
    private String crossValidationDatasets;

    @Override
    public void setup(final EvolutionState state, final Parameter base) {
        super.setup(state, base);

        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof TreeData)) {
            state.output.fatal("GPData class must subclass from " + TreeData.class, base.push(P_DATA), null);
        }

        //read path to png file from params
        fitnessType = state.parameters.getInt(base.push("fitnessType"), null, 1);
        useUpperBound = state.parameters.getBoolean(base.push("useUpperBound"), null, true);
        learningDatasets = state.parameters.getString(base.push("learningDatasets"), null);
        crossValidationDatasets = state.parameters.getString(base.push("crossValidationDatasets"), null);
        amountOfRandoms = state.parameters.getInt(base.push("amountOfRandoms"), null, 100000);

        //parameters check
        if (fitnessType < 1 || fitnessType > 3) {
            throw new ExceptionInInitializerError("Parameter: eval.problem.fitnessType is missing or incorrect.");
        }
        if (learningDatasets == null || learningDatasets.isEmpty()) {
            throw new ExceptionInInitializerError("Parameter: eval.problem.learningDatasets is missing or incorrect.");
        }
        if (crossValidationDatasets == null || crossValidationDatasets.isEmpty()) {
            throw new ExceptionInInitializerError("Parameter: eval.problem.crossValidationDatasets is missing or incorrect.");
        }
        if(amountOfRandoms < 0)
        {
            throw new ExceptionInInitializerError("Parameter: eval.problem.amountOfRandoms is missing or incorrect");
        }

        //load all problems
        System.out.print("Learning datasets: ");
        loadDatasets(learningDatasets, learningProblems, useUpperBound);
        System.out.print("Cross Validation datasets: ");
        loadDatasets(crossValidationDatasets, crossValidationProblems, useUpperBound);
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
        for (SingleProblem problem : learningProblems) {
            fitness += calcFitnessForSingleProblem(problem, state, ind);
        }

        fitness /= learningProblems.size();

        ((LowerBetterFitness) ind.fitness).setFitness(state, fitness, false);
        ind.evaluated = true;
    }
    
    public void createStatistics(GPIndividual leader, final EvolutionState state) {
        System.out.println("----- STATISTICS -----");

        System.out.println("--- Learning Problems ---");
        double sumLearningFitness = 0.0;
        for (SingleProblem problem : learningProblems) {
            sumLearningFitness += calcStatisticsForSingleProblem(problem, leader, state);
        }

        System.out.println("--- Cross Validation Problems ---");
        double sumCrossFitness = 0.0;
        for (SingleProblem problem : crossValidationProblems) {
            sumCrossFitness += calcStatisticsForSingleProblem(problem, leader, state);
        }

        System.out.println("--- Summary ---");
        System.out.println("Average Learned Fitness in Learning Problems: \n" + sumLearningFitness / learningProblems.size());
        System.out.println("Average Learned Fitness in Cross Validation Problems: \n" + sumCrossFitness / crossValidationProblems.size());
    }   

    private double calcStatisticsForSingleProblem(SingleProblem problem, GPIndividual leader, final EvolutionState state) {
        // fitness value for learned resolution
        double learned = calcFitnessForSingleProblem(problem, state, leader);
        System.out.println("--- Problem " + problem.PROBLEM_NAME + ":\nLearned: \t" + learned);

        SingleRandomStatistics stats = new SingleRandomStatistics(amountOfRandoms);

        for (int i = 0; i < amountOfRandoms; i++) {
            double duration = calcFitnessForRandom(problem);
            stats.add(duration);
        }

        stats.print();
        
        return learned;
    }
    
    private double calcFitnessForSingleProblem(SingleProblem problem, EvolutionState state, Individual ind) {

        // storing calculated properties
        Priorities priorities = new Priorities(problem);

        //calculate priority
        int threadId = (int) Thread.currentThread().getId();
        calculatePriorities(priorities, ind, state, threadId);

        //search for longest time (makespan)
        int duration = calculateDuration(priorities);

        //save lowest duration for problem
        if (duration < problem.BEST_RESULT_SO_FAR) {
            problem.BEST_RESULT_SO_FAR = duration;
        }

        switch (fitnessType) {
            case 1:
                // calculate difference between best result from web in %
                return (((double) duration / problem.BEST_RESULT_FROM_WEB) - 1.0) * 100.0;
            case 2:
                // just return duration
                return (double) duration;
            case 3:
                // calculate difference between best result so ofar and current duration
                return duration - problem.BEST_RESULT_SO_FAR;
        }

        return 0.0;
    }

    private double calcFitnessForRandom(SingleProblem problem) {
        // storing calculated properties
        Priorities priorities = new Priorities(problem);

        //search for longest time (makespan)
        int duration = calculateDurationForRandom(priorities);

        switch (fitnessType) {
            case 1:
                // calculate difference between best result from web in %
                return (((double) duration / problem.BEST_RESULT_FROM_WEB) - 1.0) * 100.0;
            case 2:
                // just return duration
                return (double) duration;
            case 3:
                // calculate difference between best result so ofar and current duration
                return duration - problem.BEST_RESULT_SO_FAR;
        }

        return 0.0;
    }

    

    private void loadDatasets(String str, ArrayList< SingleProblem> problems, boolean upper) {
        // remove spaces and get datasets
        str = str.replaceAll(" ", "");
        String[] datasets = str.split(",");
        System.out.println(Arrays.toString(datasets));

        // load files
        for (String dataset : datasets) {
            File[] files = new File("src/data/" + dataset + "/").listFiles();
            for (int id = 0; id < files.length; id++) {
                if (files[id].isFile()) {
                    SingleProblem sp = new SingleProblem();
                    try {
                        sp.load(id, files[id].getPath(), upper, false);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                        Logger.getLogger(SchedulingProblem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    problems.add(sp);
                }
            }
        }
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

    private int calculateDurationForRandom(Priorities priorities) {
        //helper arrays for keeping track on ending times
        ArrayList<Integer> machineEndingTime = new ArrayList<>(Collections.nCopies(priorities.problem.MACHINES_COUNT, 0));
        ArrayList<Integer> jobEndingTime = new ArrayList<>(Collections.nCopies(priorities.problem.jobs.size(), 0));

        while (priorities.isNotFinished()) {
            //get the best one
            SingleTask leader = priorities.getRandomWaitingTask();
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
