/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.interfaces;

/**
 *
 * @author Przemo
 */
public interface IDataProvider {
    
    boolean saveData(Iterable Data);
    
    Iterable readData();
    
}
