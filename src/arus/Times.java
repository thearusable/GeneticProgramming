/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arus;

/**
 *
 * @author arsc
 */
public class Times {

    @Override
    public String toString() {
        return "{" + "ST=" + startTime + ", ET=" + endTime + '}';
    }

    public Times(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Times() {
        startTime = 0;
        endTime = 0;
    }
    
    public int startTime;
    public int endTime;
    
}
