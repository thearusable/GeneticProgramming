/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.results;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author areks
 */
public class SingleProblemStatistics{
    
    public String problemName;
    public boolean isLearningProblem;
    public double learnedValue;
    public ArrayList<Double> values;
    
    // calculated values
    // average value
    public double AVG;
    // median value
    public double MEDIAN;
    // standard deviation
    public double STDDEV;

    public SingleProblemStatistics(String name, int size, double learned, boolean isLearning) {
        problemName = name;
        values = new ArrayList<>(size);
        learnedValue = learned;
        isLearningProblem = isLearning;
    }

    public void add(double v) {
        values.add(v);
    }

    private void sort() {
        Collections.sort(values);
    }
    
    private void calculate()
    {
        // calculate avg
        double sum = 0.0;
        for (double v : values) {
            sum += v;
        }
        AVG = sum / values.size();
        
        // calculate Median
        if (values.size() % 2 == 0) {
            MEDIAN = (values.get((values.size() / 2) - 1) + values.get(values.size() / 2)) / 2.0;
        }
        else
        {
            MEDIAN = values.get(values.size() / 2);
        }
        
        // calculate standard deviation
        double temp = 0;
        for (double a : values) {
            temp += (a - AVG) * (a - AVG);
        }
        STDDEV = Math.sqrt(temp / (values.size() - 1));
    }

    public double getMin() {
        return values.get(0);
    }

    public double getMax() {
        return values.get(values.size() - 1);
    }

    public double getAvg() {
        return AVG;
    }

    public double getMedian() {
        return MEDIAN;
    }

    public double getStandardDeviation() {
        return STDDEV;
    }

    public void prepare()
    {
        sort();
        calculate();
    }
    
    public void print() {
        System.out.println("--- Problem " + problemName + ":\nLearned: \t" + learnedValue 
                + "\nMinimal: \t" + getMin()
                + "\nMaximum: \t" + getMax()
                + "\nAverage: \t" + AVG
                + "\nMedian:  \t" + MEDIAN
                + "\nStdDev:  \t" + STDDEV);
    }
}
