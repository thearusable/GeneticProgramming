/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

/**
 *
 * @author arsc
 */
public final class Stats {

    public int machineWithBadParent; //+dummy and machine
    public int taskWithBadParent; //+dummy
    public int taskOnWrongMachine; //+when machine id != parentID
    public int taskInWrongOrder; //+when task occurs in wrong order in job
    public int taskWithBadTime; //TODO when task occurs in bad time, like overlap
    public int makespan; //+time to execute whole tree
    public int taskMissing; //+when task is not in the tree
    public int taskDoubled; //+when is too many same tasks

    public Stats() {
        reset();
    }
    
    @Override
    public String toString() {
        return "Stats" 
                + "\nmachineWithBadParent = " + machineWithBadParent 
                + "\ntaskWithBadParent = " + taskWithBadParent 
                + "\ntaskMissing = " + taskMissing 
                + "\ntaskDoubled = " + taskDoubled 
                + "\ntaskOnWrongMachine = " + taskOnWrongMachine 
                + "\ntaskInWrongOrder = " + taskInWrongOrder 
                + "\ntaskWithBadTime = " + taskWithBadTime 
                + "\nmakespan = " + makespan + '\n';
    }

    @Override
    protected Object clone() {
        Stats stats = new Stats();
        stats.machineWithBadParent = machineWithBadParent;
        stats.makespan = makespan;
        stats.taskInWrongOrder = taskInWrongOrder;
        stats.taskOnWrongMachine = taskOnWrongMachine;
        stats.taskWithBadParent = taskWithBadParent;
        stats.taskWithBadTime = taskWithBadTime;
        stats.taskMissing = taskMissing;
        stats.taskDoubled = taskDoubled;
        return stats;
    }
    
    public void reset(){
        machineWithBadParent = 0;
        taskWithBadParent = 0;
        taskOnWrongMachine = 0;
        taskInWrongOrder = 0;
        taskWithBadTime = 0;
        makespan = 0;
        taskMissing = 0;
        taskDoubled = 0;
    }
    
}
