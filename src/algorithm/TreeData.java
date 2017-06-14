/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import ec.gp.GPData;
import ec.gp.GPNodeParent;
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
    private int[] OccursCounterPerTask; //index = taskID
    
    Stats stats; 
    
    public int machineWithBadParent; //+dummy and machine
    public int taskWithBadParent; //+dummy
    public int taskOnWrongMachine; //+when machine id != parentID
    public int taskInWrongOrder; //+when task occurs in wrong order in job
    public int taskWithBadTime; //TODO when task occurs in bad time, like overlap
    public int makespan; //+time to execute whole tree
    public int taskMissing; //+when task is not in the tree
    public int taskDoubled; //+when is too many same tasks
    
    public TreeData() {
        occurrences = new Occurrence[METADATA.JOBS_COUNT * METADATA.TASKS_PER_JOB];
        StartupTimesPerJob = new int[METADATA.JOBS_COUNT];
        PreviousExecuteTaskPerJob = new int[METADATA.JOBS_COUNT];
        OccursCounterPerTask = new int[METADATA.TASKS_COUNT];
        stats = new Stats();
        
        reset();
    }
    
    public void taskOccur(int taskID, GPNodeParent parent){
        //get TaskData for taskID
        TaskData task = METADATA.getTask(taskID);
        //get parenID
        int parentID = -1;
        if(parent == null){
            parentID = -1;
        }else if(parent.getClass() == Dummy.class){
            parentID = -1;
        }else if(parent.getClass() == Machine.class){
            parentID = ((Machine)parent).ID;
        }
        //add task occurennce
        OccursCounterPerTask[taskID] += 1;
        
        
        //fill startupTime and record task occurrence
        //StartupTimesPerJob[task.jobID] = occurrences[taskID].Occur(taskID, parentID, StartupTimesPerJob[task.jobID]);
        
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
        //for(int i = 0; i <  occurrences.length; ++i){
        //    int x = occurrences[i].getHowManyTimesOccurrs();
        //    if(x < 1){ // missing
        //        stats.taskMissing += 1;
        //    }else if(x > 1){ // doubling
        //        stats.taskDoubled += 1;
        //    }
        //}
        
        for(int i = 0; i < OccursCounterPerTask.length; ++i){
            if(OccursCounterPerTask[i] > 1){
                stats.taskDoubled += OccursCounterPerTask[i] - 1;
            }else if(OccursCounterPerTask[i] <= 0){
                stats.taskMissing += 1;
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
        
        for(int i = 0; i < OccursCounterPerTask.length; ++i){
            OccursCounterPerTask[i] = 0;
        }
        
        stats.reset();
    }
    
    @Override
    public String toString() {
        String str = "------------------------\n";        
        str += "PreviousExecuteTaskPerJob: " + Arrays.toString(PreviousExecuteTaskPerJob);
        str += "\nStartupTimesPerJob: " + Arrays.toString(StartupTimesPerJob);
        str += "\nOccursCounterPerTask: " + Arrays.toString(OccursCounterPerTask);
        str += "\n" + stats.toString();
        
        return str;
    }
    
    @Override
    public Object clone() {
        TreeData other = (TreeData)super.clone();
        other.occurrences = (Occurrence[])occurrences.clone();
        other.StartupTimesPerJob = (int[])StartupTimesPerJob.clone();
        other.PreviousExecuteTaskPerJob = (int[])PreviousExecuteTaskPerJob.clone();
        other.OccursCounterPerTask = (int[])OccursCounterPerTask.clone();
        other.stats = (Stats)stats.clone();
        return other;
    }

    @Override
    public void copyTo(final GPData o) {
        TreeData other = (TreeData)o;
        System.arraycopy(occurrences, 0, other.occurrences, 0, occurrences.length);
        System.arraycopy(StartupTimesPerJob, 0, other.StartupTimesPerJob, 0, StartupTimesPerJob.length);
        System.arraycopy(PreviousExecuteTaskPerJob, 0, other.PreviousExecuteTaskPerJob, 0, PreviousExecuteTaskPerJob.length);
        System.arraycopy(OccursCounterPerTask, 0, other.OccursCounterPerTask, 0, OccursCounterPerTask.length);
        other.stats = (Stats)stats.clone();
    }
    
    private Occurrence getOccurrence(int jobID, int whichTaskInJob){
        return occurrences[jobID * METADATA.TASKS_PER_JOB + whichTaskInJob];
    }
}
