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
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author areks
 */
public class SchedulingProblem extends GPProblem implements SimpleProblemForm {
   
    //PROBLEMODAJKA:
    // # wszystkie problemy wczytane
    // - dla kazdego taska wyznaczony czas wejscia po ktorym moze zostac wykonany
    
    //OCENA ROZWIAZANIA:
    //  - stosunek uzyskanego rozwiazania do najlepszego rozwiazania wczytanego z pliku
    //dwu wymiarowa tablica taskow
    //wybranie jednego z pierwszej kolumny 
    // uzupelnienie pierwszej kolumny kolejnym z danej pracy
    //symulowanie czasu wejscia powinno byc przy takim zalozeniu proste
    
    //read weights from params file
    //makespanWeight = state.parameters.getDouble(new Parameter("makespan"), null);
    
    //private static final ArrayList < SingleProblemData > problems = new ArrayList<>();
    
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
        for(File file : files){
            if(file.isFile()){
                SingleProblem sp = new SingleProblem();
                try {
                    sp.load(file.getPath(), false);
                } catch (IOException ex) {
                    Logger.getLogger(SchedulingProblem.class.getName()).log(Level.SEVERE, null, ex);
                }
                problems.add(sp);
            }
        }
    }
    
    @Override
    public void evaluate(EvolutionState state, Individual ind, int i, int threadnum) {
        
        if(ind.evaluated == true) return;
        
        double fitness = 0.0;
        GPIndividual GPInd = (GPIndividual)ind;
        GPNode root = GPInd.trees[0].child;
        
        //calculate for each problem - make a copy ?
        for(SingleProblem problem : problems)
        {
            //calculate priority and save in task
            for(SingleJob job : problem.jobs)
            {
                for(int index = 0; index < job.size(); index++)
                {
                    TreeData treeData = new TreeData();
                    treeData.task = job.get(index);
                    // anything more ??
                        
                    root.eval(state, i, treeData, stack, GPInd, this);
                    
                    job.set(index, treeData.task); 
                }
            }
            
            System.out.println("After evaluation:");
            problem.print();
                
            //when machine will be free
            ArrayList<Integer> machineEndingTime = new ArrayList<>();
            while(machineEndingTime.size() < problem.MACHINES_COUNT) machineEndingTime.add(0);
            
            //when job will be completed
            ArrayList<Integer> jobEndingTime = new ArrayList<>();
            while(jobEndingTime.size() < problem.jobs.size()) jobEndingTime.add(0);
            
            while(problem.isNotFinished())
            {
                SingleTask leader = problem.popWithGreatestPriority();
                
                Integer minStartTime = machineEndingTime.get(leader.machineId);
                
                if(minStartTime < jobEndingTime.get(leader.jobId))
                {
                    minStartTime = jobEndingTime.get(leader.jobId);
                }
                
                minStartTime += leader.duration;
                
                machineEndingTime.set(leader.machineId, minStartTime);
                jobEndingTime.set(leader.jobId, minStartTime);
            }
            
            //print ending times
            System.out.println("MachineEndingTimes: " + machineEndingTime);
            System.out.println("JobEndingTimes: " + jobEndingTime);
            
            //search for longest time
            int duration = Integer.MIN_VALUE;
            for(int machine = 0; machine < machineEndingTime.size(); i++)
            {
                if(machineEndingTime.get(machine) > duration)
                {
                    duration = machineEndingTime.get(machine);
                }
            }
            for(int job = 0; job < jobEndingTime.size(); job++)
            {
                if(jobEndingTime.get(job) > duration)
                {
                    duration = jobEndingTime.get(job);
                }
            }
            
            //calculate difference between best result from web
            fitness += duration - problem.BEST_RESULT_FROM_WEB;
        }
        
        //average difference
        fitness = fitness / problems.size();
        ((LowerBetterFitness) ind.fitness).setFitness(state, fitness, true);
        ind.evaluated = true;
    }   
    
    //get makespan of individual
    public void printData(GPIndividual ind){
        TreeData data = new TreeData();
        
        GPNode root = ind.trees[0].child;
        EvolutionState state = new EvolutionState();
        root.eval(state, 0, data, stack, ind, this);
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
