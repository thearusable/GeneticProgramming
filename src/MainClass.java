/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//import JobsScheduling.METADATA;
import arus.CustomEvolve;
import java.io.IOException;
import arus.METADATA;
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
    public static void main(String[] args) throws IOException {       
        
        try{
            MainWindow window = new MainWindow();
            //load tasks data
            METADATA.load("test.txt", true); 
            
            //print styles 
            // -p -...
            // gp.tree.print-style=c
            // gp.tree.print-style=dot
            // gp.tree.print-style=latex
            String[] Params = {"-file", "src\\problems\\test.params"}; 
            
            //run gp
            CustomEvolve.main(Params);
 
        }catch(IOException e){
            System.out.println(e.getMessage());
        }        
    }
}
