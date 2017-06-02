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
    private boolean hideName = true;
    
    @Override
    public String toString() {
        if(hideName == true) return ".";
        else return "M" + iD;
    }
    
    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        //Calculating everything from the bottom to the top
        TreeData data = ((TreeData)input); 
        
        int x = 0;
        boolean haveTask = false;
        for(int i = 0; i < children.length; ++i){
            //eval childrens
            children[i].eval(state, thread, input, stack, individual, problem);
            
            //check if task is on good machine
            if(children[i].getClass().getPackage().getName().endsWith("tasks")){
                haveTask = true;
                TaskNode tn = ((TaskNode)children[i]);
                if(getID() != tn.task.requiredMachineID){
                    data.numberOfTasksOnWrongMachine += 1;
                }
            }
        }
        
        //hiding name of machine without childs
        hideName = !haveTask;
        
        //check if machine have task child
        if(!haveTask && children.length == 1){
            data.machinesWithoutChilds += 1;
        }
        
        //check if machine have only one child task
        if(children.length > 1){
            if(children[0].getClass().getPackage().getName().endsWith("tasks") && children[1].getClass().getPackage().getName().endsWith("tasks")){
                data.toManyChilds += 1;
            }   
        }
    }
    
}
