/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.dataRepresentation;

import java.util.ArrayList;

/**
 * Representation of one JOB
 * 
 * @author areks
 */
public class SingleJob implements java.io.Serializable, Cloneable {

    private static final long serialVersionUID = 1;
    
    // array with tasks
    ArrayList<SingleTask> tasks;
    
    // job id
    public final int JOB_ID;
    // number of tasks in this job at beggining
    public int MAX_TASK_COUNT;
    // average task duration in this job
    public double AVERAGE_DURATION_IN_JOB;
    // shortest task in this job
    public int LOWEST_DURATION_IN_JOB;
    // longest task in this job
    public int LONGEST_DURATION_IN_JOB;
    
    public SingleJob(int jobId)
    {
        tasks = new ArrayList<>();
        
        JOB_ID = jobId;
        MAX_TASK_COUNT = 0;
        AVERAGE_DURATION_IN_JOB = 0.0;
        LOWEST_DURATION_IN_JOB  = Integer.MAX_VALUE;
        LONGEST_DURATION_IN_JOB = Integer.MIN_VALUE;
    }
    
    private void increment(int duration)
    {
        int size = tasks.size();
        if(size > MAX_TASK_COUNT)
        {
            AVERAGE_DURATION_IN_JOB = (MAX_TASK_COUNT * AVERAGE_DURATION_IN_JOB + duration) / size;
            MAX_TASK_COUNT = size;
            
            if(duration > LONGEST_DURATION_IN_JOB) 
            {
                LONGEST_DURATION_IN_JOB = duration;
            }
            else if(duration < LOWEST_DURATION_IN_JOB)
            {
                LOWEST_DURATION_IN_JOB = duration;
            }
        }
    }
      
    public void append(int duration)
    {
        SingleTask task = new SingleTask();
        task.duration = duration;
        task.jobId = JOB_ID;
        task.whichTaskInJob = tasks.size();
        tasks.add(task);
        increment(task.duration);
    }
    
    public SingleTask pop()
    {
        if(!canPop())
        {
            throw new IndexOutOfBoundsException();
        }
        
        SingleTask retVal = tasks.get(0);
        tasks.remove(0);
        return retVal;
    }

    public SingleTask get(int index)
    {       
        return tasks.get(index);
    }
    
    public void set(int index, SingleTask task)
    {
        tasks.set(index, task);
    }
    
    public void setMachineForTask(int pos, int machine)
    {
        SingleTask temp = tasks.get(pos);
        temp.machineId = machine;
        tasks.set(pos, temp);
    }
    
    public boolean canPop()
    {
        return tasks.size() > 0;
    }
    
    public int size()
    {
        return tasks.size();
    }
    
    @Override
    public String toString()
    {
        String str = "J(" + JOB_ID + ") [";
        
        str = tasks.stream().map((task) -> {
            return task.toString() + " ";
        }).reduce(str, String::concat);
        
        str += "] ";
        return str;
    }
    
    @Override
    public SingleJob clone() throws CloneNotSupportedException {
        SingleJob job = (SingleJob)super.clone();
        
        job.tasks = new ArrayList<>();
        for(SingleTask task : tasks)
        {
            job.tasks.add(task.clone());
        }
        
        return job;
    }
}
