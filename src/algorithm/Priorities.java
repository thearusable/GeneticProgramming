/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import algorithm.dataRepresentation.SingleProblem;
import algorithm.dataRepresentation.SingleTask;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author areks
 */
public class Priorities {
    
    public SingleProblem problem;
    private ArrayList<ArrayList<TaskPriority>> priorities;
    
    public Priorities(SingleProblem prob)
    {
        problem = prob;
        priorities = new ArrayList<>();
        
        for(int job = 0; job < problem.jobs.size(); job++)
        {
            priorities.add(new ArrayList<>());
            
            for(int task = 0; task < problem.jobs.get(job).MAX_TASK_COUNT;  task++)
            {
                priorities.get(job).add(new TaskPriority(job, task));
            }
        }
    }
    
    public void setPriority(int job_id, int task_id, double priority)
    {
        priorities.get(job_id).get(task_id).setPriority(priority);
    }
    
    public SingleTask getTaskWithGreatestPriority()
    {
        int leaderJob = 0;
        double maxPriority = Double.MIN_VALUE;
        for(int job = 0; job < priorities.size(); job++)
        {
            if(priorities.get(job).size() > 0 && maxPriority < priorities.get(job).get(0).PRIORITY)
           {
               leaderJob = job;
               maxPriority = priorities.get(job).get(0).PRIORITY;
           }
        }
        
        //best task
        SingleTask leader = problem.getTask(priorities.get(leaderJob).get(0).JOB_ID, priorities.get(leaderJob).get(0).TASK_ID);
        
        //remove if empty array
        priorities.get(leaderJob).remove(0);
        if(priorities.get(leaderJob).isEmpty())
        {
            priorities.remove(leaderJob);
        }
        
        return leader;
    }
    
    public SingleTask getRandomWaitingTask()
    {
        int leaderJob = ThreadLocalRandom.current().nextInt(0, priorities.size());
        
        //best task
        SingleTask leader = problem.getTask(priorities.get(leaderJob).get(0).JOB_ID, priorities.get(leaderJob).get(0).TASK_ID);
        
        //remove if empty array
        priorities.get(leaderJob).remove(0);
        if(priorities.get(leaderJob).isEmpty())
        {
            priorities.remove(leaderJob);
        }
        
        return leader;
    }
    
    public boolean isNotFinished()
    {
        return !priorities.isEmpty();
    }
    
    public String toString()
    {
        String message = new String();
        for(ArrayList<TaskPriority> job : priorities)
        {
            message += "[";
            for(TaskPriority task : job)
            {
                message += task.toString();
            }
            message += "]\n";
        }
        return message;
    }
}
