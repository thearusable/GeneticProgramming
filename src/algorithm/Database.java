/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author areks
 */
public class Database {
    public static final ArrayList < SingleProblemData > problems = new ArrayList<>();
    
    public static boolean loadAllProblems(boolean debug){
       
        File[] files = new File("src/data/").listFiles();
        
        for(File file : files){
            if(file.isFile()){
                SingleProblemData spd = new SingleProblemData();
                try {
                    spd.load(file.getPath().toString(), debug);
                } catch (IOException ex) {
                    Logger.getLogger(SchedulingProblem.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                problems.add(spd);
            }
        }
        return true;
    }
}
