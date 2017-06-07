package algorithm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    
    //args[0] - problem name
    public static void main(String[] args) throws IOException {       
        if(showWindows){
            MainWindow window = new MainWindow();
        }
        
        String problemName;
        
        if(args.length < 1) {
            problemName = "test";
        }else{
            //someday remowing extension here
            problemName = args[0];
        }
        
        String path = Paths.get("").toAbsolutePath().toString() + File.separator;
        
        try{ 
            //load tasks data
            METADATA.load(path + problemName + ".txt", true); 
            
        }catch(IOException e){
            
            path += "src" + File.separator;
            
            try{ 
            //load tasks data
            METADATA.load(path + problemName + ".txt", true); 
            }catch(IOException e2){
                System.exit(0);
            }
        }

        //run gp
        String[] Params = {"-file", path + problemName + ".params"};            
        CustomEvolve.main(Params);
    }
}
