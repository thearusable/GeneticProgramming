/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JobsScheduling;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPProblem;
import ec.simple.SimpleProblemForm;
import static ec.gp.GPProblem.P_DATA;
import ec.util.Parameter;
import arus.base.TreeData;
import arus.base.LowerBetterFitness;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPTree;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author arus2
 */
public class JobsSchedulingProblem extends GPProblem implements SimpleProblemForm {
    
    Random rand = new Random();
    
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
            double fitness = Math.random();
            boolean isIdeal = false;
            
            TreeData data = (TreeData)(this.input);

            GPIndividual GPInd = (GPIndividual)ind;
            GPNode root = GPInd.trees[0].child;
            
            root.eval(state, threadnum, input, stack, GPInd, this);
            
            //System.out.printf(Arrays.toString(data.TaskCount));
            
            for(int i = 0; i < data.TaskCount.length; i++){
                if(data.TaskCount[i] == 1) fitness += 1;
                /*
                if(data.TaskCount[i] > 1){
                   fitness += data.TaskCount[i] - 1;
                }else if(data.TaskCount[i] == 0){
                    fitness += 1;
                }
                */
            }
            
            
            //int hits = 0;
            //double sum = 0.0;
            //double expectedResult;
            //double result;
            //for (int y=0;y<10;y++)
                //{
                //currentX = state.random[threadnum].nextDouble();
                //currentY = state.random[threadnum].nextDouble();
                //expectedResult = currentX*currentX*currentX*currentY* currentY + currentX*currentY + currentY - currentX;
                //((GPIndividual)ind).trees[0].child.eval(
                //    state,threadnum,input,stack,((GPIndividual)ind),this);

                //result = Math.abs(expectedResult - input.x);
                //if (result <= 0.01) hits++;
                //sum += result;                  
                //}

            // the fitness better be KozaFitness!
            //KozaFitness f = ((KozaFitness)ind.fitness);
            //f.setStandardizedFitness(state, sum);
            //f.hits = hits;
            
            if(fitness == 0.f) isIdeal = true;
            
            LowerBetterFitness fitnessObject;
            fitnessObject = (LowerBetterFitness) ind.fitness;
            
            fitnessObject.setFitness(state, fitness, isIdeal);

            ind.evaluated = true;
            }
        }
    }
