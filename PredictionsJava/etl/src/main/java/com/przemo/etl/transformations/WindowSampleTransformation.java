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
 * This class builds rows of data based on number of previous rows of specified column. The assumption is that the table has got rows already sorted chreonologically.
 * @author Przemo
 */
public class WindowSampleTransformation extends ColumnTransformation {
    
    protected String column;
    
    public WindowSampleTransformation(String columnName, int period) throws Exception{
        if(columnName==null || period<=0){
            throw new Exception("Wrong initialization parameters for Window sample transformation!");
        }
        this.column=columnName;
        this.transformation = setMyTransformation(period);
    }
    
    @Override
    public void setTransformation(Function func){
        //Do nothing.
    }

    private Function setMyTransformation(final int period) {
        return new Function(){

            @Override
            public Object apply(Object f) {
                if (f instanceof Table && ((Table)f).rowKeySet().size()>=period){
                    Table t = (Table)f;
                    Queue<Object> sq = new ArrayBlockingQueue(period);
                    int ix=0;
                    for(Object r: t.rowKeySet()){                   
                        if(ix>=period){
                            Object[] osq = sq.toArray();
                            for(int p=osq.length-1; p>=0;p--){
                               t.put(r, getColumnName(osq.length-p), osq[p]);
                            }
                            sq.poll();
                        }
                        sq.offer(t.get(r, column));
                        ix++;
                    }
                }              
                return f;
            }
            
        };
    }
    
    private String getColumnName(int p){
        return column+"_(-"+p+")";
    }
}
