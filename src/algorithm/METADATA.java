/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

/**
 *
 * @author arus2
 */
public class METADATA {
    
    public static int JOBS_COUNT;
    public static int MACHINES_COUNT;
    public static int TASKS_PER_JOB;
    public static int TASKS_COUNT;
    public static int MAX_TASK_DURATION;
    
    private static TaskData [] tasks;
    
    static public int getJobID(int taskID){
        return tasks[taskID].jobID;
    }
    
    static public int getTaskDuration(int taskID){
        return tasks[taskID].duration;
    }
    
    static public void load(String dataFile, boolean debug) throws IOException {
        int[][] times = new int[0][0];
        int[][] machines = new int[0][0];
        
        BufferedReader reader = new BufferedReader(new FileReader(dataFile)); 
        String line;
        int[] readedData;
        int stage = 1;
        int currentReadedJob = 0;
            
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
                JOBS_COUNT = readedData[0];
                MACHINES_COUNT = readedData[1];
                times = new int[JOBS_COUNT][MACHINES_COUNT];
                machines = new int[JOBS_COUNT][MACHINES_COUNT];
            }
            //times
            if(stage == 2){
                if(line.contains("Machines")){
                    currentReadedJob = 0;
                    stage = 3;
                    continue;
                }
                readedData = split(line);
                times[currentReadedJob] = readedData;
                currentReadedJob++;
            }
            //machines
            if(stage == 3){
                readedData = split(line);
                machines[currentReadedJob] = readedData;
                currentReadedJob++;
            }
        }
        
        tasks = new TaskData[JOBS_COUNT * times[0].length];
        
        TASKS_COUNT = JOBS_COUNT * times[0].length;
        TASKS_PER_JOB = times[0].length;
        
        //build tasks list
        int tasksCounter = 0;
        for(int x = 0; x < times.length; ++x){ //JobID
            for(int y =0; y < times[0].length; ++y){ //taskInJob
                tasks[tasksCounter] = new TaskData(tasksCounter, y, x, times[x][y], machines[x][y]);
                tasksCounter += 1;
                
                if(times[x][y] > MAX_TASK_DURATION) MAX_TASK_DURATION = times[x][y];
            }
        }
        //debug print
        if(debug == true){
            print();
        }
    }
    
    static public TaskData getRandomTask(){      
        Random rand = new Random();
        return tasks[ rand.nextInt(tasks.length) ];
    }
    
    static public TaskData getCloseTaskWithinJob(int taskID){
        double rand = Math.random();
        boolean getGreater;
        if(rand > 0.5){
            getGreater = true;
        }else{
            getGreater = false;
        }
        
        if(getGreater == true && (taskID + 1) >= tasks.length ){
            getGreater = false;
        }else if (getGreater == false && (taskID - 1) < 0){
            getGreater = true;
        }
        
        if(getGreater == true){
            return tasks[taskID + 1];
        }else {
            return tasks[taskID - 1];
        }
    }
    
    static public int getSimilarMachine(int machineID){
        double rand = Math.random();
        boolean getGreater;
        if(rand > 0.5){
            getGreater = true;
        }else{
            getGreater = false;
        }
        
        if(getGreater == true && (machineID + 1) >= MACHINES_COUNT ){
            getGreater = false;
        }else if (getGreater == false && (machineID - 1) < 0){
            getGreater = true;
        }
        
        if(getGreater == true){
            return machineID + 1;
        }else {
            return machineID - 1;
        }
    }
    
    static public int getRandomMachineID(){
        return (int) (Math.random() * MACHINES_COUNT);
    }
    
    static public TaskData getTask(int whichOne){
        if(whichOne > tasks.length){
            System.out.println("That tasks doeas not exist");
            throw new ArrayIndexOutOfBoundsException();
        }else{
            return tasks[whichOne];
        }
    }
    
    static private void print(){
        System.out.println("TASKS:");
        
        for(int i = 0; i < tasks.length; ++i){
            System.out.println(tasks[i].toString());
        }
        
        System.out.println("MAX_TASK_DURATION: " + MAX_TASK_DURATION);
    }
    
    /////////////////////////////////////////////
    
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
}