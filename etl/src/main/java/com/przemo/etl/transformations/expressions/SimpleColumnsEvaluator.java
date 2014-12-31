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
public class SimpleColumnsEvaluator implements IEvaluator{
    
    private String exp;
    
    private Double currentDoubleValue;
    private Object currentColumnValue;
    private Table data;
    private Object row;
    
    private static final Map<String, Integer> funcNoArgs = new HashMap<String, Integer>(){{
        put("sin", 1);
        put("cos", 1);
        put("tan", 1);
        put("ln", 1);
        put("substr", 3);
    }};
    
    public SimpleColumnsEvaluator(String expression){
        this.exp=ONPConverter.code(exp);            
    }

    private void getColumnNames(String expression){
        String[] t = expression.split("\\[");
        for(int i=0; i< t.length;i++){
            if(t[i].contains("]")){
               t[i] = t[i].substring(0, t[i].indexOf("]")); 
            }
            if(t[i].length()>0){

            }
            
        }
    }
    
    @Override
    public Object evaluate(Table data, Object row) {
        currentColumnValue=null;
        currentDoubleValue=null;
        return ONPConverter.decode(exp, this);
    }


    @Override
    public boolean isValue(String val) {
        return ((currentDoubleValue= Doubles.tryParse(val))!=null || (val.contains("[") && val.contains("]") && (currentColumnValue=data.get(row, val.replace("\\[", "").replace("\\]", "")))!=null));
    }

    @Override
    public boolean isOperator(String op) {
        return op.length()==1;
    }

    @Override
    public boolean isFunction(String f) {
        return f.length()>1 && funcNoArgs.containsKey(f);
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
