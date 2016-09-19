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
    
    public SingleJobData(int id, int... rj){
        JobID = id;
        RequiredJobs = (int[])(rj.clone());
    }
}
