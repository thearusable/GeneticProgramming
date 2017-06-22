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
import nodes.Connector;
import nodes.ERCnode;
import nodes.Machine;
import nodes.Task;

/**
 *
 * @author arus2
 */
public final class TreeData extends GPData {

    private int[] StartupTimesPerJob; //index == jobID
    private int[] PreviousExecuteTaskPerJob; //index == jobID
    
    public int[] OccursCounterPerTask; //index = taskID
    
    public int machineWithBadChild; //when machine have child with difrent id 
    public int taskInWrongOrder; //when task occurs in wrong order in job
    public int ConnectorWithBadChild; //when connector have task child
    
    public TreeData() {
        StartupTimesPerJob = new int[METADATA.JOBS_COUNT];
        PreviousExecuteTaskPerJob = new int[METADATA.JOBS_COUNT];
        OccursCounterPerTask = new int[METADATA.TASKS_COUNT];
        
        reset();
    }
    
    //bad when connector have task childs
    public void connector(GPNode child){
        if(child.getClass().getName().equals(Task.class.getName())){
            ConnectorWithBadChild += 1;
        }
    }
    
    //task is bad when - missing, doubled, inWrongOrder 
    public void task(int taskID, GPNodeParent parent){
        
        TaskData task = METADATA.getTask(taskID);
        //add task occurennce
        OccursCounterPerTask[taskID] += 1;
        
        //fill startupTime
        StartupTimesPerJob[task.jobID] += task.duration;
        
        //checking execute order
        if(PreviousExecuteTaskPerJob[task.jobID] >= 0 
                && task.whichTaskInJob != PreviousExecuteTaskPerJob[task.jobID] - 1){
            
            taskInWrongOrder += 1;
        }
        //assing last executed task
        PreviousExecuteTaskPerJob[task.jobID] = task.whichTaskInJob;
    }
    
    //bad when machine have child with difrent id
    public void machine(int machineID, GPNode child){  
        int childID = -1;
        if(child != null){
            //if child is task
            if(child.getClass().getName().equals(Task.class.getName())){
                childID = ((Task)child).getRequiredMachineID();
            }else{
                //if not task
                childID = ((ERCnode)child).getID();
            }
        }
        
        if(machineID != childID){
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
        
        taskInWrongOrder = 0;
        machineWithBadChild = 0;
        ConnectorWithBadChild = 0;
    }
    
    @Override
    public String toString() {
        String str = "------------------------\n";        
        str += "PreviousExecuteTaskPerJob: \n" + Arrays.toString(PreviousExecuteTaskPerJob);
        str += "\nStartupTimesPerJob: \n" + Arrays.toString(StartupTimesPerJob);
        str += "\nOccursCounterPerTask: \n" + Arrays.toString(OccursCounterPerTask);
        str += "\ntaskInWrongOrder: " + taskInWrongOrder;
        str += "\nmachineWithBadChild: " + machineWithBadChild;
        str += "\nConnectorWithBadChild: " + ConnectorWithBadChild;
        
        
        int missing = 0, doubled = 0;
        for(int i = 0; i < OccursCounterPerTask.length; ++i){
                if(OccursCounterPerTask[i] > 1){
                    doubled += (OccursCounterPerTask[i] - 1);
                }else if(OccursCounterPerTask[i] < 1){
                    missing += 1;
                }
            }
        
        str += "\nDoubled: " + doubled;
        str += "\nMissing: " + missing;
        
        return str;
    }
    
    @Override
    public Object clone() {
        TreeData other = (TreeData)super.clone();
        other.StartupTimesPerJob = (int[])StartupTimesPerJob.clone();
        other.PreviousExecuteTaskPerJob = (int[])PreviousExecuteTaskPerJob.clone();
        other.OccursCounterPerTask = (int[])OccursCounterPerTask.clone();
        other.taskInWrongOrder = taskInWrongOrder;
        other.machineWithBadChild = machineWithBadChild;
        other.ConnectorWithBadChild = ConnectorWithBadChild;
        return other;
    }

    @Override
    public void copyTo(final GPData o) {
        TreeData other = (TreeData)o;
        System.arraycopy(StartupTimesPerJob, 0, other.StartupTimesPerJob, 0, StartupTimesPerJob.length);
        System.arraycopy(PreviousExecuteTaskPerJob, 0, other.PreviousExecuteTaskPerJob, 0, PreviousExecuteTaskPerJob.length);
        System.arraycopy(OccursCounterPerTask, 0, other.OccursCounterPerTask, 0, OccursCounterPerTask.length);
        other.taskInWrongOrder = taskInWrongOrder;
        other.machineWithBadChild = machineWithBadChild;
        other.ConnectorWithBadChild = ConnectorWithBadChild;
    }
}
