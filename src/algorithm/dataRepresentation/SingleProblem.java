/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.dataRepresentation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author areks
 */
public class SingleProblem {
    private List<SingleJob> jobs;
    
    //public int JOBS_COUNT = 0; // size of jobs
    public int MACHINES_COUNT = 0;
    
    public int LOWEST_TASK_DURATION = Integer.MAX_VALUE;
    public int LONGEST_TASK_DURATION = Integer.MIN_VALUE;
    public double AVERAGE_TASK_DURATION = 0.0;
    
    public int BEST_RESULT_FROM_WEB = 0;
    
    SingleProblem()
    {
        jobs = new ArrayList<>();
    }
    
    
}
