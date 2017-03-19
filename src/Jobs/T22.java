/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jobs;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

/**
 *
 * @author arus
 */
public class T22 extends TaskNode {

    @Override
    public int getTaskDuration() {
        return 123;
    }

    @Override
    public int getJobID() {
        return 2;
    }

    @Override
    public String toString() {
        return "T_2[123]";
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        
    }
    
}
