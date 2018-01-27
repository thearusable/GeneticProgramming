/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import ec.gp.GPData;

/**
 *
 * @author areks
 */
public class TreeData extends GPData {

    public double value = 0.0;
    public TaskData task;
    
    @Override
    public String toString() {
        return "D:" + value;
    }
    
    @Override
    public void copyTo(GPData other) {
        ((TreeData)other).value = value;
        ((TreeData)other).task = task;
    }
    
    public void reset(){
        value = 0.0;
    }
    
}
