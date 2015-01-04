/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.dataproviders;

import com.przemo.etl.interfaces.IDataMapper;
import java.util.Map;

/**
 *
 * @author Przemo
 */
public class SimpleMapper implements IDataMapper{

    protected Map<String, String> map;
       
    @Override
    public void setMapping(Map<String, String> sourceDestinationMap) {
        map = sourceDestinationMap;
    }

    @Override
    public Map<String, String> getMapping() {
        return map;
    }
    
}
