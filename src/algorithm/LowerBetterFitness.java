/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import ec.Fitness;
import ec.simple.SimpleFitness;

/**
 *
 * @author arus2
 */
public class LowerBetterFitness extends SimpleFitness {

    private static final long serialVersionUID = 1;
    
    @Override
    public boolean betterThan(Fitness _fitness) {
        return ((LowerBetterFitness)_fitness).fitness() > fitness();
    }
    
}
