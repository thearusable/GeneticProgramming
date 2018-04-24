/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.dataRepresentation;

/**
 * Representation of one JOB
 * 
 * @author areks
 */
public class SingleJob {
    private SingleTask head;
    private int size;
    
    // job id
    public final int JOB_ID;
    // number of tasks in this job
    public int TASK_COUNT;
    // average task duration in this job
    public double AVERAGE_DURATION_IN_JOB;
    // shortest task in this job
    public int LOWEST_DURATION_IN_JOB;
    // longest task in this job
    public int LONGEST_DURATION_IN_JOB;
    
    private void increment(int duration)
    {
        size++;
        if(size > TASK_COUNT)
        {
            AVERAGE_DURATION_IN_JOB = (TASK_COUNT * AVERAGE_DURATION_IN_JOB + duration) / size;
            TASK_COUNT = size;
            
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
    
    public SingleJob(int jobId)
    {
        head = null;
        size = 0;
        JOB_ID = jobId;
        TASK_COUNT = 0;
        AVERAGE_DURATION_IN_JOB = 0.0;
        LOWEST_DURATION_IN_JOB  = Integer.MAX_VALUE;
        LONGEST_DURATION_IN_JOB = Integer.MIN_VALUE;
    }
    
    public void append(SingleTask task)
    {
        if(head == null)
        {
            head = new SingleTask(task);
            increment(task.duration);
            return;
        }
        
        SingleTask tmpHead = head;
        while(tmpHead.nextTask != null)
        {
            tmpHead = tmpHead.nextTask;
        }
        tmpHead.nextTask = new SingleTask(task);
        increment(task.duration);
    }
    
    public SingleTask pop()
    {
        if(head != null)
        {
            SingleTask retVal = head;
            head = head.nextTask;
            size--;
            return retVal;
        }
        else
        {
            throw new IndexOutOfBoundsException();
        }
    }

    public SingleTask peek()
    {
        if(head != null)
        {
            return head;
        }
        else
        {
            throw new IndexOutOfBoundsException();
        }
    }
    
    public void set(SingleTask task)
    {
        if(head == null)
        {
            head = new SingleTask(task);
            increment(task.duration);
            return;
        }
        else   
        {
            SingleTask tmpNext = head.nextTask;
            head = task;
            head.nextTask = tmpNext;
        }
    }
    
    public void setMachineForTask(int pos, int machine)
    {
        if(pos > size) throw new IndexOutOfBoundsException();
        
        SingleTask tmpHead = head;
        for(int i = 0; i < pos; i++)
        {
            tmpHead = tmpHead.nextTask;
        }
        tmpHead.machineId = machine;
    }
    
    public boolean canPop()
    {
        return head != null;
    }
    
    public int size()
    {
        return size;
    }
    
    @Override
    public String toString()
    {
        String str = "J(" + JOB_ID + ") [";
        SingleTask tmpHead = head;
        while(tmpHead != null)
        {
            str += tmpHead.toString() + " ";
            tmpHead = tmpHead.nextTask;
        }
        str += "] ";
        return str;
    }
}
