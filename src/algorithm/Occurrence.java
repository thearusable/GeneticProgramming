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
public class Occurrence {

    @Override
    public String toString() {
        return "Occurrence{" + "parentID=" + parentID + ", startTime=" + startTime + ", endTime=" + endTime + ", howManyTimesOccurrs=" + howManyTimesOccurrs + '}';
    }
   
    //related to taks
    private int parentID;
    private int startTime;
    private int endTime;
    private int howManyTimesOccurrs;
    
    public Occurrence() {
        parentID = 0;
        startTime = 0;
        endTime = 0;
        howManyTimesOccurrs = 0;
    }
    
    //return endTime
    public int Occur(int taskID, int pID, int sTime){
        parentID = pID;
        startTime = sTime;
        howManyTimesOccurrs += 1;
        endTime = startTime + METADATA.getTaskDuration(taskID);
        
        return endTime;
    }
    
    public void reset(){
        parentID = 0;
        startTime = 0;
        endTime = 0;
        howManyTimesOccurrs = 0;
    }
    
    public int getHowManyTimesOccurrs(){
        return howManyTimesOccurrs;
    }
}
