/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jobs;

import ec.gp.GPNode;

/**
 *
 * @author arus2
 */
public abstract class TaskNode extends GPNode {

    abstract public int getTaskDuration();
    abstract public int getJobID();

}
