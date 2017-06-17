/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

import algorithm.JobsSchedulingProblem;
import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.simple.SimpleProblemForm;
import ec.simple.SimpleStatistics;
import ec.util.Parameter;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author arsc
 */
public class MyStatistics extends SimpleStatistics {
    
    
    public static final String P_PNG_FILE = "png";
    
    public File bestPNGFile;
    
    @Override
    public void setup(final EvolutionState state, final Parameter base){
        super.setup(state,base);
        
        //read path to png file from params
        bestPNGFile = state.parameters.getFile(base.push(P_PNG_FILE),null);
    }
    
    @Override
    public void finalStatistics(final EvolutionState state, final int result){
        super.finalStatistics(state,result);
        
        try{
            //get dot tree
            GPIndividual BestSoFarInd = (GPIndividual)best_of_run[0];
            String dotTree = BestSoFarInd.trees[0].child.makeGraphvizTree();
            
            //init grapviz and pass tree data
            GraphViz gv = new GraphViz(false);
            gv.add(dotTree);
            
            //create png file
            gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), P_PNG_FILE ), bestPNGFile );
            
            //unlock show png button
            MainWindow.updateBestPath(bestPNGFile.getCanonicalPath());
            
        } catch (IOException e) {
            // do something
            System.out.println("Final Statistics Fails!");
        }
    }
    
    boolean warned = false;
    @Override
    public void postEvaluationStatistics(final EvolutionState state){
        //super.postEvaluationStatistics(state);
        
        //set generation number
        MainWindow.updateGenerationNumber(state.generation);
        
        // for now we just print the best fitness per subpopulation.
        Individual[] best_i = new Individual[state.population.subpops.length];  // quiets compiler complaints
        for(int x=0;x<state.population.subpops.length;x++)
            {
            best_i[x] = state.population.subpops[x].individuals[0];
            for(int y=1;y<state.population.subpops[x].individuals.length;y++)
                {
                if (state.population.subpops[x].individuals[y] == null)
                    {
                    if (!warned)
                        {
                        state.output.warnOnce("Null individuals found in subpopulation");
                        warned = true;  // we do this rather than relying on warnOnce because it is much faster in a tight loop
                        }
                    }
                else if (best_i[x] == null || state.population.subpops[x].individuals[y].fitness.betterThan(best_i[x].fitness))
                    best_i[x] = state.population.subpops[x].individuals[y];
                if (best_i[x] == null)
                    {
                    if (!warned)
                        {
                        state.output.warnOnce("Null individuals found in subpopulation");
                        warned = true;  // we do this rather than relying on warnOnce because it is much faster in a tight loop
                        }
                    }
                }
        
            // now test to see if it's the new best_of_run
            if (best_of_run[x]==null || best_i[x].fitness.betterThan(best_of_run[x].fitness))
                best_of_run[x] = (Individual)(best_i[x].clone());
            }
        
        // print the best-of-generation individual
        if (doGeneration) state.output.println("\nGeneration: " + state.generation,statisticslog);
        if (doGeneration) state.output.println("Best Individual:",statisticslog);
        for(int x=0;x<state.population.subpops.length;x++){
            
            //calc average fitness
            double avgF = 0.0;
            for(int i = 0; i < state.population.subpops[x].individuals.length; ++i){
                avgF += state.population.subpops[x].individuals[i].fitness.fitness();
            }
            avgF /= state.population.subpops[x].individuals.length;
            
            //add data to graph
            if(state.generation > 0){
                MainWindow.addEntryToGraph(state.generation, best_i[x].fitness.fitness(), avgF);
            }
            
            if (doGeneration) state.output.println("Subpopulation " + x + ":",statisticslog);
            if (doGeneration) best_i[x].printIndividualForHumans(state,statisticslog);
            if (doMessage && !silentPrint) state.output.message("Subpop " + x + " best fitness of generation" + 
                (best_i[x].evaluated ? " " : " (evaluated flag not set): ") +
                best_i[x].fitness.fitnessToStringForHumans());
                
            // describe the winner if there is a description
            if (doGeneration && doPerGenerationDescription) 
                {
                if (state.evaluator.p_problem instanceof SimpleProblemForm)
                    ((SimpleProblemForm)(state.evaluator.p_problem.clone())).describe(state, best_i[x], x, 0, statisticslog);   
                }   
            }

        
        //set minimum fitness
        double minF = getBestSoFar()[0].fitness.fitness();
        
        MainWindow.updateMinimumFitness(minF);
    }
    
}
