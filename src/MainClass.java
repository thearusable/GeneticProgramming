/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//import JobsScheduling.METADATA;
import ec.Evolve;
import java.io.IOException;
import arus.METADATA;

/**
 *
 * @author arus2
 */
public class MainClass {
     /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {       

        try{
            //load tasks data
            METADATA.load("test.txt", true); 
            
            //set problem params
            String[] Params = {"-file", "src\\arus\\problems\\test.params"}; 
            
            //run gp
            Evolve.main(Params);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }        
    }
}
