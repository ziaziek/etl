/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations;

import com.google.common.collect.Table;

/**
 *
 * @author Przemo
 */
public class ProportionalDateGapFiller extends AbstractGapsFiller {

    @Override
    protected boolean isGapped(Object get, Object get0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void fillGap(Integer previousKey, Integer rowNumber, Table<Integer, Object, Object> data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}
