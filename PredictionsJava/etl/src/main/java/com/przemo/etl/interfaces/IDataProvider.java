/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.interfaces;

import com.google.common.collect.Table;

/**
 *
 * @author Przemo
 */
public interface IDataProvider {
    
    boolean saveData(Table Data);
    
    Table readData();
    
}
