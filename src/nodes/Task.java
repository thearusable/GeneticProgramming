/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;


import algorithm.METADATA;
import algorithm.TreeData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

/**
 *
 * @author arsc
 */
public class Task extends ERCnode{
    
    public Task(){
        //setID(METADATA.getNextTask());
    }
    
    public int getRequiredMachineID(){
        //return METADATA.getTask(getID()).requiredMachineID;
        return 0;
    }
    
    @Override
    public int expectedChildren() {
        return 0;
    }

    @Override
    public String name() {
        return "T";
    }

    @Override
    public String toString() {
        //return METADATA.getStringForTask(getID());
        return "";
    }
        
    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem){
        
        TreeData data = ((TreeData)input); 
        
        data.task(getID(), parent);
    }

    @Override
    public String encode() {
        //return METADATA.getCodeForTask(getID());
        return "";
    }
    
    @Override
    public Object clone() {
        Task task = (Task)super.clone();
        task.setID(getID());
        return task;
    }

    @Override
    public void resetNode(EvolutionState es, int i) {
        //setID(METADATA.getNextTask());
    }
    
}
