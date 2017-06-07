/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import ec.gp.GPData;
import java.util.Arrays;
import java.util.Vector;

/**
 *
 * @author arus2
 */
public class TreeData extends GPData {

    public int[] howManyTimesOccurs;
    
    //calculated from MachineERC
    public int numberOfTasksOnWrongMachine;
    public int toManyChilds;
    public int machinesWithoutChilds;
    
    //calculated from DummyERC
    public int wrongChildOfDummy;
    
    //para (int,int) - startTime, EndTime
    //2d array - Job0: pair(0,x)
    public Times[][] timesPerJob;
    //must be dynamic
    public Vector<Vector<Times>> timesPerMachine = new Vector<Vector<Times>>(METADATA.MACHINES_COUNT);
    //public Times[][] timesPerMachine;
    
    //startup time per job (and meybe second per machine)
    public int[] StartupTimesPerJob;
    public int[] StartupTimesPerMachine;
   
    
    public TreeData() {
        howManyTimesOccurs = new int[METADATA.TASKS_COUNT];
        numberOfTasksOnWrongMachine = 0;
        toManyChilds = 0;
        machinesWithoutChilds = 0;
        wrongChildOfDummy = 0;
        
        timesPerJob = new Times[METADATA.JOBS_COUNT][METADATA.TASKS_PER_JOB];
        //schoud by enough big number
        timesPerMachine = new Vector<Vector<Times>>(METADATA.MACHINES_COUNT);
        //timesPerMachine = new Times[METADATA.MACHINES_COUNT][METADATA.TASKS_PER_JOB * 2];
        
        StartupTimesPerJob = new int[METADATA.JOBS_COUNT];
        StartupTimesPerMachine = new int[METADATA.MACHINES_COUNT];
        
        reset();
    }
    
    public int getMakespan(){
        int makespan = StartupTimesPerMachine[0];
        
        for(int i = 1; i < StartupTimesPerMachine.length; ++i){
            if(StartupTimesPerMachine[i] > makespan) makespan = StartupTimesPerMachine[i];
        }
        
        return makespan;
    }
    
    public void reset(){
        for(int i = 0; i < howManyTimesOccurs.length; ++i ){
            howManyTimesOccurs[i] = 0;
        }
        numberOfTasksOnWrongMachine = 0;
        toManyChilds = 0;
        machinesWithoutChilds = 0;
        wrongChildOfDummy = 0;
        
        for(int i = 0; i < timesPerJob.length; ++ i){
            for(int j = 0; j < timesPerJob[0].length; ++j){
                timesPerJob[i][j] = new Times();
            }
        }
        
        timesPerMachine.clear();
        
        for(int i = 0; i < METADATA.MACHINES_COUNT; ++ i){
            timesPerMachine.add(new Vector<Times>());
        }
        
        for(int i = 0; i < StartupTimesPerJob.length; ++i){
            StartupTimesPerJob[i] = 0;
        }
        
        for(int i = 0; i < StartupTimesPerMachine.length; ++i){
            StartupTimesPerMachine[i] = 0;
        }
    }
    
    @Override
    public String toString() {
        String str = "------------------------\n";
        str += "howManyTimesOccurs:\n" + Arrays.toString(howManyTimesOccurs) + "\ntimesPerJob:\n";
        
        for(int i = 0; i < timesPerJob.length; ++i){
            str += Arrays.toString(timesPerJob[i]);
            str += "\n";
        }
        str += "timesPerMachine: \n";
        for(int i = 0; i < timesPerMachine.size(); ++i){
            str += timesPerMachine.get(i).toString();
            //str += Arrays.toString(timesPerMachine[i]);
            str += "\n";
        }
        
        str += "StartupTimesPerJob: " + Arrays.toString(StartupTimesPerJob);
        str += "\nStartupTimesPerMachine: " + Arrays.toString(StartupTimesPerMachine) + "\n";
        str += "numberOfTasksOnWrongMachine: " + numberOfTasksOnWrongMachine + "\n";
        str += "toManyChilds: " + toManyChilds + "\n";
        str += "machinesWithoutChilds: " + machinesWithoutChilds;
        str += "wrongChildOfDummy: " + wrongChildOfDummy;
        
        return str;
    }
    
    @Override
    public Object clone() {
        TreeData bd = new TreeData();
        bd.howManyTimesOccurs = howManyTimesOccurs.clone();
        bd.timesPerJob = timesPerJob.clone();
        bd.timesPerMachine = timesPerMachine;
        bd.wrongChildOfDummy = wrongChildOfDummy;
        bd.StartupTimesPerJob = StartupTimesPerJob.clone();
        bd.StartupTimesPerMachine = StartupTimesPerMachine.clone();
        return bd;
    }

    @Override
    public void copyTo(GPData gpd) {
        ((TreeData)gpd).howManyTimesOccurs = howManyTimesOccurs.clone();
        ((TreeData)gpd).timesPerJob = timesPerJob.clone();
        ((TreeData)gpd).timesPerMachine = timesPerMachine;
        ((TreeData)gpd).wrongChildOfDummy = wrongChildOfDummy;
        ((TreeData)gpd).StartupTimesPerJob = StartupTimesPerJob.clone();
        ((TreeData)gpd).StartupTimesPerMachine = StartupTimesPerMachine.clone();
    }
    
}
