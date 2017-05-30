/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus;

import ec.gp.GPData;
import java.util.Arrays;

/**
 *
 * @author arus2
 */
public class TreeData extends GPData {

    @Override
    public String toString() {
        return "TreeData{" + "howManyTimesOccurs=" + Arrays.toString(howManyTimesOccurs) 
                + ", " + numberOfTasksOnWrongMachine + ", " + toManyChilds + ", " + machinesWithoutChilds + '}';
    }

    public int[] howManyTimesOccurs;
    public int numberOfTasksOnWrongMachine;
    public int toManyChilds;
    public int machinesWithoutChilds;
    
    public TreeData() {
        howManyTimesOccurs = new int[METADATA.TASKS_COUNT];
        numberOfTasksOnWrongMachine = 0;
        toManyChilds = 0;
        machinesWithoutChilds = 0;
    }
    
    public void reset(){
        for(int i = 0; i < howManyTimesOccurs.length; ++i ){
            howManyTimesOccurs[i] = 0;
        }
        numberOfTasksOnWrongMachine = 0;
        toManyChilds = 0;
        machinesWithoutChilds = 0;
    }
    
    @Override
    public Object clone() {
        TreeData bd = new TreeData();
        bd.howManyTimesOccurs = howManyTimesOccurs.clone();
        return bd;
    }

    @Override
    public void copyTo(GPData gpd) {
        ((TreeData)gpd).howManyTimesOccurs = howManyTimesOccurs.clone();
    }
    
}
