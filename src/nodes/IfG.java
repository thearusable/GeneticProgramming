/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

/**
 *
 * @author areks
 */
public class IfG extends GPNode{
    @Override
    public String toString() {
        return "G";
    }

    @Override
    public void eval(EvolutionState es, int i, GPData gpdata, ADFStack adfs, GPIndividual gpi, Problem prblm) {
        DataNode data = (DataNode) gpdata;

        children[0].eval(es, i, data, adfs, gpi, prblm);
        double a = data.value;
        children[1].eval(es, i, data, adfs, gpi, prblm);
        double b = data.value;
        
        if(a > b){
            data.value = a;
        }
        else{
            data.value = b;
        }
    }
}
