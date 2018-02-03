/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPProblem;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author areks
 */
public class SchedulingProblem extends GPProblem implements SimpleProblemForm {
   
    //PROBLEMODAJKA:
    // - wszystkie problemy wczytane
    // - dla kazdego taska wyznaczony czas wejscia po ktorym moze zostac wykonany
    // - taski moga byc brane w losowej kolejnosci (??)

    //DRZEWO:(osobnik)
    // - "funkcja" ktora jest w stanie wyliczyc priorytet
    //      - get czasu rozpoczecia
    //      - get czasu trwania taska
    //      # a + b 
    //      # a - b
    //      # a / b
    //      # a * b
    //      # if a < b 
    //      # if a > b 
    //      # constant
    // - jedna "funkcja" na wszystkie przypadki (wzglednie dobre dla wszystkich)
    
    //OCENA ROZWIAZANIA:
    //  - dlugosc czasu jaki zajmie cale zadanie 
    //  - przypisanie dla kazdego taska wyliczonej wartosci(w jednym przebiegu) nastepnie wykonywanie od tego z najwyzsza wartoscia
    
    //OBLICZENIA:
    // - checkpointy
    // - jeden plik params
    // - 16 watkow
    // - 16 GB RAM
    
    //read weights from params file
    //makespanWeight = state.parameters.getDouble(new Parameter("makespan"), null);
    
    @Override
    public void setup(final EvolutionState state, final Parameter base){
        super.setup(state, base);
        
        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof TreeData)){
            state.output.fatal("GPData class must subclass from " + TreeData.class, base.push(P_DATA), null);      
        }
    }
    
    @Override
    public void evaluate(EvolutionState state, Individual ind, int i, int threadnum) {
        
        if(ind.evaluated == true) return;
        
        double fitness = 0.0;
        GPIndividual GPInd = (GPIndividual)ind;
        GPNode root = GPInd.trees[0].child;
        
        
        //calculate for each problem
        for(SingleProblemData data : Database.problems)
        {
            //calculate priority and save in task
            for(int job = 0; job < data.JOBS_COUNT; job++)
            {
                for(int task = 0; task < data.TASKS_PER_JOB; task++)
                {
                    TreeData treeData = new TreeData();
                    treeData.task = data.getTask(job, task);
                    treeData.data = data;
                    
                    root.eval(state, i, treeData, stack, GPInd, this);
                    
                    data.getTask(job, task).calculatedPriority = treeData.value;
                    
                    //System.out.println(data.getTask(job, task).ID + " " + data.getTask(job, task).jobID);
                }
            }
            
            //when machine will be free
            ArrayList<Integer> machineEndingTime = new ArrayList<>();
            //when job will be completed
            ArrayList<Integer> jobEndingTime = new ArrayList<>();
            
            //construct scheme of task execution
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
                        //TODO return 1 if rhs should be before lhs 
                        //     return -1 if lhs should be before rhs
                        //     return 0 otherwise
                        if(rhs.calculatedPriority > lhs.calculatedPriority) return 1;
                        else return -1;
                    }
                });
                
                for(TaskData t : currentTasks)
                {
                    System.out.println(t.calculatedPriority);
                }
                
                //dwu wymiarowa tablica taskow
                //wybranie jednego z pierwszej kolumny 
                // uzupelnienie pierwszej kolumny kolejnym z danej pracy
                // i tak do konca
                
                //symulowanie czasu wejscia powinno byc przy takim zalozeniu proste
                
            }
            //add time to fitness
        }
        
        fitness = fitness / Database.problems.size();
        ((LowerBetterFitness) ind.fitness).setFitness(state, fitness, false);
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
    
    private void printErrorsWeights(){
        System.out.println("NOT IMPLEMENTED");
    }
}
