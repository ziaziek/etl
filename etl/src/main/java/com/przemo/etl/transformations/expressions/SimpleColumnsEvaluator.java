/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations.expressions;

import com.google.common.collect.Table;
import com.google.common.primitives.Doubles;
import com.przemo.etl.interfaces.IEvaluator;
import com.przemo.etl.transformations.expressions.onp.ONPConverter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Przemo
 */
public class SimpleColumnsEvaluator extends SimpleNumberEvaluator implements IEvaluator{
    
    private Object currentColumnValue;
    private Table data;
    private Object row;
      
    public SimpleColumnsEvaluator(String expression){
        this.exp=ONPConverter.code(exp);            
    }
    
    @Override
    public Object evaluate(Object... params) {
        currentColumnValue=null;
        currentDoubleValue=null;
        if(params[0] instanceof Table){
            data = (Table)params[0];
            row=params[1];
           return ONPConverter.decode(exp, this); 
        } else {
            return null;
        }       
    }


    @Override
    public boolean isValue(String val) {
        return (super.isValue(val) || (val.contains("[") && val.contains("]") && (currentColumnValue=data.get(row, val.replace("\\[", "").replace("\\]", "")))!=null));
    }

    @Override
    public Object getValue(String val) {
        if(currentDoubleValue!=null){
            return currentDoubleValue;
        } else {
            return currentColumnValue;
        }
    }
}
