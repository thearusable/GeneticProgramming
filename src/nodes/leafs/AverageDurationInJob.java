/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes.leafs;

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
public class AverageDurationInJob extends GPNode {
    
    private static final long serialVersionUID = 1;
    
    @Override
    public String toString() {
        return "ADIJ";
    }
    
    @Override
    public int expectedChildren(){
        return 0;
    }

    @Override
    public void eval(EvolutionState es, int i, GPData gpdata, ADFStack adfs, GPIndividual gpi, Problem prblm) {
        TreeData data = ((TreeData)gpdata);
        data.value = data.AVERAGE_DURATION_IN_JOB;
    }
}
