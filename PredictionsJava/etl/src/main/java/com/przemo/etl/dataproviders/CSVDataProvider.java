/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.dataproviders;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.przemo.etl.interfaces.IDataProvider;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
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

    private void writeSeparatedData(OutputStream wr, String[] data) throws IOException{
        StringBuilder sb = new StringBuilder();
            for(String s: (String[])data){
                sb.append(s).append(sep);
            }
            sb.append("\n");
            System.out.println("Writing separated data.");
            wr.write(sb.toString().getBytes());                 
    }
    
    /**
     * Saves data to a CSV file. If no headers were provided, default ones are created, if previously defined to include headers.
     * @param Data
     * @return 
     */
    @Override
    public boolean saveData(Table Data) {
        OutputStream wr=null;
        try {
             wr = new BufferedOutputStream(new FileOutputStream(this.file));
             
             if(dh){
                 if(headers==null){
                     headers = defaultHeaders(Data);
                 }
                 writeSeparatedData(wr, headers);
                 System.out.println("Saved headers.");
             }
             Set columnsSet = Data.columnKeySet();
             String[] w = new String[columnsSet.size()];
            int i; int cint=0;
            
            System.out.println("Start saving rows.");
            for(Object r: Data.rowKeySet()){
                i=0;
                System.out.println("Saving row no. "+cint);
                for(Object c: columnsSet){
                    Object v = Data.get(r, c);
                    if(v!=null){
                     w[i] = v.toString();   
                    }                
                    i++;
                } 
                cint++;
                System.out.println("Writing the row to the writer stream.");
                writeSeparatedData(wr, w);
                if(cint%1000==0){
                    System.out.println("Saved row no. "+ cint);
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
     * Reads data from a given file. Headers array gets populated either with default ones or with read in from the file, if so requested.
     * @return
     * @throws Exception
     */
    @Override
    public Table readData(){
        Table data = HashBasedTable.create();
        if(file.exists() && file.canRead()){
            try {
                BufferedReader r = new BufferedReader(new FileReader(file));
                String line;
                boolean dhReadIn=false;
                int rowNumber=0;
                while((line=r.readLine())!=null){
                    String[] lineArray =line.split(sep);
                    if(! dhReadIn){
                        if(dh){
                            headers=lineArray;
                        } else{
                            headers = defaultHeaders(lineArray.length);
                        }
                        dhReadIn=true;
                    } else {
                        for(int c=0; c<lineArray.length;c++){
                            data.put(rowNumber, headers[c], lineArray[c]);
                        }
                    }
                    rowNumber++;
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
    
    protected String[] defaultHeaders(int length){
        String[] r = new String[length];
        for(int i=0; i<length; i++){
            r[i]="Column "+ i;
        }
        return r;
    }
    
    protected String[] defaultHeaders(Table data){
        String[] r = new String[data.columnKeySet().size()];
        int i=0;
        for(Object c : data.columnKeySet()){
            if (c instanceof String){
                r[i]=(String)c;
            } else {
                r[i]="Column "+ i;
            }
            i++;
        }
        return r;
    }
    
}
