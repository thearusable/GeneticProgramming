/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import algorithm.dataRepresentation.SingleJob;
import algorithm.dataRepresentation.SingleProblem;
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
        
        //calculate for each problem
        for(SingleProblem problem : problems)
        {
            
            System.out.println("Start of calculation:");
            problem.print();
            
            //calculate priority and save in task
            //for(int job = 0; job < problem.jobs.size(); job++)
            //{
                for(SingleJob job : problem.jobs)
                {
                    
                //przeniesc ocenianie do problemu
                for(int task = 0; task <  job.TASK_COUNT; task++)
                {
                    TreeData treeData = new TreeData();
                    treeData.task = .getTask(job, task);
                    treeData.data = data;
                    
                    root.eval(state, i, treeData, stack, GPInd, this);
                    
                    data.getTask(job, task).calculatedPriority = treeData.value;
                }
                }
            //}
            /*
            
            System.out.println("After calculating priority:");
            data.print();
            
            //when machine will be free
            ArrayList<Integer> machineEndingTime = new ArrayList<>();
            while(machineEndingTime.size() < data.MACHINES_COUNT) machineEndingTime.add(0);
            
            //when job will be completed
            ArrayList<Integer> jobEndingTime = new ArrayList<>();
            while(jobEndingTime.size() < data.JOBS_COUNT) jobEndingTime.add(0);
            
            System.out.println("machineEndingTime: " + machineEndingTime);
            System.out.println("jobEndingTime: " + jobEndingTime);
            */
            /*
            //construct scheme of task execution - TOUPGRADE
            for(int task = 0; task < data.TASKS_PER_JOB; task++)
            {
                //per job
                ArrayList<TaskData> currentTasks = new ArrayList<>();
                for(int job = 0; job < data.JOBS_COUNT; job++)
                {
                    //z gory do dolu
                    currentTasks.add(data.getTask(job, task));
                }
                
                //sortowanie
                Collections.sort(currentTasks,new Comparator<TaskData>(){
                    @Override
                    public int compare(final TaskData lhs,TaskData rhs) {                       
                        if(rhs.calculatedPriority > lhs.calculatedPriority) return 1;
                        else return -1;
                    }
                });
                
                //adding times
                for(TaskData t : currentTasks)
                {
                    machineEndingTime.set(t.requiredMachineID, machineEndingTime.get(t.requiredMachineID) + t.duration);
                    jobEndingTime.set(t.jobID, jobEndingTime.get(t.jobID) + t.duration);
                }                
            }
            
            //find longest duration
            int maxDuration = 0;
            for(Integer ele : machineEndingTime)
            {
                if(ele > maxDuration) maxDuration = ele;
            }
            for(Integer ele : jobEndingTime)
            {
                if(ele > maxDuration) maxDuration = ele;
            }
            
            fitness += (maxDuration - data.BEST_RESULT_FROM_WEB)/ data.BEST_RESULT_FROM_WEB; 
            
            */
        }
        
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
