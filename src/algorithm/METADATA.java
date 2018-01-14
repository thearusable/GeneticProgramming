/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import ec.util.Code;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 *
 * @author arus2
 */
public class METADATA {
    
    private static int lastTask = -1;
    private static int lastMachine = -1;
    
    public static int PROBLEMS_COUNT = 0;
    
    public static double [] lastResults; 
    private static SingleProblemData [] problems;
    /*
    static public int getNextTask(){
        lastTask += 1;
        
        if(lastTask >= tasks.length) lastTask = 0;
        
        return lastTask;
    }
    
    static public int getNextMachine(){
        lastMachine += 1;
        
        if(lastMachine >= MACHINES_COUNT) lastMachine = 0;
        
        return lastMachine;
    }
    
    static public String getCodeForTask(int taskID){
        return Code.encode("T") + Code.encode(tasks[taskID].jobID) + Code.encode(tasks[taskID].requiredMachineID) 
                + Code.encode(tasks[taskID].whichTaskInJob) + Code.encode(tasks[taskID].duration);
    }
    
    static public String getStringForTask(int taskID){
        return "j" + tasks[taskID].jobID + " t" + tasks[taskID].whichTaskInJob + " m" + tasks[taskID].requiredMachineID + " D" + tasks[taskID].duration + " w" + tasks[taskID].whichTaskInJob;
    }
    
    static public TaskData getTask(int whichOne){
        if(whichOne > tasks.length){
            System.out.println("That tasks doeas not exist");
            throw new ArrayIndexOutOfBoundsException();
        }else{
            return tasks[whichOne];
        }
    }
    
    static public String getTaskString(int startTime, int taskID){
        return startTime + "_(" + tasks[taskID].jobID + "," + tasks[taskID].whichTaskInJob + ")_" + (startTime +  tasks[taskID].duration);
    }
    */
    static public void loadAllDataFiles(boolean isDebugOn) throws IOException
    {
        System.out.println("load all data files");
        
        try (Stream<Path> paths = Files.walk(Paths.get("src/data/"))) {
            paths
                .filter(Files::isRegularFile)
                .forEach(System.out::println);
        } 
    }
}
