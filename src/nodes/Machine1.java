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
public class Machine1 extends Machine{

    public Machine1() {
        setID(METADATA.getRandomMachineID());
    }

    @Override
    public int expectedChildren() {
        return 1;
    }

    @Override
    public Object clone() {
        Machine m1 = (Machine1)super.clone();
        m1.setID(getID());
        return m1;
    }
}
