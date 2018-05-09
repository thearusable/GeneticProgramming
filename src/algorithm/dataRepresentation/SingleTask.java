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
        return "T(J" + jobId + "|P" + calculatedPriority + "|W" + whichTaskInJob + "|D" + duration + "|M" + machineId + ")";
    }

    public SingleTask(int jobId, int whichTaskInJob, int duration, int requiredMachineID) {
        this.whichTaskInJob = whichTaskInJob;
        this.duration = duration;
        this.machineId = requiredMachineID;
        this.jobId = jobId;
        this.calculatedPriority = Double.MIN_VALUE;
    }
    
    public SingleTask()
    {
        whichTaskInJob = Integer.MIN_VALUE;
        duration = Integer.MIN_VALUE;
        machineId = Integer.MIN_VALUE;
        jobId = Integer.MIN_VALUE;
        calculatedPriority = Double.MIN_VALUE;
    }
    
    public SingleTask(SingleTask other)
    {
        this.whichTaskInJob = other.whichTaskInJob;
        this.duration = other.duration;
        this.machineId = other.machineId;
        this.jobId = other.jobId;
        this.calculatedPriority = other.calculatedPriority;
    }
    
    public double calculatedPriority;
    
    public int whichTaskInJob;
    public int duration;
    public int machineId;
    public int jobId;
}
