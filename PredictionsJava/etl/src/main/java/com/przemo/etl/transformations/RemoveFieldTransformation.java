/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations;

import com.google.common.collect.Table;
import com.przemo.etl.interfaces.IDataProvider;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Przemo
 */
public class RemoveFieldTransformation extends AbstractTransformer{

    private List fieldIndice = new ArrayList<>();

    public RemoveFieldTransformation(IDataProvider object, Table samples) {
        super(object, samples);
    }

    public List<Integer> getFieldIndice() {
        return fieldIndice;
    }

    public void setFieldsToRemove(List fieldIndice) {
        this.fieldIndice = fieldIndice;
    }
    
    @Override
    public void transform() {
        if(fieldIndice!=null && !fieldIndice.isEmpty() && data!=null && !data.columnKeySet().isEmpty()){         
                for(Object i: fieldIndice){
                    if(data.columnKeySet().contains(i)){
                        for(Object r: data.rowKeySet()){
                            data.remove(r, i);
                        }
                }
            }
        }
    }
    
}
