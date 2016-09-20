/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jobs;

/**
 *
 * @author arus2
 */
public class Job_2 extends JobTreeNode {

    @Override
    public int expectedChildren() {
        return 2;
    }

    @Override
    public int getJobID() {
        return 2;
    }
    
    @Override
    public String getRequiredJobsInfo() {
        return "R[0,1]";
    }
}
