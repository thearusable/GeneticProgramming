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
import arus.base.BaseData;
import arus.base.LowerBetterFitness;
import ec.gp.GPIndividual;
import ec.gp.GPTree;
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
        if (!(input instanceof BaseData))
            state.output.fatal("GPData class must subclass from " + BaseData.class,
                base.push(P_DATA), null);
        }
        
    @Override
    public void evaluate(final EvolutionState state, 
        final Individual ind, 
        final int subpopulation,
        final int threadnum)
        {
            //ind.evaluated = true;
        if (!ind.evaluated)  // don't bother reevaluating
            {
            //DoubleData data = (DoubleData)(this.input);
            GPIndividual GPInd = (GPIndividual) ind;
            GPTree tree = GPInd.trees[0];
            
            
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

            
            LowerBetterFitness fitness;
            fitness = (LowerBetterFitness) ind.fitness;
            
            fitness.setFitness(state, rand.nextDouble(), false);

            ind.evaluated = true;
            }
        }
    }
