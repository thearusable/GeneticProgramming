/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JobsScheduling;

/**
 *
 * @author arus2
 */
public class SingleJobData {

    public int JobID;
    public int [] RequiredJobs;
    
    @Override
    public String toString() {
        String text = "SingleJobData{";
        text += " JobID=" + JobID;
        
        text += " RequiredJobs:";
        for(int i=0; i < RequiredJobs.length; i++){
            text += " " + RequiredJobs[i];
        }
        
        return text + '}';
    }
    
    public SingleJobData(int id, int... rj){
        JobID = id;
        RequiredJobs = (int[])(rj.clone());
    }
}
