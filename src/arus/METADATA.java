/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus;

import ec.util.Parameter;
import ec.util.ParameterDatabase;
import java.io.BufferedReader;
import java.io.File;
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
    
    private static Task [] tasks;
    
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
        
        tasks = new Task[JOBS_COUNT * times[0].length];
        
        TASKS_COUNT = JOBS_COUNT * times[0].length;
        TASKS_PER_JOB = times[0].length;
        
        //build tasks list
        int tasksCounter = 0;
        for(int x = 0; x < times.length; ++x){ //JobID
            for(int y =0; y < times[0].length; ++y){ //taskInJob
                tasks[tasksCounter] = new Task(y, x, times[x][y], machines[x][y]);
                tasksCounter += 1;
            }
        }
        //debug print
        if(debug == true){
            print();
        }
    }
    
    static public Task getRandomTask(){
        Random rand = new Random();
        return tasks[ rand.nextInt(tasks.length + 1) ];
    }
    
    static public Task getTask(int whichOne){
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
