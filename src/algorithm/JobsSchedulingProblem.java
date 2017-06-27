/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPProblem;
import ec.simple.SimpleProblemForm;
import static ec.gp.GPProblem.P_DATA;
import ec.util.Parameter;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import window.MainWindow;
import window.OrderChart;

/**
 *
 * @author arus2
 */
public class JobsSchedulingProblem extends GPProblem implements SimpleProblemForm {
    
    public static final String P_MAKESPAN = "makespan";
    public static final String P_DOUBLING = "doubling";
    public static final String P_MISSING = "missing";
    public static final String P_BADORDER = "badOrder";
    public static final String P_BADMACHINE = "badMachine";
    public static final String P_SINGLEMBAD = "singleMBad";
    
    static private double makespanWeight;
    static private double doublingTaskWeight;
    static private double missingTaskWeight;
    static private double taskInWrongOrderErrorWeight;
    static private double taskOnBadMachineErrorWeight;
    static private double singleMachineWithBadChildErrorWeight;
    
    @Override
    public void setup(final EvolutionState state, final Parameter base){
        super.setup(state, base);
        // verify our input is the right class (or subclasses from it)
        if (!(input instanceof TreeData)){
            state.output.fatal("GPData class must subclass from " + TreeData.class, base.push(P_DATA), null);      
        }
        
        //read weights from params file
        makespanWeight = state.parameters.getDouble(new Parameter(P_MAKESPAN), null);
        doublingTaskWeight = state.parameters.getDouble(new Parameter(P_DOUBLING), null);
        missingTaskWeight = state.parameters.getDouble(new Parameter(P_MISSING), null);
        taskInWrongOrderErrorWeight = state.parameters.getDouble(new Parameter(P_BADORDER), null);
        taskOnBadMachineErrorWeight = state.parameters.getDouble(new Parameter(P_BADMACHINE), null);
        singleMachineWithBadChildErrorWeight = state.parameters.getDouble(new Parameter(P_SINGLEMBAD), null);
        
        //print weights
        printErrorsWeights();
    }
        
    @Override
    public void evaluate(final EvolutionState state, 
        final Individual ind, 
        final int subpopulation,
        final int threadnum){
            
        if (!ind.evaluated)
            {
            double fitness = 0.0;

            TreeData data = (TreeData)(this.input);
            
            GPIndividual GPInd = (GPIndividual)ind;
            GPNode root = GPInd.trees[0].child;
            
            //collect data
            root.eval(state, threadnum, data, stack, GPInd, this);
            
            //penality for number of each task
            for(int i = 0; i < data.OccursCounterPerTask.length; ++i){
                if(data.OccursCounterPerTask[i] > 1){
                    fitness += (data.OccursCounterPerTask[i] - 1) * doublingTaskWeight;
                }else if(data.OccursCounterPerTask[i] < 1){
                    fitness += missingTaskWeight;
                }
            }
            
            //tasks on bad machine
            fitness += data.taskOnBadMachine * taskOnBadMachineErrorWeight;
            
            //tasks in wrong order
            fitness += data.taskInWrongOrder * taskInWrongOrderErrorWeight;
            
            //small clean of tree
            fitness += data.singleMachineWithBadChild * singleMachineWithBadChildErrorWeight;
            
            //adding makespan to fitness
            int onlyMakespan = data.getMakespan();
            
            //add makespan to fitness
            fitness += onlyMakespan * makespanWeight;

            //Assing calculated fitness
            ((LowerBetterFitness) ind.fitness).setFitness(state, fitness, false);
            //mark individual as evaluated
            ind.evaluated = true;
            //reset collect data in TreeData 
            data.reset();
            }
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
        }
        
        OrderChart.buildDataset(data.order);
        
        System.out.println(data.toString());
    }
    
    private void printErrorsWeights(){
        String str = "\nWeights:";
        str += "\nmakespanWeight: " + makespanWeight;
        str += "\ndoublingTaskWeight: " + doublingTaskWeight;
        str += "\nmissingTaskWeight: " + missingTaskWeight;
        str += "\ntaskInWrongOrderErrorWeight: " + taskInWrongOrderErrorWeight;
        str += "\ntaskOnBadMachineErrorWeight: " + taskOnBadMachineErrorWeight;
        str += "\nsingleMachineWithBadChildErrorWeight: " + singleMachineWithBadChildErrorWeight;

        str += "\n";
        
        System.out.println(str);
    }
}
