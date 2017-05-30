/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

/**
 *
 * @author arsc
 */
public abstract class MachineNode extends GPNode {

    public abstract int getID();
    
    public MachineNode() {
        iD = getID();
    }
    
    public int iD;
    
    @Override
    public String toString() {
        return "M" + iD;
    }
    
    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        //TODO
        TreeData data = ((TreeData)input); 
        
        int x = 0;
        for(int i = 0; i < children.length; ++i){
            //eval childrens
            children[i].eval(state, thread, input, stack, individual, problem);
            
            //check if machine is good
            if(children[i].getClass().getPackage().getName().endsWith("tasks")){
                TaskNode tn = ((TaskNode)children[i]);
                if(getID() != tn.task.requiredMachineID){
                    data.numberOfTasksOnWrongMachine += 1;
                    x += 1;
                }
            }
        }
        
        //if machine have to many tasks
        if(x > 1){
            data.toManyChilds += x - 1;
        }
        
    }
    
}
