/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPProblem;
import ec.simple.SimpleProblemForm;
import static ec.gp.GPProblem.P_DATA;
import ec.util.Parameter;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

/**
 *
 * @author arus2
 */
public class JobsSchedulingProblem extends GPProblem implements SimpleProblemForm {
    
    final private double fitnessWeight = 0.1;
    final private double structuralErrorWeight = 1.0;
    final private double doublingTaskWeight = 1.0;
    final private double missingTaskWeight = 100.0;
    final private double scheduleErrorWeight = 1.0;
    
    @Override
    public void setup(final EvolutionState state,
        final Parameter base)
        {
        super.setup(state, base);
        
        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof TreeData))
            state.output.fatal("GPData class must subclass from " + TreeData.class,
                base.push(P_DATA), null);
        }
        
    @Override
    public void evaluate(final EvolutionState state, 
        final Individual ind, 
        final int subpopulation,
        final int threadnum)
        {
            
        if (!ind.evaluated)  // don't bother reevaluating
            {
            double fitness = 0.0;
            
            TreeData data = (TreeData)(this.input);

            GPIndividual GPInd = (GPIndividual)ind;
            GPNode root = GPInd.trees[0].child;
            
            //collect data
            root.eval(state, threadnum, data, stack, GPInd, this);
            
            
            
            //penality for number of each task
            //posibly need for adding rewarding for having tasks
            for(int i = 0; i < data.howManyTimesOccurs.length; ++i){
                if(data.howManyTimesOccurs[i] > 1){
                    fitness += (data.howManyTimesOccurs[i] - 1) * doublingTaskWeight;
                }else if(data.howManyTimesOccurs[i] < 1){
                    fitness += 1.0 * missingTaskWeight;
                }
            }
            
            //penality for tasks on wrong machines
            fitness += data.numberOfTasksOnWrongMachine * structuralErrorWeight;
            
            //penality for too many tasks on machine
            fitness += data.toManyChilds * structuralErrorWeight;
            
            //penality for machines without task
            fitness += data.machinesWithoutChilds * structuralErrorWeight;
            
            //penality for order of tasks in job
            
            
            //penality for overlapping tasks
            
            
            //Add makespan to fitness
            fitness += data.getMakespan() * fitnessWeight;
            
            //System.out.println(data.toString());
            

            //Assing calculated fitness
            ((LowerBetterFitness) ind.fitness).setFitness(state, fitness, false);
            //mark individual as evaluated
            ind.evaluated = true;
            //reset collect data in TreeData 
            data.reset();
            }
        }   
}
