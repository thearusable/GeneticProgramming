/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//import JobsScheduling.METADATA;
import ec.Evolve;
import java.io.IOException;
import arus.METADATA;
import ec.EvolutionState;
import ec.util.Parameter;
import ec.util.ParameterDatabase;
import java.io.File;
import graph.GraphViz;

/**
 *
 * @author arus2
 */
public class MainClass {
     /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */

    static Parameter param = new Parameter("data");

    public static void main(String[] args) throws IOException {       
     
        try{
            ParameterDatabase pm = new ParameterDatabase();
        
            File test = pm.getFile(param,null);
        
            System.out.println(test.getAbsolutePath());
        
        }catch(Exception e){
            System.out.println("data param not loaded");
            return;
        }
        
        try{
            //load tasks data
            METADATA.load("test.txt", true); 
            
            //set problem params
            // gp.tree.print-style=c
            // gp.tree.print-style=dot
            // gp.tree.print-style=latex
            String[] Params = {"-file", "src\\problems\\test.params", "-p", ""}; 
            
            //run gp
            Evolve.main(Params);
            
        }catch(IOException e){
            System.out.println(e.getMessage());
        }        
    }
}
