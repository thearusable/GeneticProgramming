/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import ec.EvolutionState;
import ec.gp.ERC;
import ec.gp.GPNode;
import ec.util.Code;

/**
 *
 * @author arsc
 */
public abstract class ERCnode extends ERC{

    private int ID;

    @Override
    public abstract String name();
    
    @Override
    public abstract int expectedChildren();
    
    public abstract void mutate();
    
    public abstract void reset();
    
    @Override
    public void mutateERC(EvolutionState es, int i){
        mutate();
    }
    
    @Override
    public void resetNode(EvolutionState es, int i){
        reset();
    }
    
    public int getID(){
        return ID;
    }
    
    public final void setID(int newID){
        ID = newID;
    }
    
    @Override
    public String toString() {
        return name() + getID();
    }

    @Override
    public boolean nodeEquals(GPNode gpnode) {
        return this.getClass().equals(gpnode.getClass()) && this.getID() == ((ERCnode)gpnode).getID();
    }
    
    @Override
    public String encode() {
        return Code.encode(name()) + Code.encode(getID());
    }
    
}
