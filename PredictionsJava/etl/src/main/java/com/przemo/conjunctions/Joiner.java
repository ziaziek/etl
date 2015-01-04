/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.conjunctions;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Przemo
 */
public class Joiner {
    
    public Table join(Table[] tables, String columnJoinName, String[] resultingColumns){
        if (tables != null && columnJoinName != null && tables.length > 0) {
            //tables[0] is the main table against which we're going to check the joining values
            Table main = tables[0];           
            Map cl = main.column(columnJoinName);
            Set<Object> frows = cl.keySet();
            if (tables.length > 1 && main.containsColumn(columnJoinName)) {
                if(resultingColumns==null || resultingColumns.length==0){
                    resultingColumns = concatAllColumns(tables);
                }
                filterJoinedRows(tables, columnJoinName, frows, cl);
                return buildJoinedTable(frows, columnJoinName, cl, tables, resultingColumns);
            } else {
                return main;
            }
        } else {
            return null;
        }
}

    /**
     * Filters the given set of rows iterating through the given tables to match the values of the given column name
     * @param tables Array of tables to join with
     * @param columnJoinName Name of the column against which the values will be checked
     * @param frows Set of rows that are common for all the tables. Initially this should be filled with all rows from the main table.
     * @param cl Column and its values of the first (main) table
     */
    protected void filterJoinedRows(Table[] tables, String columnJoinName, Set<Object> frows, Map cl) {
        for (int i = 1; i < tables.length; i++) {
            if(tables[i].containsColumn(columnJoinName)){
                Map cli = tables[i].column(columnJoinName);
                Iterator it = frows.iterator();
                Set<Object> os = new HashSet<>();
                while(it.hasNext()){
                    Object r = it.next();
                    if(!cli.containsKey(r) || cli.get(r)!=cl.get(r)){
                        os.add(r);
                    }
                }
                for(Object rm: os){
                    frows.remove(rm);
                }
            }
        }
    }

    /**
     * Builds a joined table, taking the set of rows and array of columns to include
     * @param frows
     * @param columnJoinName
     * @param cl
     * @param tables
     * @param resultingColumns
     * @return 
     */
    protected Table buildJoinedTable(Set<Object> frows, String columnJoinName, Map cl, Table[] tables, String[] resultingColumns) {
        Table tr = HashBasedTable.create();
        if (resultingColumns != null) {
            //create the tables based on a resulting rows set
            for (Object r : frows) {
                Table nt = HashBasedTable.create();
                nt.put(r, columnJoinName, cl.get(r));
                int ti = 0;
                for (Table ct : tables) {
                    for (String col : resultingColumns) {
                        if (ct.containsColumn(col) && !col.equals(columnJoinName)) {
                            Object v = ct.get(r, col);
                            if (v != null) {
                                if (nt.get(r, col) != null) {
                                    col += "_" + ti;
                                }
                                nt.put(r, col, v);
                            }
                        }
                    }
                    ti++;
                }
                tr.putAll(nt);
            }
        }
        return tr;
    }

    protected String[] concatAllColumns(Table[] tables) {
        Set<String> ret = new HashSet<>();
        for(Table t: tables){
            for(Object c: t.columnKeySet()){
                if(c instanceof String){
                    ret.add((String)c);
                }
            }
        }       
        return ret.toArray(new String[]{});
    }
    
}
