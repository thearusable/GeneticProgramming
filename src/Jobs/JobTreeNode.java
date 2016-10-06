/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jobs;

import JobsScheduling.JobsData;
import JobsScheduling.SingleJobData;
import ec.EvolutionState;
import ec.Problem;
import ec.app.multiplexer.MultiplexerData;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

/**
 *
 * @author arus2
 */
public abstract class JobTreeNode extends GPNode {
    
    @Override
    public abstract int expectedChildren(); 
    
    public abstract int getJobID();
    
    public abstract String getRequiredJobsInfo();
    
        @Override
    public String toString() {
        return "" + getJobID() + "_" + getRequiredJobsInfo();
        
    }
    
    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        JobsData data = (JobsData)(input);
        /*
        if(!data.Data.isEmpty()){
            SingleJobData temp = data.Data.get(0);
            System.out.println(temp.toString());
            data.Data.remove(0);
        }
*/
    }
    
}
