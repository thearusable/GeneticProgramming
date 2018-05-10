/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.dataRepresentation;

import algorithm.TaskData;
import algorithm.TreeData;
import ec.gp.GPNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author areks
 */
public class SingleProblem implements Cloneable {

    public List<SingleJob> jobs;
    
    public int PROBLEM_ID;
    
    public int MACHINES_COUNT;
    public int TASKS_COUNT;
    
    public int LOWEST_DURATION_IN_PROBLEM;
    public int LONGEST_DURATION_IN_PROBLEM;
    public double AVERAGE_DURATION_IN_PROBLEM;
    
    public int BEST_RESULT_FROM_WEB;
    
    public SingleProblem()
    {
        jobs = new ArrayList<>();
        PROBLEM_ID = 0;
        BEST_RESULT_FROM_WEB = 0;
        MACHINES_COUNT = 0;
        LOWEST_DURATION_IN_PROBLEM = Integer.MAX_VALUE;
        LONGEST_DURATION_IN_PROBLEM = Integer.MIN_VALUE;
        AVERAGE_DURATION_IN_PROBLEM = 0.0;
        TASKS_COUNT = 0;
    }
    
    public SingleTask popWithGreatestPriority()
    {
        int job = 0;
        double maxPriority = Double.MIN_VALUE;
        for(int i = 0; i < jobs.size(); i++)
        {
            if(maxPriority < jobs.get(i).get(0).calculatedPriority)
            {
                job = i;
                maxPriority = jobs.get(i).get(0).calculatedPriority;
            }
        }
        
        SingleTask leader = jobs.get(job).pop();
        
        if(jobs.get(job).size() == 0)
        {
            jobs.remove(job);
        }
        
        return leader;
    }
    
    public boolean isNotFinished()
    {
        return !jobs.isEmpty();
    }
    
    public void load(int id, String dataFile, boolean debug) throws IOException 
    {
        PROBLEM_ID = id;
        
        BufferedReader reader = new BufferedReader(new FileReader(dataFile)); 
        String line;
        int[] readedData;
        int stage = 1;
        int currentReadedJob = 0;
        HashSet<Integer> machinesIds = new HashSet<>();
            
        while((line = reader.readLine()) != null){
            //ignore comments
            if(isComment(line) || line.length() == 0) continue;
            //metadata
            if(stage == 1){
                if(line.contains("Times")){
                    stage = 2;
                    continue;
                }
                readedData = split(line);
                BEST_RESULT_FROM_WEB = readedData[0];
                continue;
            }
            //times
            if(stage == 2){
                if(line.contains("Machines")){
                    currentReadedJob = 0;
                    stage = 3;
                    continue;
                }
                readedData = split(line);
                
                if(readedData.length > 0)
                {
                    jobs.add(new SingleJob(currentReadedJob));
                }
                
                for(int i = 0; i < readedData.length; i++)
                {                   
                    jobs.get(currentReadedJob).append(readedData[i]);
                }
                
                currentReadedJob++;
                continue;
            }
            //machines
            if(stage == 3){
                readedData = split(line);
                
                for(int i = 0; i < readedData.length; i++)
                {
                    jobs.get(currentReadedJob).setMachineForTask(i, readedData[i]);
                    machinesIds.add(readedData[i]);
                }
                
                currentReadedJob++;
            }
        }
        
        for(int i = 0; i < jobs.size(); i++)
        {
            TASKS_COUNT += jobs.get(i).MAX_TASK_COUNT;
            AVERAGE_DURATION_IN_PROBLEM += jobs.get(i).AVERAGE_DURATION_IN_JOB;
            
            if(LOWEST_DURATION_IN_PROBLEM > jobs.get(i).LOWEST_DURATION_IN_JOB)
            {
                LOWEST_DURATION_IN_PROBLEM = jobs.get(i).LOWEST_DURATION_IN_JOB;
            }
            
            if(LONGEST_DURATION_IN_PROBLEM < jobs.get(i).LONGEST_DURATION_IN_JOB)
            {
                LONGEST_DURATION_IN_PROBLEM = jobs.get(i).LONGEST_DURATION_IN_JOB;
            }
        }
        
        AVERAGE_DURATION_IN_PROBLEM = AVERAGE_DURATION_IN_PROBLEM / jobs.size();
        MACHINES_COUNT = machinesIds.size();
 
        //debug print
        if(debug == true){
            print();
        }
    }
    
    private static boolean isComment(String line){
        return line.length() > 0 && line.charAt(0) == '#';
    }
    
    private static int[] split(String text) {
        if(text.length() == 0) return null;
        
        StringTokenizer tokenizer = new StringTokenizer(text);
        String line = "";
        
        while(tokenizer.hasMoreTokens()){
            line += tokenizer.nextToken() + " ";
        }
        
        String[] stringData = line.split(" ");
        int[] intData = new int[stringData.length];
        
        for(int i = 0; i < stringData.length; i++){
            intData[i] = Integer.parseInt(stringData[i]);
        }
        
        return intData;
    }
    
    public void print(){
        System.out.println("\nPROBLEM " + PROBLEM_ID + ":");

        for(int i = 0; i < jobs.size(); i++)
        {
            System.out.println(jobs.get(i).toString());
        }       
        System.out.println("JOBS_COUNT: \t\t" + jobs.size());
        System.out.println("MACHINES_COUNT: \t" + MACHINES_COUNT);
        System.out.println("TASKS_COUNT: \t\t" + TASKS_COUNT);
        System.out.println("LOWEST_TASK_DURATION: \t" + LOWEST_DURATION_IN_PROBLEM);
        System.out.println("LONGEST_TASK_DURATION: \t" + LONGEST_DURATION_IN_PROBLEM);
        System.out.println("AVERAGE_TASK_DURATION: \t" + AVERAGE_DURATION_IN_PROBLEM);
        System.out.println("BEST_RESULT_FROM_WEB: \t" + BEST_RESULT_FROM_WEB);
    }  
    
    @Override
    public SingleProblem clone() throws CloneNotSupportedException {
        SingleProblem problem = (SingleProblem)super.clone();
        
        problem.jobs = new ArrayList<>();
        for(SingleJob job : jobs)
        {
            problem.jobs.add(job.clone());
        }
        
        return problem;
    }
    
}
