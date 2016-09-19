/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JobsScheduling;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPProblem;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;

/**
 *
 * @author arus2
 */
public class JobsSchedulingProblem extends GPProblem implements SimpleProblemForm {

    double fitness;
    
    @Override
    public void evaluate(EvolutionState state, Individual ind, int subpopulation, int threadnum) {
        if(!ind.evaluated){
            fitness = 0.0;
            nodeCalculate(((GPIndividual) ind).trees[0].child, state);

            SimpleFitness f = ((SimpleFitness) ind.fitness);
            f.setFitness(state, fitness, false);
            ind.evaluated = true;
        }
    }
    
    void nodeCalculate(GPNode p, EvolutionState state)
        {
        int pval = ((JobTreeNode) p).JobID;
        for (int i = 0; i < p.children.length; i++)
            {
            GPNode c = p.children[i];
            int cval = ((JobTreeNode) c).JobID;
            if (pval < cval)
                {
                // direct fitness contribution
                fitness += 1;
                nodeCalculate(c, state);
                }
            else if (pval == cval)
                {
                // neutral-left-walk
                boolean found = false;
                while (c.children.length > 0 && cval == pval && !found)
                    {
                    c = c.children[0];
                    cval = ((JobTreeNode) c).JobID;
                    if (pval < cval)
                        {
                        found = true;
                        }
                    }
                if (found)
                    {
                    fitness += 1;
                    nodeCalculate(c, state);
                    }
                }
            }
        }
    }
