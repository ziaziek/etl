/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations;

import com.google.common.collect.Table;
import com.przemo.etl.transformations.AbstractGapsFiller.ValueComparator;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Przemo
 */
public abstract class AbstractGapsFiller extends AbstractTransformer {
    
    protected class ValueComparator implements Comparator<Integer>{

        Map<Integer, Object> myMap;
        
        public ValueComparator(Map<Integer, Object> map){
            myMap=map;
        }
        
        @Override
        public int compare(Integer o1, Integer o2) {
            if(myMap.get(o1) instanceof Comparable && myMap.get(o2) instanceof Comparable && ((Comparable)myMap.get(o1)).compareTo((Comparable)(myMap.get(o2)))<=0){
                return -1;
            } else {
                return 1;
            }
        }
        
    }
    
    protected String gapColumn;
    
    protected String[] filledColumns;

    public void setFilledColumns(String[] filledColumns) {
        this.filledColumns = filledColumns;
    }

    /**
     * Fills the gaps between the columns. It assumes that the column by which
     * we're looking is comparable and is a datetime object
     */
    @Override
    public void transform() {
        SortedMap<Integer, Object> m = new TreeMap(new ValueComparator(data.column(this.gapColumn)));
        m.putAll(data.column(this.gapColumn));
        Integer previousKey = null;
        Set<Integer> currentKeys = m.keySet();
        for (Integer rowNumber : currentKeys) {
            if (previousKey != null && isGapped(m.get(rowNumber), m.get(previousKey))) {
                fillGap(previousKey, rowNumber, data);
            }
            previousKey = rowNumber;
        }
    }

    public void setGapColumn(String gapColumnName) {
        this.gapColumn = gapColumnName;
    }

    protected abstract boolean isGapped(Object get, Object get0);

    protected abstract void fillGap(Integer previousKey, Integer rowNumber, Table<Integer, Object, Object> data);
}
