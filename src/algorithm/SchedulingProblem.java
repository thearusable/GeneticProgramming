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
   
    //PROBLEMODAJKA:
    // ?? dla kazdego taska wyznaczony czas wejscia po ktorym moze zostac wykonany 
    // zmienic sposob obliczania fitness, % roznicy ???
    
    private static final ArrayList < SingleProblem > problems = new ArrayList<>();
    
    @Override
    public void setup(final EvolutionState state, final Parameter base){
        super.setup(state, base);
        
        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof TreeData)){
            state.output.fatal("GPData class must subclass from " + TreeData.class, base.push(P_DATA), null);      
        }
        
        //load all problems
        File[] files = new File("src/data/").listFiles();
        for(int id = 0; id < files.length ;id++){
            if(files[id].isFile()){
                SingleProblem sp = new SingleProblem();
                try {
                    sp.load(id, files[id].getPath(), false);
                } catch (IOException ex) {
                    Logger.getLogger(SchedulingProblem.class.getName()).log(Level.SEVERE, null, ex);
                }
                problems.add(sp);
            }
        }
    }
    
    @Override
    public void evaluate(EvolutionState state, Individual ind, int i, int threadnum) {
        
        //skip evaluated ones
        if(ind.evaluated == true) return;
        
        double fitness = 0.0;
        
        //get tree root
        GPIndividual GPInd = (GPIndividual)ind;
        GPNode root = GPInd.trees[0].child;
        
        //make a copy for safety reasons - to upgrade later
        ArrayList < SingleProblem > myProblems = new ArrayList<>();
        for(SingleProblem problem : problems)
        {
            try {
                myProblems.add(problem.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(SchedulingProblem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //calculate for each problem
        for(SingleProblem problem : myProblems)
        {            
            //calculate priority and save in task
            for(SingleJob job : problem.jobs)
            {
                for(int index = 0; index < job.size(); index++)
                {
                    //make new TreeData
                    TreeData treeData = new TreeData();
                    treeData.set(job.get(index), job, problem);
                        
                    //evaluate tree
                    root.eval(state, i, treeData, stack, GPInd, this);
                    
                    //update task with new priority
                    treeData.task.calculatedPriority = treeData.value;
                    job.set(index, treeData.task); 
                }
            }
                
            //when machine will be free
            ArrayList<Integer> machineEndingTime = new ArrayList<>();
            while(machineEndingTime.size() < problem.MACHINES_COUNT) machineEndingTime.add(0);
            
            //when job will be completed
            ArrayList<Integer> jobEndingTime = new ArrayList<>();
            while(jobEndingTime.size() < problem.jobs.size()) jobEndingTime.add(0);
            
            //keep picking best ones until jobs become empty
            while(problem.isNotFinished())
            {
                //get the best one
                SingleTask leader = problem.popWithGreatestPriority();
                
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
            int duration = Math.max(Collections.max(machineEndingTime), Collections.max(jobEndingTime));
            
            //calculate difference between best result from web
            fitness += ((double)duration / problem.BEST_RESULT_FROM_WEB) - 1.0;
        }
        
        // fitness -> % how much longer are durations than best from web
        fitness = (fitness * 100) / problems.size();
        
        //end when perfect fitness occurs - if miracle happend
        ((LowerBetterFitness) ind.fitness).setFitness(state, fitness, fitness == 0.0);
        ind.evaluated = true;
    }   
    
    //get makespan of individual
    public void printData(GPIndividual ind){
        //TreeData data = new TreeData();
        
        //GPNode root = ind.trees[0].child;
        //EvolutionState state = new EvolutionState();
        //root.eval(state, 0, data, stack, ind, this);
/*
        if(data.isValidtree()){
            MainWindow.updateMinimumMakespan(Integer.toString(data.getMakespan()));
        }else{
            MainWindow.updateMinimumMakespan("Not Founded");
            System.out.println(data.toString());
        }
        */
        //OrderChart.buildDataset(data.order);
    }
}
