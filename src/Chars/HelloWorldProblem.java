/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chars;

/**
 *
 * @author carlos
 */
import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;
import ec.vector.GeneVectorIndividual;

public class HelloWorldProblem extends Problem implements SimpleProblemForm {
    private char[] _expected = "Hello, world! Arus like it!".toCharArray();

    public void evaluate(final EvolutionState evolutionState, 
                                    final Individual individual, 
                                    final int subPopulation, 
                                    final int threadNum) {
        if (individual.evaluated)
            return;

        int fitnessValue = 0;

        GeneVectorIndividual charVectorIndividual = (GeneVectorIndividual) individual;
        long length = charVectorIndividual.size();
        for (int i = 0; i < length; i++) {
            CharVectorGene charVectorGene 
                    = (CharVectorGene) charVectorIndividual.genome[i];
            char actual = charVectorGene.getAllele();
            if (actual == _expected[i]) {
                fitnessValue += 1;
            }
        }

        SimpleFitness fitness 
                         = (SimpleFitness) charVectorIndividual.fitness;
        fitness.setFitness(evolutionState, fitnessValue, 
                fitnessValue == charVectorIndividual.genomeLength());

        charVectorIndividual.evaluated = true;
    }
}
