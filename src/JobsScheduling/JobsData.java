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
import java.util.List;
import java.util.Set;



/**
 *
 * @author arus2
 */
public class JobsData extends GPData{
    
    public List<SingleJobData> Data;

    @Override
    public void setup(final EvolutionState state, final Parameter base){
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
        
    }
}
