/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus;

/**
 *
 * @author arsc
 */
public class Task {

    @Override
    public String toString() {
        return "Task{ID:" + ID + ", whichTaskInJob=" + whichTaskInJob + ", jobID=" + jobID + ", duration=" + duration + ", requiredMachineID=" + requiredMachineID + '}';
    }

    public Task(int iD, int whichTaskInJob, int jobID, int duration, int requiredMachineID) {
        this.ID = iD;
        this.whichTaskInJob = whichTaskInJob;
        this.jobID = jobID;
        this.duration = duration;
        this.requiredMachineID = requiredMachineID;
    }
    
    public int ID;
    public int whichTaskInJob;
    public int jobID;
    public int duration;
    public int requiredMachineID;
}
