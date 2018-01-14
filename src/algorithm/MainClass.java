package algorithm;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
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
        //create window
        /*
        MainWindow window = new MainWindow();
        
        //print startup params
        System.out.println("Startup params: \t" + Arrays.toString(args));
        
        //get path to current folder
        String path = Paths.get("").toAbsolutePath().toString() + File.separator;
        String problemName = "";
        
        for(int i = 0; i < args.length; ++i){
            //check if its run from IDE
            if(args[i].equals("-IDE")){
                path += "src" + File.separator;
            }else if (args[i].contains("-problem=")){ //check for problem name
                problemName = args[i].substring(args[i].lastIndexOf("=") + 1);
            }
        }
        
        //check if found a paramsFile name
        if(problemName.isEmpty()){
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }catch(ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            }
            //create file chooser window
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(path));
            //set filter
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PARAMS FILES", "params");
            chooser.setFileFilter(filter);
            //read choosed file
            int result = chooser.showOpenDialog(chooser);
            if(result == JFileChooser.APPROVE_OPTION){
                path = chooser.getSelectedFile().getAbsolutePath();
            }        
        }else{
            //add file extension
            path += problemName + ".params";
        }
        
        //print path
        System.out.println("Path to params file: \t" + path);

        */
        
        SingleProblemData data = new SingleProblemData();
        
        data.load("src/data/p9.txt", true);
        
        //run gp
        //String[] Params = {"-file", "src/problem.params"};            
        //CustomEvolve.main(Params);
    }
}
