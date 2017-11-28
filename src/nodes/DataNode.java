/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import ec.gp.GPData;

/**
 *
 * @author areks
 */
public class DataNode extends GPData {

    public double value = 0.0;
    
    @Override
    public String toString() {
        return "D:" + value;
    }
    
    @Override
    public void copyTo(GPData other) {
        ((DataNode)other).value = value;
    }
    
}
