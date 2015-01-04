/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.interfaces;

import java.util.Map;

/**
 *
 * @author Przemo
 */
public interface IDataMapper {
    
    void setMapping(Map<String, String> sourceDestinationMap);
    
    Map<String, String> getMapping();
    
}
