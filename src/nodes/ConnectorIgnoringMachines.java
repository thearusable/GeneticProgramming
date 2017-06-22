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

/**
 *
 * @author arsc
 */
public class ConnectorIgnoringMachines extends ERCnode{

    @Override
    public String toString() {
        return "";
    }

    public ConnectorIgnoringMachines() {
        setID(-1);
    }
    
    @Override
    public int expectedChildren(){
        return CHILDREN_UNKNOWN;
    }
    
    @Override
    public String name() {
        return "CNM";
    }

    @Override
    public void eval(EvolutionState es, int i, GPData gpdata, ADFStack adfs, GPIndividual gpi, Problem prblm) {       
        for(int x = 0; x < children.length; ++x){
            children[x].eval(es, i , gpdata, adfs, gpi, prblm);
        }
    }

    @Override
    public void mutate() {
        //nothing TODO
    }

    @Override
    public Object clone() {
        ConnectorIgnoringMachines con = (ConnectorIgnoringMachines)super.clone();
        con.setID(getID());
        return con;
    }

    @Override
    public void reset() {
        //nothing TODO
    }

}
