/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

/**
 *
 * @author arsc
 */
public class TimeNode {

    public TimeNode(TaskData task, int startTime) {
        this.task = task;
        this.startTime = startTime;
    }
    
    public TaskData task;
    public int startTime;
    
    public String getShortString(){
        //return task.jobID + "_" + task.whichTaskInJob;
        return startTime + "_(" + task.jobID + "," + task.whichTaskInJob + ")_" + (startTime +  task.duration);
    }
    
}
