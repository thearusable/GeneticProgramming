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
import ec.gp.GPNode;

/**
 *
 * @author arus2
 */
public class TaskNode extends GPNode {

    @Override
    public int expectedChildren() {
        return 0;
    }

    public int getJobID() {
        return 1;
    }
    
    public String getRequiredJobsInfo() {
        return "1";
    }

    @Override
    public String toString() {
        return "TaskNode";
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        
    }
}
