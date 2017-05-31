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
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.management.InvalidApplicationException;
import window.GraphViz;

/**
 *
 * @author arsc
 */
public class MyStatistics extends SimpleStatistics {
    
    /** log best file parameter */
    public static final String P_PNG_FILE = "png";
    
    public File bestPNGFile;
    
    @Override
    public void setup(final EvolutionState state, final Parameter base){
        super.setup(state,base);
        
        bestPNGFile = state.parameters.getFile(base.push(P_PNG_FILE),null);
    }
    
    @Override
    public void finalStatistics(final EvolutionState state, final int result){
        super.finalStatistics(state,result);
        
        try{
            GPIndividual BestSoFarInd = (GPIndividual)best_of_run[0];
            String dotTree = BestSoFarInd.trees[0].child.makeGraphvizTree();

            System.out.println("best: " + bestPNGFile.getAbsolutePath());
            
            //change to multiplatform path
            String tempPath = Paths.get("").toAbsolutePath().toString();
            
            //init grapviz and pass tree data
            GraphViz gv = new GraphViz(tempPath, true);
            gv.add(dotTree);
            System.out.println(gv.getDotSource());
            
            //create png file
            gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), "png" ), bestPNGFile );
            
        } catch (Exception e) {
            // do something
        }
    }
    
}
