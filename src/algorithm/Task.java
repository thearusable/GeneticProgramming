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
        
        data.taskOccur(task.ID, parent);
        
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
