/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.dataproviders;

import com.google.common.collect.Table;
import com.przemo.database.DbConnector;
import com.przemo.etl.interfaces.IDataMapper;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Przemo
 */
public class PostgresDatabaseDataProvider extends DefaultDatabaseDataProvider {

    public PostgresDatabaseDataProvider(DbConnector connector, IDataMapper columnsMapper, String tableName) {
        super(connector, columnsMapper, tableName);
    }
    
    
    @Override
    public boolean saveData(Table Data) {
        if(Data!=null && tableName!=null){
            try {
                    if(connector!=null){
                        String pq = prepareMappedQuery(mapper, Data);
                       connector.execute(pq); 
                    } 
                    return true;
                } catch (Exception ex) {
                    Logger.getLogger(DefaultDatabaseDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }

        } else {
            return false;
        }
    }
    
    /**
     * Use built-in functionality of PostgresQL to read in data from a stdin by using the COPY method
     * @param mapper has to provide the source columns that should be used. Destination mapping is not important.
     * @param Data
     * @return 
     */
    @Override
    protected String prepareMappedQuery(IDataMapper mapper, Table Data){
        StringBuilder sb = new StringBuilder();
        sb.append("copy ").append(this.tableName).append(" from STDIN WITH NULL 'NULL';\r\n");
        Set<String> columns = mapper.getMapping().keySet();
        for(Object r : Data.rowKeySet()){
         for(String column: columns){
                    Object v= Data.get(r, column);
                    if(v==null){
                        v="'NULL'";
                    }
                    sb.append(v).append("\t");
            }
         sb.append("\r\n");
        }
        
        return sb.toString();
    }
}
