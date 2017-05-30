/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus;

//import JobsScheduling.METADATA;
import ec.Evolve;
import java.io.IOException;

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

        boolean isLoaded = METADATA.load("test.txt", "test_seq.txt", true);        
        
        if(isLoaded == false){
            System.out.println("File cannot be readed.");
            return;
        }
        
        String[] Params = {"-file", "src\\arus\\problems\\test.params"}; 
        
        Evolve.main(Params);
        
    }
}
