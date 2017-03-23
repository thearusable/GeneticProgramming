/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus.base;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

/**
 *
 * @author arus2
 */
public abstract class BaseMachine extends GPNode {

    public abstract int getMachineId();

    @Override
    public String toString() {
        return "M" + getMachineId();
    }

    @Override
    public void eval(EvolutionState state
            , int thread
            , GPData input
            , ADFStack stack
            , GPIndividual individual
            , Problem problem) 
    {
        for(int i = 0; i < children.length; i++){
            children[i].eval(state, thread, input, stack, individual, problem);
        }
    }
    
    
}
