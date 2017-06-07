/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPProblem;
import ec.simple.SimpleProblemForm;
import static ec.gp.GPProblem.P_DATA;
import ec.util.Parameter;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import window.MainWindow;

/**
 *
 * @author arus2
 */
public class JobsSchedulingProblem extends GPProblem implements SimpleProblemForm {
    
    final private double fitnessWeight = 0.1;
    final private double machinesWithoutTaskChildsErrorWeight = 1.0;
    final private double wrongMachineErrorWeight = 20.0;
    final private double toManyChildsErrorWeight = 5.0;
    final private double doublingTaskWeight = 1.2;
    final private double missingTaskWeight = 2.0;
    final private double scheduleErrorWeight = 1.0;
    final private double wrongChildOfDummyErrorWeight = 1.0;
    
    //ending calculations before max generations number will occur
    static private int BestFitnessOccursToEndCalculations = 1000;
    static private double lowestFitness = Integer.MAX_VALUE;
    static private int BestFitnessOccurCount;
    
    @Override
    public void setup(final EvolutionState state, final Parameter base){
        super.setup(state, base);
        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof TreeData)){
            state.output.fatal("GPData class must subclass from " + TreeData.class, base.push(P_DATA), null);      
        }
    }
        
    @Override
    public void evaluate(final EvolutionState state, 
        final Individual ind, 
        final int subpopulation,
        final int threadnum){
            
        if (!ind.evaluated)
            {
            double fitness = 0.0;

            TreeData data = (TreeData)(this.input);
            
            GPIndividual GPInd = (GPIndividual)ind;
            GPNode root = GPInd.trees[0].child;
            
            //collect data
            root.eval(state, threadnum, data, stack, GPInd, this);
            //System.out.print(".");
            //penality for number of each task
            for(int i = 0; i < data.howManyTimesOccurs.length; ++i){
                if(data.howManyTimesOccurs[i] > 1){
                    fitness += (data.howManyTimesOccurs[i] - 1) * doublingTaskWeight * METADATA.MAX_TASK_DURATION;
                }else if(data.howManyTimesOccurs[i] < 1){
                    fitness += missingTaskWeight * METADATA.MAX_TASK_DURATION;
                }
            }
            
            //penality for tasks on wrong machines
            fitness += data.numberOfTasksOnWrongMachine * wrongMachineErrorWeight;
            
            //penality for too many tasks on machine
            fitness += data.toManyChilds * toManyChildsErrorWeight;
            
            //penality for machines without task
            fitness += data.machinesWithoutChilds * machinesWithoutTaskChildsErrorWeight;
            
            fitness += data.wrongChildOfDummy * wrongChildOfDummyErrorWeight;
            
            //penality for order of tasks in job
            /*
            for( int i = 0; i < data.timesPerJob.length; ++i){
                int lastEndTime = 0;
                for(int j = 0; j < data.timesPerJob[i].length; ++j){
                    if(data.timesPerJob[i][j].startTime < lastEndTime){
                        fitness += 1.0 * scheduleErrorWeight;
                    }
                    lastEndTime = data.timesPerJob[i][j].endTime;
                }
            }*/
            
            //penality for overlapping tasks on machine
            
            double onlyTreeFitness = fitness;
            int onlyMakespan = data.getMakespan();
            //Add makespan to fitness
            fitness += onlyMakespan * fitnessWeight;
            
            //System.out.println(data.toString());
            

            
            
            
            //countin lowest makespan
            boolean ended = false;
            if(onlyMakespan < lowestFitness && onlyTreeFitness == 0.0){
                System.out.println("New lowest makespan " + onlyMakespan);
                lowestFitness = onlyMakespan;
                BestFitnessOccurCount = 0;
                MainWindow.updateMinimumMakespan(onlyMakespan);
                MainWindow.hitsReset();
            }else if(lowestFitness == data.getMakespan() && onlyTreeFitness == 0.0){
                BestFitnessOccurCount += 1;
                MainWindow.hit();
            }else if(onlyMakespan < lowestFitness){
                MainWindow.updateTreeFitness(onlyTreeFitness);
            }
            
            if(BestFitnessOccurCount >= BestFitnessOccursToEndCalculations){
                ended = true;
            }
            
            //Assing calculated fitness
            ((LowerBetterFitness) ind.fitness).setFitness(state, fitness, ended);
            //mark individual as evaluated
            ind.evaluated = true;
            //reset collect data in TreeData 
            data.reset();
            }
        }
    
    //get makespan of individual
    public int getMakespan(GPIndividual ind){
        TreeData data = new TreeData();
        
        GPNode root = ind.trees[0].child;
        EvolutionState state = new EvolutionState();
        root.eval(state, 0, data, stack, ind, this);
        
        return data.getMakespan();
    }
}
