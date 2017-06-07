package algorithm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
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
    
    public static boolean showWindows = true;
    
    public static void main(String[] args) throws IOException {       
        
        try{
            if(showWindows){
                MainWindow window = new MainWindow();
            }
            
            String path = "src" + File.separator;
            
            //load tasks data
            METADATA.load(path + "test.txt", true); 
            
            //run gp
            String[] Params = {"-file", path + "test.params"};            
            CustomEvolve.main(Params);

        }catch(IOException e){
            System.out.println(e.getMessage());
        }        
    }
}
