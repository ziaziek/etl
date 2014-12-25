/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations;

import java.util.Map;

/**
 *
 * @author Przemo
 */
public class ColumnRenameTransformation extends AbstractTransformer {

    private String name;
    private Object renamed;
    
    public void setReName(Object renamedColumn, String name){
     this.renamed=renamedColumn;
     this.name=name;
    }
    
    @Override
    public void transform() {
        if(name!=null && renamed!=null && data.columnKeySet().contains(renamed)){
            Map r = data.columnMap().remove(renamed);
            for(Object row: r.keySet()){
                if(row instanceof Integer){
                   data.put((Integer) row, name, r.get(row)); 
                }                
            }
        }
    }
    
}
