/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations;

/**
 *
 * @author Przemo
 */
public class AddFieldTransformation extends AbstractTransformer {

    private Object sv;
    
    public void setFieldDescriptor(Object value) {
        this.sv = value;
    }
    
    @Override
    public void transform() {
        if(sv!=null && data!=null){
            data.put(0, sv, new Object());
        }
    }
    
}
