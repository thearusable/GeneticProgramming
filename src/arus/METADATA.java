/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

/**
 *
 * @author arus2
 */
public class METADATA {
    public static int[][] times;
    public static int[][] machines;
    
    public static int JOBS_COUNT;
    public static int MACHINES_COUNT;
    public static int MAKESPAN_ORIGINAL;
    public static int TASKS_COUNT;
    
    private static Task [] tasks;
    
    static public boolean load(String dataFile, String resultsFile, boolean debug) throws IOException{
        LoadDataFile(dataFile);
        LoadSequenceFile(resultsFile);
        
        buildTasksList();        
        
        if(debug == true){
            print();
        }
        
        return true;
    }
    
    static public void buildTasksList(){
        int tasksCounter = 0;
        System.out.println("times.length: " + times.length + " times[0].length: " + times[0].length );
        for(int x = 0; x < times.length; ++x){ //JobID
            for(int y =0; y < times[0].length; ++y){ //taskInJob
                tasks[tasksCounter] = new Task(y, x, times[x][y], machines[x][y]);
                tasksCounter += 1;
            }
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
        System.out.println("Jobs: " + JOBS_COUNT + " MACHINES: " + MACHINES_COUNT + " MAKESPAN: " + MAKESPAN_ORIGINAL);
        
        if(times != null){
            System.out.println("Times:");
            for(int i =0; i<times.length; i++){
                for(int j = 0; j < times[i].length; j++){
                    System.out.print(times[i][j] + " ");
                }
                System.out.println();
            }
        }
        
        if(machines != null){
            System.out.println("Machines:");
            for(int i =0; i<machines.length; i++){
                for(int j = 0; j < machines[i].length; j++){
                    System.out.print(machines[i][j] + " ");
                }   
                System.out.println();
            }
        }
        
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
    
    private static boolean LoadDataFile(String DataFileName) throws FileNotFoundException, IOException{
        boolean returnValue = false;
        try ( // DataFile
            BufferedReader reader = new BufferedReader(new FileReader(DataFileName))) {
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
            returnValue = true;
        }
        return returnValue;
    }
    
    private static boolean LoadSequenceFile(String SequenceFileNamme) throws FileNotFoundException, IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(SequenceFileNamme))) {
            String line;
            int[] readedData;
            
            while((line = reader.readLine()) != null){
                //ignore comments
                if(isComment(line) || line.length() == 0) continue;

                readedData = split(line);
                MAKESPAN_ORIGINAL = readedData[2];    

                return true;
            }
        }
        return true;
    }
}
