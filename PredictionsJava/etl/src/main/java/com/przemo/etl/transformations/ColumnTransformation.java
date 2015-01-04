/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations;

import com.google.common.base.Function;

/**
 *
 * @author Przemo
 */
public class ColumnTransformation extends AbstractTransformer{

    protected Function transformation;

    public void setTransformation(Function transformation) {
        this.transformation = transformation;
    }
    
    @Override
    public void transform() {
        if(transformation!=null){
            transformation.apply(data);
        }
    }
    
}
