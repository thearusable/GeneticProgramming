/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus.example;

import arus.base.BaseTask;

/**
 *
 * @author arus2
 */
public class J1T3 extends BaseTask{

    @Override
    public int getJobId() {
        return 1;
    }

    @Override
    public int getWhichInJob() {
        return 3;
    }

    @Override
    public int getMachineId() {
        return 3;
    }

    @Override
    public int getProcessingTime() {
        return 4;
    }
    
    @Override
    public int getUniqueId() {
        return 2;
    }
}
