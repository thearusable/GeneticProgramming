/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JobsScheduling;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.simple.SimpleProblemForm;
import ec.app.tutorial4.DoubleData;
import static ec.gp.GPProblem.P_DATA;
import ec.gp.koza.KozaFitness;
import ec.util.Parameter;

/**
 *
 * @author arus2
 */
public class JobsSchedulingProblem extends GPProblem implements SimpleProblemForm {

    public double currentX;
    public double currentY;
    
    public void setup(final EvolutionState state,
        final Parameter base)
        {
        super.setup(state, base);
        
        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof JobsData))
            state.output.fatal("GPData class must subclass from " + JobsData.class,
                base.push(P_DATA), null);
        }
        
    public void evaluate(final EvolutionState state, 
        final Individual ind, 
        final int subpopulation,
        final int threadnum)
        {
            
        if (!ind.evaluated)  // don't bother reevaluating
            {
            DoubleData input = (DoubleData)(this.input);
        
            int hits = 0;
            double sum = 0.0;
            double expectedResult;
            double result;
            for (int y=0;y<10;y++)
                {
                currentX = state.random[threadnum].nextDouble();
                currentY = state.random[threadnum].nextDouble();
                expectedResult = currentX*currentX*currentX*currentY* currentY + currentX*currentY + currentY - currentX;
                ((GPIndividual)ind).trees[0].child.eval(
                    state,threadnum,input,stack,((GPIndividual)ind),this);

                result = Math.abs(expectedResult - input.x);
                if (result <= 0.01) hits++;
                sum += result;                  
                }

            // the fitness better be KozaFitness!
            KozaFitness f = ((KozaFitness)ind.fitness);
            f.setStandardizedFitness(state, sum);
            f.hits = hits;
            ind.evaluated = true;
            }
        }
    }
