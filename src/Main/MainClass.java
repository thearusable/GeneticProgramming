/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import JobsScheduling.METADATA;
import ec.Evolve;
import java.io.IOException;

/**
 *
 * @author arus2
 */
public class MainClass {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        METADATA.load("ta60.txt", "ta60_seq.txt", true);        
        //METADATA.print();
        
        String[] Params = {"-file", "src\\JobsScheduling\\jobs.params"}; 
        Evolve.main(Params);
        
    }
}
