/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import ec.gp.GPData;
import ec.gp.GPNode;
import ec.gp.GPNodeParent;
import java.util.Arrays;

/**
 *
 * @author arus2
 */
public final class TreeData extends GPData {

    private int[] StartupTimesPerJob; //index == jobID
    private int[] PreviousExecuteTaskPerJob; //index == jobID
    
    public int[] OccursCounterPerTask; //index = taskID
    
    public int machineWithBadParent; //+dummy and machine
    public int machineWithBadChild;
    public int taskWithBadParent; //+dummy
    public int taskOnWrongMachine; //+when machine id != parentID
    public int taskInWrongOrder; //+when task occurs in wrong order in job
    public int taskWithBadTime; //TODO when task occurs in bad time, like overlap
    
    public TreeData() {
        StartupTimesPerJob = new int[METADATA.JOBS_COUNT];
        PreviousExecuteTaskPerJob = new int[METADATA.JOBS_COUNT];
        OccursCounterPerTask = new int[METADATA.TASKS_COUNT];
        
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
        StartupTimesPerJob[task.jobID] += task.duration;
        
        //checking task parent and machine id
        if(parentID >= 0 && task.requiredMachineID != parentID){
            taskOnWrongMachine += 1;
        }else if (parentID == -1){
            taskWithBadParent += 1;
        }
        //checking execute order
        if(PreviousExecuteTaskPerJob[task.jobID] >= 0 
                && task.whichTaskInJob != PreviousExecuteTaskPerJob[task.jobID] - 1){
            
            taskInWrongOrder += 1;
        }
        //assing last executed task
        PreviousExecuteTaskPerJob[task.jobID] = task.whichTaskInJob;
        
    }
    
    public void machineOccur(final GPNodeParent parent){
        if(parent != null && parent.getClass() != Dummy.class){
            machineWithBadParent += 1;
        }
    }
    
    public void checkMachineChild(final GPNode child){
        if(child != null && child.getClass() != Task.class){
            machineWithBadChild += 1;
        }
    }
    
    public int getMakespan(){
        int makespan = StartupTimesPerJob[0];
        for(int i = 1; i < StartupTimesPerJob.length; ++i){
            if(StartupTimesPerJob[i] > makespan) makespan = StartupTimesPerJob[i];
        }

        return makespan;
    }
    
    public void reset(){

        for(int i = 0; i < METADATA.JOBS_COUNT; ++i){
            StartupTimesPerJob[i] = 0;
            PreviousExecuteTaskPerJob[i] = -1;
        }
        
        for(int i = 0; i < METADATA.TASKS_COUNT; ++i){
            OccursCounterPerTask[i] = 0;
        }
        
        machineWithBadParent = 0; 
        taskWithBadParent = 0;
        taskOnWrongMachine = 0;
        taskInWrongOrder = 0;
        taskWithBadTime = 0;
        machineWithBadChild = 0;
    }
    
    @Override
    public String toString() {
        String str = "------------------------\n";        
        str += "PreviousExecuteTaskPerJob: " + Arrays.toString(PreviousExecuteTaskPerJob);
        str += "\nStartupTimesPerJob: " + Arrays.toString(StartupTimesPerJob);
        str += "\nOccursCounterPerTask: " + Arrays.toString(OccursCounterPerTask);
        str += "\nmachineWithBadParent: " + machineWithBadParent;
        str += "\ntaskWithBadParent: " + taskWithBadParent;
        str += "\ntaskOnWrongMachine: " + taskOnWrongMachine;
        str += "\ntaskInWrongOrder: " + taskInWrongOrder;
        str += "\ntaskWithBadTime: " + taskWithBadTime;
        str += "\nmachineWithBadChild: " + machineWithBadChild;
        
        return str;
    }
    
    @Override
    public Object clone() {
        TreeData other = (TreeData)super.clone();
        other.StartupTimesPerJob = (int[])StartupTimesPerJob.clone();
        other.PreviousExecuteTaskPerJob = (int[])PreviousExecuteTaskPerJob.clone();
        other.OccursCounterPerTask = (int[])OccursCounterPerTask.clone();
        other.machineWithBadParent = machineWithBadParent;
        other.taskInWrongOrder = taskInWrongOrder;
        other.taskOnWrongMachine = taskOnWrongMachine;
        other.taskWithBadParent = taskWithBadParent;
        other.taskWithBadTime = taskWithBadTime;
        other.machineWithBadChild = machineWithBadChild;
        return other;
    }

    @Override
    public void copyTo(final GPData o) {
        TreeData other = (TreeData)o;
        System.arraycopy(StartupTimesPerJob, 0, other.StartupTimesPerJob, 0, StartupTimesPerJob.length);
        System.arraycopy(PreviousExecuteTaskPerJob, 0, other.PreviousExecuteTaskPerJob, 0, PreviousExecuteTaskPerJob.length);
        System.arraycopy(OccursCounterPerTask, 0, other.OccursCounterPerTask, 0, OccursCounterPerTask.length);
        other.machineWithBadParent = machineWithBadParent;
        other.taskInWrongOrder = taskInWrongOrder;
        other.taskOnWrongMachine = taskOnWrongMachine;
        other.taskWithBadParent = taskWithBadParent;
        other.taskWithBadTime = taskWithBadTime;
        other.machineWithBadChild = machineWithBadChild;
    }
}
