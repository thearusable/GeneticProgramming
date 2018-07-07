/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

/**
 *
 * @author areks
 */
public class TaskPriority {
    public int JOB_ID;
    public int TASK_ID;
    public double PRIORITY;
    
    public TaskPriority(int job, int task)
    {
        JOB_ID = job;
        TASK_ID = task;
        PRIORITY = Double.MIN_VALUE;
    }
    
    public String toString()
    {
        return "[" + JOB_ID + "|" + TASK_ID + "|" + PRIORITY + "]";
    }
    
    public void setPriority(double priority)
    {
        PRIORITY = priority;
    }
}
