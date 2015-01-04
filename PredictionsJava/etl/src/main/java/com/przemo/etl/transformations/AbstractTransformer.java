/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations;

import com.google.common.collect.Table;
import com.przemo.etl.interfaces.IDataProvider;
import com.przemo.etl.interfaces.IDataTransformer;

/**
 *
 * @author Przemo
 */
public abstract class AbstractTransformer implements IDataTransformer{

    IDataProvider dataProvider;

    Table<Integer, Object, Object> data;

    public void setData(Table data) {
        this.data = data;
    }
    
    public AbstractTransformer() {
    }

    public AbstractTransformer(IDataProvider prvd, Table data){
        this.data=data;
        this.dataProvider=prvd;
    }
    
    @Override
    public void setDataProvider(IDataProvider provider) {
        this.dataProvider = provider;
    }
    
}
