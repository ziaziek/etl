/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Przemo
 */
public class WeightedAverageTransformation extends MovingAverageTransformation {

    double[] weights = null;

    public void setWeigths(double[] weigths) {
        this.weights = weigths;
    }
    
    public WeightedAverageTransformation(String column, int period, double[] weights) {
        super(column, period);
        this.weights=weights;
        this.ResultingColumnName = "WAvg";
    }
    
    @Override
    protected Table calculateMovingAverage(Table table, int period, String column){
        if(this.weights==null || this.weights.length!=period){
            return null;
        }
                Table t = HashBasedTable.create();
                int ix = 0;
                double s0;
                double weightsTotal = calculateTotalWeight(weights);
                int qi;
                String rc = resultingColumnName(period);
                Queue<Double> sq = new ArrayBlockingQueue(period);
                for (Object r : table.rowKeySet()) {
                    sq.offer((Double) table.get(r, column));
                    ix++;
                    if (ix >= period) {
                            qi=0;
                            s0=0;
                            for (Double d : sq) {
                                s0 += this.weights[qi]*d;
                                qi++;
                            }
                        t.put(r, rc, s0/weightsTotal);
                        sq.poll();
                    }
                }
                return t;
    }
    
    private double calculateTotalWeight(double[] t){
        double r = 0.0;
        for(double d: t){
            r+=d;
        }
        return r;
    }
    
}
