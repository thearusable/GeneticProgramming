/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.StringTokenizer;

/**
 *
 * @author areks
 */
public class SingleProblemData {
    
    // TODO
    // Generowanie czasow wejscia
    // przygotowanie danych z kazdego rozmiaru po jednej (conajmniej)
    // zmiana metody przechowywania danych ???
    
    public static int JOBS_COUNT = 0;
    public static int MACHINES_COUNT = 0;
    public static int TASKS_PER_JOB = 0;
    public static int TASKS_COUNT = 0;
    public static int LOWEST_TASK_DURATION = Integer.MAX_VALUE;
    public static int LONGEST_TASK_DURATION = Integer.MIN_VALUE;
    public static double AVERAGE_TASK_DURATION = 0.0;
    
    private static TaskData [] tasks;
    
    public static int BEST_RESULT_FROM_WEB = 0;
    
    public void load(String dataFile, boolean debug) throws IOException {
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
                BEST_RESULT_FROM_WEB = readedData[2];
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
        double allDurationsSummed = 0;
        for(int x = 0; x < times.length; ++x){ //JobID
            for(int y =0; y < times[0].length; ++y){ //taskInJob
                tasks[tasksCounter] = new TaskData(tasksCounter, y, x, times[x][y], machines[x][y]);
                tasksCounter += 1;
                
                if(times[x][y] > LONGEST_TASK_DURATION) LONGEST_TASK_DURATION = times[x][y];
                if(times[x][y] < LOWEST_TASK_DURATION) LOWEST_TASK_DURATION = times[x][y];
                allDurationsSummed += times[x][y];
            }
        }
        
        AVERAGE_TASK_DURATION = allDurationsSummed / tasksCounter;
        
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
    
    static private void print(){
        System.out.println("\nTASKS:");
        
        for(int i = 0; i < tasks.length; ++i){
            System.out.println(tasks[i].toString());
        }
        
        System.out.println("JOBS_COUNT: \t\t" + JOBS_COUNT);
        System.out.println("MACHINES_COUNT: \t" + MACHINES_COUNT);
        System.out.println("TASKS_PER_JOB: \t" + TASKS_PER_JOB);
        System.out.println("TASKS_COUNT: \t" + TASKS_COUNT);
        System.out.println("LOWEST_TASK_DURATION: \t" + LOWEST_TASK_DURATION);
        System.out.println("LONGEST_TASK_DURATION: \t" + LONGEST_TASK_DURATION);
        System.out.println("AVERAGE_TASK_DURATION: \t" + AVERAGE_TASK_DURATION);
        System.out.println("BEST_RESULT_FROM_WEB: \t" + BEST_RESULT_FROM_WEB);
    }

    void load(Path path, boolean b) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
