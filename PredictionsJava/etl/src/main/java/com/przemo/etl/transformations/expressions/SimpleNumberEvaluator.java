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
 * @author Przemysław
 */
public class SimpleNumberEvaluator implements IEvaluator{
    protected Object currentValue;

    protected String exp;
    
    public SimpleNumberEvaluator(String expression){
        this.exp=ONPConverter.code(exp);
    }
    
    public SimpleNumberEvaluator(){
        
    }
    
    @Override
    public Object evaluate(Object... args) {
        currentValue=null;
        if(exp!=null){
            return ONPConverter.decode(exp, this);
        } else{
            return null;
        }
    }


    @Override
    public boolean isValue(String val) {
        return ((currentValue= Doubles.tryParse(val))!=null);
    }

    @Override
    public boolean isOperator(String op) {
        return op.length()==1;
    }

    @Override
    public boolean isFunction(String f) {
        return f.length()>1;
    }

    @Override
    public Object getValue(String val) {
        if(currentValue!=null){
            return currentValue;
        } else {
            return null;
        }
    }
    
}
