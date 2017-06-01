/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus;

import ec.gp.GPData;
import java.util.Arrays;

/**
 *
 * @author arus2
 */
public class TreeData extends GPData {

    public int[] howManyTimesOccurs;
    public int numberOfTasksOnWrongMachine;
    public int toManyChilds;
    public int machinesWithoutChilds;
    
    //para (int,int) - startTime, EndTime
    //2d array - Job0: pair(0,x)
    public Times[][] times;
    
    //startup time per job (and meybe second per machine)
    public int[] StartupTimesPerJob;
    public int[] StartupTimesPerMachine;
   
    
    public TreeData() {
        howManyTimesOccurs = new int[METADATA.TASKS_COUNT];
        numberOfTasksOnWrongMachine = 0;
        toManyChilds = 0;
        machinesWithoutChilds = 0;
        
        times = new Times[METADATA.JOBS_COUNT][METADATA.TASKS_PER_JOB];
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
        
        for(int i = 0; i < times.length; ++ i){
            for(int j = 0; j < times[0].length; ++j){
                times[i][j] = new Times();
            }
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
        String str = "";
        str += "howManyTimesOccurs=" + Arrays.toString(howManyTimesOccurs) + "\n times=\n";
        
        for(int i = 0; i < times.length; ++i){
            str += Arrays.toString(times[i]);
            str += "\n";
        }
        
        str += "perJob: " + Arrays.toString(StartupTimesPerJob);
        str += "\nperMachine: " + Arrays.toString(StartupTimesPerMachine) + "\n";
        
        str += numberOfTasksOnWrongMachine + ", " + toManyChilds + ", " + machinesWithoutChilds + '}';
        
        return str;
    }
    
    @Override
    public Object clone() {
        TreeData bd = new TreeData();
        bd.howManyTimesOccurs = howManyTimesOccurs.clone();
        bd.times = times.clone();
        bd.StartupTimesPerJob = StartupTimesPerJob.clone();
        bd.StartupTimesPerMachine = StartupTimesPerMachine.clone();
        return bd;
    }

    @Override
    public void copyTo(GPData gpd) {
        ((TreeData)gpd).howManyTimesOccurs = howManyTimesOccurs.clone();
        ((TreeData)gpd).times = times.clone();
        ((TreeData)gpd).StartupTimesPerJob = StartupTimesPerJob.clone();
        ((TreeData)gpd).StartupTimesPerMachine = StartupTimesPerMachine.clone();
    }
    
}
