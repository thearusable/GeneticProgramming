/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import ec.gp.GPData;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author arus2
 */
public final class TreeData extends GPData {

    //occurrence of each task
    private Occurrence[] occurrences; //index == taskID
    private int[] StartupTimesPerJob; //index == jobID
    private int[] PreviousExecuteTaskPerJob; //index == jobID
    Stats stats; 
    
    public TreeData() {
        occurrences = new Occurrence[METADATA.JOBS_COUNT * METADATA.TASKS_PER_JOB];
        StartupTimesPerJob = new int[METADATA.JOBS_COUNT];
        PreviousExecuteTaskPerJob = new int[METADATA.JOBS_COUNT];
        stats = new Stats();
        
        reset();
    }
    
    public void taskOccur(int taskID, int parentID){
        TaskData task = METADATA.getTask(taskID);
        //fill startupTime and record task occurrence
        StartupTimesPerJob[task.jobID] = occurrences[taskID].Occur(taskID, parentID, StartupTimesPerJob[task.jobID]);
        //checking task parent and machine id
        if(parentID >= 0 && task.requiredMachineID != parentID){
            stats.taskOnWrongMachine += 1;
        }else if (parentID == -1){
            stats.taskWithBadParent += 1;
        }
        //checking execute order
        if(PreviousExecuteTaskPerJob[task.jobID] >= 0 
                && task.whichTaskInJob != PreviousExecuteTaskPerJob[task.jobID] - 1){
            
            stats.taskInWrongOrder += 1;
        }
        //assing last executed task
        PreviousExecuteTaskPerJob[task.jobID] = task.whichTaskInJob;
        
    }
    
    public void machineOccur(boolean isNotValid){
        if(isNotValid == true){
            stats.machineWithBadParent += 1;
        }
    }
    
    public Stats getStats(){
        //check how many times each task Occurrs
        for(int i = 0; i < occurrences.length; ++i){
            int x = occurrences[i].getHowManyTimesOccurrs();
            if(x < 1){ // missing
                stats.taskMissing += 1;
            }else if(x > 1){ // doubling
                stats.taskDoubled += 1;
            }
        }
        
        //check if tasks times dont overlap
        
        
        //calc makespan
        int makespan = StartupTimesPerJob[0];
        for(int i = 1; i < StartupTimesPerJob.length; ++i){
            if(StartupTimesPerJob[i] > makespan) makespan = StartupTimesPerJob[i];
        }
        stats.makespan = makespan;

        return stats;
    }
    
    public void reset(){
        
        for(int i = 0; i < occurrences.length; ++i){
            occurrences[i] = new Occurrence();
        }
        
        for(int i = 0; i < StartupTimesPerJob.length; ++i){
            StartupTimesPerJob[i] = 0;
        }
        
        for(int i = 0; i < PreviousExecuteTaskPerJob.length; ++i){
            PreviousExecuteTaskPerJob[i] = -1;
        }
        
        stats.reset();
    }
    
    @Override
    public String toString() {
        String str = "------------------------\n";
        str += "Occurrences: \n";
        for(int i = 0; i < occurrences.length; ++i){
            str += occurrences[i].toString() + "\n";
        }
        
        str += "PreviousExecuteTaskPerJob: " + Arrays.toString(PreviousExecuteTaskPerJob);
        str += "\nStartupTimesPerJob: " + Arrays.toString(StartupTimesPerJob);
        str += "\n" + stats.toString();
        
        return str;
    }
    
    @Override
    public Object clone() {
        TreeData other = (TreeData)super.clone();
        other.occurrences = (Occurrence[])occurrences.clone();
        other.StartupTimesPerJob = (int[])StartupTimesPerJob.clone();
        other.PreviousExecuteTaskPerJob = (int[])PreviousExecuteTaskPerJob.clone();
        other.stats = (Stats)stats.clone();
        return other;
    }

    @Override
    public void copyTo(final GPData o) {
        TreeData other = (TreeData)o;
        System.arraycopy(occurrences, 0, other.occurrences, 0, occurrences.length);
        System.arraycopy(StartupTimesPerJob, 0, other.StartupTimesPerJob, 0, StartupTimesPerJob.length);
        System.arraycopy(PreviousExecuteTaskPerJob, 0, other.PreviousExecuteTaskPerJob, 0, PreviousExecuteTaskPerJob.length);
        other.stats = (Stats)stats.clone();
    }
    
    private Occurrence getOccurrence(int jobID, int whichTaskInJob){
        return occurrences[jobID * METADATA.TASKS_PER_JOB + whichTaskInJob];
    }
}
