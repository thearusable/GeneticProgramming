/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.dataRepresentation;

/**
 *
 * @author arsc
 */
public class SingleTask {

    @Override
    public String toString() {
        return "T(" + calculatedPriority + "|" + whichTaskInJob + "|" + duration + "|" + machineId + ")";
    }

    public SingleTask(int whichTaskInJob, int duration, int requiredMachineID) {
        this.whichTaskInJob = whichTaskInJob;
        this.duration = duration;
        this.machineId = requiredMachineID;
        this.calculatedPriority = -1.0;
        nextTask = null;
    }
    
    public SingleTask(SingleTask other)
    {
        this.whichTaskInJob = other.whichTaskInJob;
        this.duration = other.duration;
        this.machineId = other.machineId;
        this.calculatedPriority = other.calculatedPriority;
        this.nextTask = other.nextTask;
    }
    
    public double calculatedPriority;
    
    public int whichTaskInJob;
    public int duration;
    public int machineId;
    
    public SingleTask nextTask;
}
