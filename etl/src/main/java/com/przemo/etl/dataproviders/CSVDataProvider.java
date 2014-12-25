/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.dataproviders;

import com.przemo.etl.interfaces.IDataProvider;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Przemo
 */
public class CSVDataProvider extends FileDataProvider implements IDataProvider{

    private String sep;
    private boolean dh;
    
    private String[] headers;

    public String[] getHeaders() {
        return headers;
    }
    
    public CSVDataProvider(String filename, String dataSeparator, boolean includeDataHeaders) {
        super(filename);
        this.sep=dataSeparator;
        this.dh = includeDataHeaders;
    }

    private void writeSeparatedData(FileWriter wr, String[] data) throws IOException{
            for(String s: (String[])data){
                    wr.write(s+ sep);
            }
            wr.write("\n");
    }
    
    @Override
    public boolean saveData(Iterable Data) {
        FileWriter wr=null;
        try {
             wr = new FileWriter(file);
             if(dh && headers!=null){
                 writeSeparatedData(wr, headers);
             }
            for(Object o: Data){
                if(o instanceof String[]){
                    writeSeparatedData(wr, (String[])o);
                }               
            }
            wr.flush();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CSVDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if(wr!=null){
                try {
                    wr.close();
                } catch (IOException ex) {
                    Logger.getLogger(CSVDataProvider.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public Iterable readData(){
        List<String[]> data = new ArrayList();
        if(file.exists() && file.canRead()){
            try {
                BufferedReader r = new BufferedReader(new FileReader(file));
                String line;
                while((line=r.readLine())!=null){
                    String[] lineArray =line.split(sep);
                    if( data.size()>0 && lineArray.length!=data.get(0).length){
                        throw new Exception ("Incompatible data lengths!");
                    }
                    data.add(lineArray);
                }
                if(dh){
                    headers = data.get(0);
                    data.remove(0);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CSVDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CSVDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CSVDataProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return data;
    }
    
}
