/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus.base;

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
public abstract class BaseTask extends GPNode {

    public abstract int getJobId();
    public abstract int getWhichInJob();
    public abstract int getMachineId();
    public abstract int getProcessingTime();
    public abstract int getUniqueId();

    @Override
    public String toString() {
        return "J" + getJobId() + "T" + getWhichInJob() + "_" + getProcessingTime();
    }

    @Override
    public void eval(EvolutionState state
            , int thread
            , GPData input
            , ADFStack stack
            , GPIndividual individual
            , Problem problem) 
    {
        BaseData data = (BaseData) input;
        
        for(int i = 0; i < children.length; i++){
            children[i].eval(state, thread, input, stack, individual, problem);
        }
                
        data.TaskCount[getUniqueId()] += 1;
    }
    
    
}
