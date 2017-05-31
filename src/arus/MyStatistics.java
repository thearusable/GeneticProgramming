/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus;

import ec.EvolutionState;
import ec.gp.GPIndividual;
import ec.simple.SimpleStatistics;
import ec.util.Parameter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.management.InvalidApplicationException;
import window.GraphViz;

/**
 *
 * @author arsc
 */
public class MyStatistics extends SimpleStatistics {
    
    /** log best file parameter */
    public static final String P_BEST_FILE = "best";
    
    public File bestFile;
    
    @Override
    public void setup(final EvolutionState state, final Parameter base){
        super.setup(state,base);
        
        bestFile = state.parameters.getFile(base.push(P_BEST_FILE),null);
    }
    
    @Override
    public void finalStatistics(final EvolutionState state, final int result){
        super.finalStatistics(state,result);
        
        try{
            //create or override file
            PrintWriter writer = new PrintWriter(bestFile.getAbsolutePath(), "UTF-8");
            
            GPIndividual BestSoFarInd = (GPIndividual)best_of_run[0];
            //write dot tree to file
            writer.println(BestSoFarInd.trees[0].child.makeGraphvizTree());
            //close file
            writer.close();
            
            //test dot 
            GraphViz gv = new GraphViz();
            gv.addln(gv.start_graph());
            gv.addln("A -> B;");
            gv.addln("A -> C;");
            gv.addln(gv.end_graph());
            System.out.println(gv.getDotSource());
            
            String type = "gif";
            File out = new File("out." + type);   // out.gif in this example
            gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
            
        } catch (IOException e) {
            // do something
        }
    }
    
    public static void createDotGraph(String dotFormat,String fileName){
        GraphViz gv=new GraphViz();
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        // String type = "gif";
        String type = "pdf";
        // gv.increaseDpi();
        gv.decreaseDpi();
        gv.decreaseDpi();
        File out = new File(fileName+"."+ type); 
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
    }
    
}
