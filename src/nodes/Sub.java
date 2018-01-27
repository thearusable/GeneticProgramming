/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import algorithm.TreeData;
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
public class Sub extends GPNode {
    @Override
    public String toString() {
        return "S";
    }
    
    @Override
    public int expectedChildren(){
        return 2;
    }

    @Override
    public void eval(EvolutionState es, int i, GPData gpdata, ADFStack adfs, GPIndividual gpi, Problem prblm) {
        TreeData data = (TreeData) gpdata;
        //a
        children[0].eval(es, i, data, adfs, gpi, prblm);
        double temp = data.value;
        //b
        children[1].eval(es, i, data, adfs, gpi, prblm);
        data.value = temp - data.value;
    }
}
