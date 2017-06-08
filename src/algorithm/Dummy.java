/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.ERC;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

/**
 *
 * @author arsc
 */
public class Dummy extends ERC{

    @Override
    public String toString() {
        return "";
    }

    @Override
    public int expectedChildren() {
        return CHILDREN_UNKNOWN;
    }

    @Override
    public String name() {
        return "ERCD";
    }

    @Override
    public void resetNode(EvolutionState es, int i) {
        //nothing to do
    }

    @Override
    public boolean nodeEquals(GPNode gpnode) {
        return false;
    }

    @Override
    public String encode() {
        return "";
    }

    @Override
    public void eval(EvolutionState es, int i, GPData gpdata, ADFStack adfs, GPIndividual gpi, Problem prblm) {
        for(int x = 0; x < children.length; ++x){
            children[x].eval(es, i, gpdata, adfs, gpi, prblm);
        }
    }
    
}
