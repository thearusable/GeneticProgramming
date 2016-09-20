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
 * @author arus2
 */
public class Job_0 extends JobTreeNode {

    @Override
    public int expectedChildren() {
        return 0;
    }

    @Override
    public int getJobID() {
        return 0;
    }

    @Override
    public String getRequiredJobsInfo() {
        return "R[None]";
    }
    
}
