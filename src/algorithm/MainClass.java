package algorithm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import window.MainWindow;

/**
 *
 * @author arus2
 */
public class MainClass {
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {         
        
        //create window
        MainWindow window = null;
        try {
            window = new MainWindow();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //run gp
        CustomEvolve.evolve();
    }
}
