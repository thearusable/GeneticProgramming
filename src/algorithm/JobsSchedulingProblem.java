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
    
    final static private double makespanWeight = 1.0 / METADATA.TASKS_COUNT;
    final static private double taskWithBadParentErrorWeight = 1.0 * METADATA.TASKS_COUNT;
    final static private double taskOnWrongMachineErrorWeight = 2.0 * METADATA.TASKS_COUNT;
    final static private double machineWithBadParentErrorWeight = 5.0 * METADATA.TASKS_COUNT;
    final static private double doublingTaskWeight = 1.2 * METADATA.TASKS_COUNT;
    final static private double missingTaskWeight = 5.0 * METADATA.TASKS_COUNT;
    final static private double taskInWrongOrderErrorWeight = 1.0 * METADATA.TASKS_COUNT;
    final static private double taskWithBadTimeErrorWeight = 0.4 * METADATA.TASKS_COUNT;
    
    //ending calculations before max generations number will occur
    private static final int BestFitnessOccursToEndCalculations = 1;
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
            
            //System.out.println(data.toString());
            
            //penality for number of each task
            for(int i = 0; i < data.OccursCounterPerTask.length; ++i){
                if(data.OccursCounterPerTask[i] > 1){
                    fitness += (data.OccursCounterPerTask[i] - 1) * doublingTaskWeight;
                }else if(data.OccursCounterPerTask[i] < 1){
                    fitness += missingTaskWeight;
                }
            }
            
            fitness += data.machineWithBadParent * machineWithBadParentErrorWeight;
            
            fitness += data.taskInWrongOrder * taskInWrongOrderErrorWeight;
            
            fitness += data.taskOnWrongMachine * taskOnWrongMachineErrorWeight;
            
            fitness += data.taskWithBadParent * taskWithBadParentErrorWeight;
            
            fitness += data.taskWithBadTime * taskWithBadTimeErrorWeight;
            
            
            //adding makespan to fitness
            double onlyTreeFitness = fitness;
            int onlyMakespan = data.getMakespan();
            
            fitness += onlyMakespan * makespanWeight;
            
            //countin lowest makespan
            boolean ended = false;
            if(onlyMakespan < lowestFitness && onlyTreeFitness == 0.0){
                lowestFitness = onlyMakespan;
                BestFitnessOccurCount = 0;
                MainWindow.updateMinimumMakespan(onlyMakespan);
                MainWindow.hitsReset();
            }else if(lowestFitness == onlyMakespan && onlyTreeFitness == 0.0){
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
