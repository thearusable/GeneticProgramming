/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author areks
 */
public class SingleRandomStatistics implements java.io.Serializable{

    private static final long serialVersionUID = 1;
    
    private ArrayList<Double> values;

    SingleRandomStatistics(int size) {
        values = new ArrayList<>(size);
    }

    public void add(double v) {
        values.add(v);
    }

    public void sort() {
        Collections.sort(values);
    }

    public double getMin() {
        return values.get(0);
    }

    public double getMax() {
        return values.get(values.size() - 1);
    }

    public double getMean() {
        double sum = 0.0;
        for (double v : values) {
            sum += v;
        }
        return sum / values.size();
    }

    public double getMedian() {
        // array is already sorted
        if (values.size() % 2 == 0) {
            return (values.get((values.size() / 2) - 1) + values.get(values.size() / 2)) / 2.0;
        }
        return values.get(values.size() / 2);
    }

    public double getStandardDeviation() {
        double mean = getMean();
        double temp = 0;
        for (double a : values) {
            temp += (a - mean) * (a - mean);
        }
        return Math.sqrt(temp / (values.size() - 1));
    }

    public void print() {
        sort();

        System.out.println("Minimal: \t" + getMin());
        System.out.println("Maximum: \t" + getMax());
        System.out.println("Average: \t" + getMean());
        System.out.println("Median:  \t" + getMedian());
        System.out.println("StdDev:  \t" + getStandardDeviation());
    }
}
