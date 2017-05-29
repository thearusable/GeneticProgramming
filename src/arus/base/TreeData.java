/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus.base;

import ec.gp.GPData;
import java.util.Arrays;

/**
 *
 * @author arus2
 */
public class TreeData extends GPData {

    public int[] TaskCount = new int[10];
    
    @Override
    public Object clone() {
        TreeData bd = new TreeData();
        bd.TaskCount = TaskCount.clone();
        return bd;
    }

    @Override
    public void copyTo(GPData gpd) {
        ((TreeData)gpd).TaskCount = TaskCount.clone();
    }
    
}
