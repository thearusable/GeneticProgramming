/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus;

import arus.Task;
import arus.METADATA;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

/**
 *
 * @author arsc
 */
public abstract class TaskNode extends GPNode {

    public TaskNode(Task task) {
        this.task = task;
        startingTime = 0;
    }
    
    public TaskNode(){
        task =  METADATA.getTask( getWhichOne() );
        startingTime = 0;
    }
    //
    public abstract int getWhichOne();
    
    public Task task;
    public int startingTime;


    @Override
    public String toString() {
        return "[J" + task.jobID + "T" + task.whichTaskInJob + "]";
    }
    
    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem){
        
        TreeData data = ((TreeData)input); 
        
        //Add occurs
        data.howManyTimesOccurs[getWhichOne()] += 1;
        
    }
    
}
