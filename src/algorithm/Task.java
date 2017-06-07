/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;


import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.ERC;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Code;
import static java.lang.Math.max;

/**
 *
 * @author arsc
 */
public class Task extends ERC{

    @Override
    public int expectedChildren() {
        return 0;
    }

    @Override
    public String name() {
        return "ERCT";
    }
    
    public Task(){
        task =  METADATA.getRandomTask();
        startingTime = 0;
    }
    
    public TaskData task;
    public int startingTime;

    @Override
    public String toString() {
        return "j" + task.jobID + " t" + task.whichTaskInJob + " m" + task.requiredMachineID + " D" + task.duration;
    }
        
    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem){
        
        TreeData data = ((TreeData)input); 
        
        //Add occurs
        data.howManyTimesOccurs[task.jobID * METADATA.TASKS_PER_JOB + task.whichTaskInJob] += 1;
        
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
        
        //penality for wrong parent
        if(parent.getClass() != Machine.class){
            data.numberOfTasksOnWrongMachine += 1;
        }
    }
    
    @Override
    public void resetNode(EvolutionState es, int thread) {
        task = METADATA.getRandomTask();
        startingTime = 0;
    }

    @Override
    public boolean nodeEquals(GPNode gpnode) {
        return (gpnode.getClass() == this.getClass() && ((Task)gpnode).task.ID == task.ID);
    }

    @Override
    public String encode() {
        return Code.encode(task.jobID) + Code.encode(task.requiredMachineID) + Code.encode(task.whichTaskInJob) + Code.encode(task.duration);
    }
    
    
    @Override
    public void mutateERC(EvolutionState state, int thread) {
        task = METADATA.getCloseTaskWithinJob(task.ID);
    }
    
}
