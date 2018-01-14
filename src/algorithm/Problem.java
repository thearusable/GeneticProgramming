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
import nodes.DataNode;
import window.MainWindow;
import window.OrderChart;

/**
 *
 * @author areks
 */
public class Problem extends GPProblem implements SimpleProblemForm {

    @Override
    public void evaluate(EvolutionState state, Individual ind, int i, int threadnum) {
        
        double fitness = 0.0;
        
        DataNode data = (DataNode)(this.input);
        
        GPIndividual GPInd = (GPIndividual)ind;
        GPNode root = GPInd.trees[0].child;
            
        //collect data
        root.eval(state, threadnum, data, stack, GPInd, this);
        
        boolean isGood = false;
        if(data.value > 100)
        {
            isGood = true;
        }
        
        //Assing calculated fitness
        ((LowerBetterFitness) ind.fitness).setFitness(state, fitness, isGood);
        //mark individual as evaluated
        ind.evaluated = true;
        //reset collect data in TreeData 
        data.reset();
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
}
