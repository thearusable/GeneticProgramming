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
import ec.gp.ERC;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Code;
import ec.util.DecodeReturn;

/**
 *
 * @author areks
 */
public class Constant extends ERC {

    private static final long serialVersionUID = 1;
    
    private double constant;

    @Override
    public String toString() {
        return "" + constant;
    }

    @Override
    public int expectedChildren() {
        return 0;
    }

    @Override
    public void eval(EvolutionState es, int i, GPData gpdata, ADFStack adfs, GPIndividual gpi, Problem prblm) {
        TreeData data = ((TreeData) gpdata);

        data.value = data.value * constant;
    }

    @Override
    public void mutateERC(EvolutionState state, int thread) {
        constant += state.random[thread].nextGaussian() * 0.01;
    }

    @Override
    public void resetNode(EvolutionState es, int i) {
        constant = es.random[i].nextDouble();
    }

    @Override
    public boolean nodeEquals(GPNode gpnode) {
        return (gpnode.getClass() == this.getClass() && ((Constant) gpnode).constant == constant);
    }

    @Override
    public String encode() {
        return Code.encode(constant);
    }

    @Override
    public boolean decode(DecodeReturn dret) {
        int pos = dret.pos;
        String data = dret.data;
        Code.decode(dret);
        if (dret.type != DecodeReturn.T_DOUBLE)
        {
            dret.data = data;
            dret.pos = pos;
            return false;
        }
        constant = dret.d;
        return true;
    }

    @Override
    public String name() {
        return "C[" + constant + "]";
    }

}
