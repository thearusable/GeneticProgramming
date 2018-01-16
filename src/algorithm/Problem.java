/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPProblem;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import nodes.DataNode;
import window.MainWindow;
import window.OrderChart;

/**
 *
 * @author areks
 */
public class Problem extends GPProblem implements SimpleProblemForm {
    
    @Override
    public void setup(final EvolutionState state, final Parameter base){
        super.setup(state, base);
        
        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof DataNode)){
            state.output.fatal("GPData class must subclass from " + DataNode.class, base.push(P_DATA), null);      
        }
    }
    
    @Override
    public void evaluate(EvolutionState state, Individual ind, int i, int threadnum) {
        
        if(ind.evaluated == true) return;
        
        double fitness = calculateFitness(((GPIndividual)ind).trees[0].child);
            
        ((LowerBetterFitness) ind.fitness).setFitness(state, fitness, false);
        ind.evaluated = true;
    }   
    
    //get makespan of individual
    public void printData(GPIndividual ind){
        TreeData data = new TreeData();
        
        GPNode root = ind.trees[0].child;
        EvolutionState state = new EvolutionState();
        root.eval(state, 0, data, stack, ind, this);

        if(data.isValidtree()){
            MainWindow.updateMinimumMakespan(Integer.toString(data.getMakespan()));
        }else{
            MainWindow.updateMinimumMakespan("Not Founded");
            System.out.println(data.toString());
        }
        
        OrderChart.buildDataset(data.order);
    }
    
    private void printErrorsWeights(){
        System.out.println("NOT IMPLEMENTED");
    }
    
    // GPNode przekzac do funkcji, tam uzupelnic o brakuje dane dotyczace taska.
    // wykonac obliczenia dla wszystkich problemow, wyciagnac srednia.
    private double calculateFitness(GPNode root){
        
        return 0.0;
    }
}
