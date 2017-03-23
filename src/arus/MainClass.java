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
        // TODO code application logic here
        boolean isLoaded = METADATA.load("ta12.txt", "ta12_seq.txt", true);        
        //METADATA.print();
        
        String[] Params = {"-file", "src\\arus\\example\\example.params"}; 
        //String[] Params = {"-file", "src\\ec\\app\\twobox\\noadf.params"};
        Evolve.main(Params);
        
    }
}
