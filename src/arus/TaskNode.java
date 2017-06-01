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
import static java.lang.Math.max;
import java.util.Vector;

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
        return "j" + task.jobID + "t" + task.whichTaskInJob + "D" + task.duration;
    }
        
    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem){
        
        TreeData data = ((TreeData)input); 
        
        //Add occurs
        data.howManyTimesOccurs[getWhichOne()] += 1;
        
        //read startTime
        int startTimePerJob = data.StartupTimesPerJob[task.jobID];
        int startTimePerMachine = data.StartupTimesPerMachine[task.requiredMachineID];
        int startTime = max(startTimePerJob, startTimePerMachine);
        //assing values in timesPerJob array
        data.timesPerJob[task.jobID][task.whichTaskInJob].startTime = startTime;
        data.timesPerJob[task.jobID][task.whichTaskInJob].endTime = startTime + task.duration;
        //assing values in timesPerMachine vector
        if(data.timesPerMachine.size() > task.requiredMachineID){
            //data.timesPerMachine.get(task.requiredMachineID).add(new Times(startTime, startTime + task.duration));
        }else{
            //data.timesPerMachine.add(new Vector<Times>());
            //data.timesPerMachine.get(task.requiredMachineID).add(new Times(startTime, startTime + task.duration));
            //data.timesPerMachine.get(task.requiredMachineID).add(new Times(startTime, startTime + task.duration));
        }
        //update new times
        data.StartupTimesPerJob[task.jobID] = startTime + task.duration;
        data.StartupTimesPerMachine[task.requiredMachineID] = startTime + task.duration;
        
    }
    
}
