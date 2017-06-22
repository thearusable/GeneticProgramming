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
public abstract class Machine extends ERCnode{

    boolean hideName = true;
    
    @Override
    public String toString() {
        if(hideName == true){
            return "";
        }else{
            return super.toString();
        }
    }
    
    @Override
    public String name() {
        return "M";
    }

    @Override
    public void eval(EvolutionState es, int i, GPData gpdata, ADFStack adfs, GPIndividual gpi, Problem prblm) {

        hideName = true;
        for(int x = 0; x < children.length; ++x){          
            children[x].eval(es, i, gpdata, adfs, gpi, prblm);
            
            if(children[x].getClass().getName().contains("Task")){
                hideName = false;
            }
        }
        
    }

    @Override
    public void mutate() {
        setID(METADATA.getMachineSiblingID(getID()));
    }
    
    @Override
    public void reset() {
        setID(METADATA.getRandomMachineID());
    }

}
