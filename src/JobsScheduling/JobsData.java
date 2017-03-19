/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JobsScheduling;

import ec.gp.GPData;



/**
 *
 * @author arus2
 */
public class JobsData extends GPData{
    public double x;    // return value

    @Override
    public void copyTo(final GPData gpd)   // copy my stuff to another DoubleData
        { ((JobsData)gpd).x = x; }
    
}
