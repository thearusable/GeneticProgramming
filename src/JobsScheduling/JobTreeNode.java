/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JobsScheduling;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

/**
 *
 * @author arus2
 */
public class JobTreeNode extends GPNode {
    
    public int JobID;
    public int [] RequiredJobs;
    
    @Override
    public String toString() {
        return "" + JobID;
        
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        JobsData data = (JobsData)(input);
        
        if(!data.Data.isEmpty()){
            System.out.println(thread);
            SingleJobData temp = data.Data.get(0);
            JobID = temp.JobID;
            RequiredJobs = (int[])(temp.RequiredJobs.clone());
            data.Data.remove(0);
        }
        
    }
    
}
