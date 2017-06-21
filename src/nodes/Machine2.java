/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import algorithm.METADATA;

/**
 *
 * @author arsc
 */
public class Machine2 extends Machine{

    public Machine2() {
        setID(METADATA.getRandomMachineID());
    }

    @Override
    public int expectedChildren() {
        return 2;
    }
    
    @Override
    public Object clone() {
        Machine2 m2 = (Machine2)super.clone();
        m2.setID(getID());
        return m2;
    }
    
}
