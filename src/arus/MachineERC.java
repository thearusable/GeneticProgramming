/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.ERC;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Code;

/**
 *
 * @author arsc
 */
public class MachineERC extends ERC {

    public int ID;
    
    @Override
    public void mutateERC(EvolutionState state, int thread) {
        ID = METADATA.getSimilarMachine(ID);
    }

    @Override
    public String toString() {
        return "M" + ID;
    }

    @Override
    public int expectedChildren() {
        return 1;
    }

    @Override
    public String name() {
        return "ERCM";
    }

    @Override
    public void resetNode(EvolutionState es, int i) {
        ID = METADATA.getRandomMachineID();
    }

    @Override
    public boolean nodeEquals(GPNode gpnode) {
        return (gpnode.getClass() == this.getClass() && ((MachineERC)gpnode).ID == ID);
    }

    @Override
    public String encode() {
        return Code.encode(ID);
    }

    @Override
    public void eval(EvolutionState es, int i, GPData gpdata, ADFStack adfs, GPIndividual gpi, Problem prblm) {
        TreeData data = (TreeData)gpdata;
        
        for(int x = 0; x < children.length; ++x){
            if(children[x].getClass() != TaskNodeERC.class){
                data.numberOfTasksOnWrongMachine += 1;
            }
            
            if(children[x].getClass() == TaskNodeERC.class){
                TaskNodeERC t = (TaskNodeERC)children[x];
                if(t.task.requiredMachineID != ID){
                    data.numberOfTasksOnWrongMachine += 1;
                }
            }
            
            children[x].eval(es, i, data, adfs, gpi, prblm);
        }
    }
    
}
