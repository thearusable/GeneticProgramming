package algorithm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
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
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, EngineException {
        
        //check if matlab have shared session
        String[] engines = MatlabEngine.findMatlab();
        if (engines.length == 0) {
            throw new ExceptionInInitializerError("MATLAB IS NOT RUNNING!");
        }

        //create window
        MainWindow window = null;
        try {
            window = new MainWindow();
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        //run gp
        CustomEvolve.evolve(false, "src/problem.params");

    }
}
