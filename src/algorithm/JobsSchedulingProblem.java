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
    
    final static private double makespanWeight = 0.1 / METADATA.TASKS_COUNT;
    
    final static private double doublingTaskWeight = 0.2 * METADATA.TASKS_COUNT;
    final static private double missingTaskWeight = 0.4 * METADATA.TASKS_COUNT;
    
    final static private double machineWithBadChildErrorWeight = 0.2 * METADATA.TASKS_COUNT;
    final static private double taskInWrongOrderErrorWeight = 0.2 * METADATA.TASKS_COUNT;
    final static private double ConnectorWithBadChildErrorWeight = 0.1 * METADATA.TASKS_COUNT;
    
    
    //ending calculations before max generations number will occur
    private static final int BestFitnessOccursToEndCalculations = 1;
    private static double lowestFitness = Integer.MAX_VALUE;
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
            
            //penality for number of each task
            for(int i = 0; i < data.OccursCounterPerTask.length; ++i){
                if(data.OccursCounterPerTask[i] > 1){
                    fitness += (data.OccursCounterPerTask[i] - 1) * doublingTaskWeight;
                }else if(data.OccursCounterPerTask[i] < 1){
                    fitness += missingTaskWeight;
                }
            }
            
            fitness += data.taskInWrongOrder * taskInWrongOrderErrorWeight;
            
            fitness += data.ConnectorWithBadChild * ConnectorWithBadChildErrorWeight;
            
            fitness += data.machineWithBadChild * machineWithBadChildErrorWeight;
            
            //adding makespan to fitness
            double onlyTreeFitness = fitness;
            int onlyMakespan = data.getMakespan();
            
            fitness += onlyMakespan * makespanWeight;
            
            //countin lowest makespan
            boolean ended = false;
            if(fitness < lowestFitness && onlyTreeFitness == 0.0){
                lowestFitness = fitness;
                BestFitnessOccurCount = 0;
                MainWindow.updateMinimumMakespan(onlyMakespan);
            }else if(lowestFitness == fitness && onlyTreeFitness == 0.0){
                BestFitnessOccurCount += 1;
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
    public void printData(GPIndividual ind){
        TreeData data = new TreeData();
        
        GPNode root = ind.trees[0].child;
        EvolutionState state = new EvolutionState();
        root.eval(state, 0, data, stack, ind, this);
        
        System.out.println(data.toString());
    }
}
