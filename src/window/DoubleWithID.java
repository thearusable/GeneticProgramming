/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package window;

/**
 *
 * @author arsc
 */
public class DoubleWithID extends Number {

    @Override
    public String toString() {
        return "ID: " + id + " VAL: " + val;
    }

    public DoubleWithID(double val, int id) {
        this.val = val;
        this.id = id;
    }

    private final double val;
    private final int id;
    
    @Override
    public int intValue() {
        return (int)val;
    }

    @Override
    public long longValue() {
        return (long)val;
    }

    @Override
    public float floatValue() {
        return (float)val;
    }

    @Override
    public double doubleValue() {
        return val;
    }
    
    public int getID(){
        return id;
    }
    
}
