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
public class SingleTask implements java.io.Serializable, Cloneable {

    private static final long serialVersionUID = 1;
    
    public int whichTaskInJob;
    public int duration;
    public int machineId;
    public int jobId;

    public SingleTask(int jobId, int whichTaskInJob, int duration, int requiredMachineID) {
        this.whichTaskInJob = whichTaskInJob;
        this.duration = duration;
        this.machineId = requiredMachineID;
        this.jobId = jobId;
    }
    
    public SingleTask()
    {
        whichTaskInJob = Integer.MIN_VALUE;
        duration = Integer.MIN_VALUE;
        machineId = Integer.MIN_VALUE;
        jobId = Integer.MIN_VALUE;
    }
    
    public SingleTask(SingleTask other)
    {
        this.whichTaskInJob = other.whichTaskInJob;
        this.duration = other.duration;
        this.machineId = other.machineId;
        this.jobId = other.jobId;
    }
    
    @Override
    public String toString() {
        return "T(J" + jobId + "|W" + whichTaskInJob + "|D" + duration + "|M" + machineId + ")";
    }
    
    @Override
    public SingleTask clone() throws CloneNotSupportedException {
        return (SingleTask)super.clone();
    }
    
    
}
