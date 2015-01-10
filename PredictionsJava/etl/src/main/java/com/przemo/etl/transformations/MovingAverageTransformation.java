/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations;

import com.google.common.base.Function;
import com.google.common.collect.Table;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 *
 * @author Przemo
 */
public class MovingAverageTransformation extends ColumnTransformation {
    
    protected String ResultingColumnName = "MovAvg";

    public String getResultingColumnName() {
        return ResultingColumnName;
    }

    public void setResultingColumnName(String ResultingColumnName) {
        this.ResultingColumnName = ResultingColumnName;
    }
    
    @Override
    public void setTransformation(Function f){
        //do nothing. Function is already stated
    }
    
    /**
     * Calculates moving average on the values of the given column in the table. The table has to be sorted to give correct results.
     * The values in the column must be of a numeric type.
     * @param column
     * @param period 
     */
    public MovingAverageTransformation(final String column, final int period){
        this.transformation = new Function() {

            @Override
            public Object apply(Object f) {
                if(f instanceof Table && ((Table)f).containsColumn(column)){
                    Table t = (Table)f;                 
                    if(t.column(column).size()>=period){
                        calculateMovingAverage(t);
                    }
                }
                return f;
            }           

            protected void calculateMovingAverage(Table t) {
                int ix = 0;
                double s0=0; double prev=0;
                boolean firstRound=true;
                Queue<Double> sq = new ArrayBlockingQueue(period);
                for (Object r : t.rowKeySet()) {
                    sq.offer((Double) t.get(r, column));
                    ix++;
                    if (ix >= period) {
                        if (firstRound) {
                            for (Double d : sq) {
                                s0 += d;
                            }
                            firstRound=false;
                        } else {
                            s0+=((Double) t.get(r, column)-prev);
                        }
                        t.put(r, resuiltingColumnName(period), s0 / period);
                        prev = sq.poll();
                    }
                }
            }
        };
    }
    protected String resuiltingColumnName(int period) {
                return ResultingColumnName +"("+period+")";
            }
}
