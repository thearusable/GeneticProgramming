/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JobsScheduling;

import ec.EvolutionState;
import ec.gp.GPData;
import static ec.gp.GPData.P_GPDATA;
import ec.gp.GPDefaults;
import ec.util.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



/**
 *
 * @author arus2
 */
public class JobsData extends GPData{
    //kopiowanie jak w DoubleData z tutorila 4 
    /*
    public final ArrayList<SingleJobData> Data;

    public JobsData(){
        Data = new ArrayList<SingleJobData>();       
    }
    
    @Override
    public void setup(final EvolutionState state, final Parameter base){
        Data.clear();
        Data.add(new SingleJobData(0, -1));
        Data.add(new SingleJobData(1, 0));
        Data.add(new SingleJobData(2, 0));
        Data.add(new SingleJobData(3, 1, 2));
        Data.add(new SingleJobData(4, 1));
        Data.add(new SingleJobData(5, 1, 3));
        Data.add(new SingleJobData(6, 4, 5));
        Data.add(new SingleJobData(7, 3, 6));
        Data.add(new SingleJobData(8, 7));
        Data.add(new SingleJobData(9, 2, 5, 8));
        
        System.out.println("JobsData Initialized. SingleJobData: " + Data.size());
    }
*/
    public ArrayList<Integer> FinishedJobs;
    
    @Override
    public void copyTo(GPData other){
        ((JobsData)other).FinishedJobs = new ArrayList<>(FinishedJobs);
    }
    
    @Override
    public Object clone(){
        JobsData other = (JobsData)(super.clone());
        other.FinishedJobs = new ArrayList<>();
        return other;
    }
    
}
