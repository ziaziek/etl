/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations;

import com.google.common.base.Function;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 *
 * @author Przemo
 */
public class MovingAverageTransformation extends ColumnTransformation {
    
    protected String ResultingColumnName = "MovAvg";

    protected String calculatedColumn;
    
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
        this.calculatedColumn=column;
        this.transformation = new Function() {

            @Override
            public Object apply(Object f) {
                if(f instanceof Table && ((Table)f).containsColumn(column)){
                    Table t = (Table)f;                 
                    if(t.column(column).size()>=period){
                        Table tr =calculateMovingAverage(t, period, column);
                        if(tr!=null){
                           t.putAll(tr); 
                        }    
                    }
                }
                return f;
            }                   
        };
    }
    
    protected Table calculateMovingAverage(Table table, int period, String column) {
        Table t = HashBasedTable.create();
        int ix = 0;
        double s0;
        int ixCounter = 0;
        double[] sq = new double[period];
        String cn = resultingColumnName(period);
        for (Object r : table.rowKeySet()) {
            sq[ixCounter] = (double) table.get(r, column) / period;
            ix++;
            ixCounter++;
            if (ix >= period) {
                s0 = 0;
                for (double d : sq) {
                    s0 += d;
                }
                t.put(r, cn, s0);
                if (ixCounter == period) {
                    ixCounter = 0;
                }
            }
        }
        return t;
    }
    
    protected String resultingColumnName(int period) {
                return ResultingColumnName +"_"+calculatedColumn+"_("+period+")";
            }
}
