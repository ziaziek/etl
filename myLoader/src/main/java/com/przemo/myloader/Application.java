/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.myloader;

import com.google.common.collect.Table;
import com.przemo.database.DbConnector;
import com.przemo.database.DbConnectorFactory;
import com.przemo.etl.dataproviders.CSVDataProvider;
import com.przemo.etl.dataproviders.DefaultDatabaseDataProvider;
import com.przemo.etl.dataproviders.SimpleMapper;
import com.przemo.etl.interfaces.IDataMapper;
import com.przemo.etl.interfaces.IDataProvider;
import com.przemo.etl.transformations.ColumnTransformation;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Przemo
 */
public class Application {
    
    final static List<String> processedFiles = Collections.synchronizedList(new ArrayList<String>());
    
    public static void main(String[] args){
        final IDataMapper mapper = new SimpleMapper();
        Map<String, String> m = new LinkedHashMap<>();
        m.put("date", "date");
        m.put("time", "time");
        m.put("Column 1", "o");
        m.put("Column 2", "h");
        m.put("Column 3", "l");
        m.put("Column 4", "c");
        m.put("Column 5", "v");
        mapper.setMapping(m);
        final FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return  name.matches("DAT_ASCII_EURUSD_M1\\w+\\.csv$");
            }
        };
        
        Thread[] threads = new Thread[4];
        for (int i=0; i<threads.length;i++) {
            threads[i]=new Thread() {
                @Override
                public void run() {
                    DbConnector connector = DbConnectorFactory.createDbConnector(DbConnectorFactory.DatabaseType.POSTGRESQL, "192.168.2.250", "przemo", "derek", "predictions");
                    scanThroughDirectories(new File("G:\\Przemo\\Dokumenty\\SystemPredykcyjny\\Dokumentacja Version 2\\Dane"), filter, new DefaultDatabaseDataProvider(connector, mapper, "eurusd1m"));
                }
            };
            threads[i].start();
        }
    }
    
    private static void scanThroughDirectories(File current, FilenameFilter ff, IDataProvider prvd){
        if(current.isDirectory()){
            for(File f: current.listFiles(ff)){
                scanThroughDirectories(f, ff, prvd);
            }
        } else {
            importFile(current.getAbsolutePath(), prvd);
        }      
    }
    
    private static void importFile(String fileName, IDataProvider prvd){
        if (!processedFiles.contains(fileName)) {
            processedFiles.add(fileName);
            System.out.println("Processing file " + fileName);
            System.out.println();
            Table t = new CSVDataProvider(fileName, ";", false).readData();
            // add new field - date and time
            ColumnTransformation tran = new DateTransformation(5);
            tran.setData(t);
            tran.transform();
            prvd.saveData(t);
        }

    }
    
}
