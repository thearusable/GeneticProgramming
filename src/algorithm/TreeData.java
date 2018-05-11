/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import algorithm.dataRepresentation.SingleJob;
import algorithm.dataRepresentation.SingleProblem;
import algorithm.dataRepresentation.SingleTask;
import ec.gp.GPData;

/**
 *
 * @author areks
 */
public class TreeData extends GPData {
  
    //calculated result value
    public double value;
    
    // task
    public SingleTask task;
    
    // from problem
    public int LOWEST_DURATION_IN_PROBLEM;
    public int LONGEST_DURATION_IN_PROBLEM;
    public double AVERAGE_DURATION_IN_PROBLEM;
    
    // from job
    public int LOWEST_DURATION_IN_JOB;
    public int LONGEST_DURATION_IN_JOB;
    public double AVERAGE_DURATION_IN_JOB;

    public TreeData() 
    {
        value = 0.0;
        LOWEST_DURATION_IN_PROBLEM = 0;
        LONGEST_DURATION_IN_PROBLEM = 0;
        AVERAGE_DURATION_IN_PROBLEM = 0.0;
        LOWEST_DURATION_IN_JOB = 0;
        LONGEST_DURATION_IN_JOB = 0;
        AVERAGE_DURATION_IN_JOB = 0;
    }
    
    public void set(SingleTask currentTask, SingleJob currentJob, SingleProblem currentProblem)
    {
        task = currentTask;
        
        LOWEST_DURATION_IN_JOB = currentJob.LOWEST_DURATION_IN_JOB;
        LONGEST_DURATION_IN_JOB = currentJob.LONGEST_DURATION_IN_JOB;
        AVERAGE_DURATION_IN_JOB = currentJob.AVERAGE_DURATION_IN_JOB;
        
        LOWEST_DURATION_IN_PROBLEM = currentProblem.LOWEST_DURATION_IN_PROBLEM;
        LONGEST_DURATION_IN_PROBLEM = currentProblem.LONGEST_DURATION_IN_PROBLEM;
        AVERAGE_DURATION_IN_PROBLEM = currentProblem.AVERAGE_DURATION_IN_PROBLEM;
    }
    
    @Override
    public void copyTo(GPData other) {
        ((TreeData)other).value = value;
        ((TreeData)other).task = task;
        ((TreeData)other).LOWEST_DURATION_IN_PROBLEM = LOWEST_DURATION_IN_PROBLEM;
        ((TreeData)other).LONGEST_DURATION_IN_PROBLEM = LONGEST_DURATION_IN_PROBLEM;
        ((TreeData)other).AVERAGE_DURATION_IN_PROBLEM = AVERAGE_DURATION_IN_PROBLEM;
        ((TreeData)other).LOWEST_DURATION_IN_JOB = LOWEST_DURATION_IN_JOB;
        ((TreeData)other).LONGEST_DURATION_IN_JOB = LONGEST_DURATION_IN_JOB;
        ((TreeData)other).AVERAGE_DURATION_IN_JOB = AVERAGE_DURATION_IN_JOB;
    }
}
