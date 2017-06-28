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

    public TimeNode(int taskId, int startTime) {
        taskID = taskId;
        this.startTime = startTime;
    }
    
    public int taskID;
    public int startTime;
    
    public String getString(){
        return METADATA.getTaskString(startTime, taskID);
    }
    
}
