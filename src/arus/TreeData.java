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

    public int[] howManyTimesOccurs;
    public int[] startupTaskTime;
    public int numberOfTasksOnWrongMachine;
    public int toManyChilds;
    public int machinesWithoutChilds;
    
    public TreeData() {
        howManyTimesOccurs = new int[METADATA.TASKS_COUNT];
        startupTaskTime = new int[METADATA.TASKS_COUNT];
        numberOfTasksOnWrongMachine = 0;
        toManyChilds = 0;
        machinesWithoutChilds = 0;
    }
    
    public void reset(){
        for(int i = 0; i < howManyTimesOccurs.length; ++i ){
            howManyTimesOccurs[i] = 0;
            startupTaskTime[i] = 0;
        }
        numberOfTasksOnWrongMachine = 0;
        toManyChilds = 0;
        machinesWithoutChilds = 0;
    }
    
    @Override
    public String toString() {
        return "howManyTimesOccurs=" + Arrays.toString(howManyTimesOccurs) 
                + "\n" + "startupTaskTime=   " + Arrays.toString(startupTaskTime) 
                + ", " + numberOfTasksOnWrongMachine + ", " + toManyChilds + ", " + machinesWithoutChilds + '}';
    }
    
    @Override
    public Object clone() {
        TreeData bd = new TreeData();
        bd.howManyTimesOccurs = howManyTimesOccurs.clone();
        bd.startupTaskTime = startupTaskTime.clone();
        return bd;
    }

    @Override
    public void copyTo(GPData gpd) {
        ((TreeData)gpd).howManyTimesOccurs = howManyTimesOccurs.clone();
        ((TreeData)gpd).startupTaskTime = startupTaskTime.clone();
    }
    
}
