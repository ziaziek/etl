/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.dataproviders;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.przemo.etl.interfaces.IDataMapper;
import com.przemo.etl.interfaces.IDataProvider;
import com.przemo.database.DbConnector;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Przemo
 */
public class DefaultDatabaseDataProvider implements IDataProvider{
    
    protected final String valsReplacer = "$vals$";
    
    private DbConnector connector;

    private IDataMapper mapper;

    public IDataMapper getMapper() {
        return mapper;
    }

    public void setMapper(IDataMapper mapper) {
        this.mapper = mapper;
    }
    
    private final String tableName;
    
    public DbConnector getConnector() {
        return connector;
    }

    public void setConnector(DbConnector connector) {
        this.connector = connector;
    }
    
    public DefaultDatabaseDataProvider(DbConnector connector, IDataMapper columnsMapper, String tableName){
        this.connector=connector;
        this.mapper=columnsMapper;
        this.tableName=tableName;
    }

    @Override
    public boolean saveData(Table Data) {
        if(Data!=null && tableName!=null){
            
            //execute queries for each row
            return executeQuery(connector, Data, prepareMappedQuery(mapper, Data));

        } else {
            return false;
        }
        
    }

    @Override
    public Table readData() {
        Table t = HashBasedTable.create();

        if (connector != null && tableName != null) {
            try {
                ResultSet res = connector.query("select * from " + tableName);
                int cols = res.getMetaData().getColumnCount();

                for (int i = 0; i < cols; i++) {
                    int counter = 0;
                    while (res.next()) {
                        t.put(counter, res.getMetaData().getColumnName(i), res.getObject(i));
                        counter++;
                    }
                }

            } catch (Exception ex) {
                Logger.getLogger(DefaultDatabaseDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return t;
    }
    
    protected String prepareMappedQuery(IDataMapper mapper, Table Data){
        String qry = String.format("insert into %s(", tableName);
            if(mapper!=null && mapper.getMapping()!=null){
                for(String mapped: mapper.getMapping().keySet()){
                    qry+=mapper.getMapping().get(mapped)+",";
                }               
            } else {
                for(Object column: Data.columnKeySet()){
                    qry+=(String)column+",";
                }
            }
            qry = qry.substring(0, qry.length()-1);
            qry+=String.format(") values (%s)", valsReplacer);
            return qry;
    }
    
    
    protected boolean executeQuery(DbConnector connector, Table Data, String basicQuery){
        for(Object row: Data.rowKeySet()){
                String vals=prepareQueryValues(Data, row);
                try {
                    if(connector!=null){
                       connector.execute(basicQuery.replace(valsReplacer, vals)); 
                    }                  
                } catch (Exception ex) {
                    Logger.getLogger(DefaultDatabaseDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        return true;
    }

    protected String prepareQueryValues(Table Data, Object row) {
        String vals="";
        Set columnsToGo = new LinkedHashSet();
        if(mapper!=null && mapper.getMapping()!=null && !mapper.getMapping().isEmpty()){
            for(Object o: mapper.getMapping().keySet()){
                columnsToGo.add(o);
            }
        } else {
            columnsToGo=Data.columnKeySet();
        }
        for(Object col : columnsToGo){
            if(Data.get(row, col) instanceof String || Data.get(row, col) instanceof Date){
                vals+="'"+String.valueOf(Data.get(row, col))+"',";
            } else {
                vals+=String.valueOf(Data.get(row, col))+",";
            }
        }
        vals = vals.substring(0, vals.length()-1);
        return vals;
    }
}
