/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.etl.transformations;

import com.google.common.collect.Table;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 *
 * @author Przemo
 */
public class FlatDateGapFiller extends AbstractGapsFiller {

    private final int DEFAULT_TIMEFIELD = Calendar.SECOND;
    private final int DEFAULT_TIMESTEP = 1;
    
    protected int timeStep = DEFAULT_TIMESTEP;

    public int getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
    }
    
    protected int timeField = DEFAULT_TIMEFIELD;

    public int getTimeField() {
        return timeField;
    }

    public void setTimeField(int timeField) {
        this.timeField = timeField;
    }
    
    @Override
    protected boolean isGapped(Object get, Object get0) {
        if(get instanceof Date && get0 instanceof Date){
            Date d1 = (Date)get; Date d2 = (Date)get0;
            Calendar c = Calendar.getInstance();
            Calendar c1 = Calendar.getInstance();
            c.setTime(d1); c1.setTime(d2);
            c1.setTimeInMillis(c1.getTimeInMillis()-c.getTimeInMillis());
            return c1.getTimeInMillis()>(timeStep* getMilisOfField(timeField));
        } else {
            return false;
        }
    }

    @Override
    protected void fillGap(Integer previousKey, Integer rowNumber, Table<Integer, Object, Object> data) {
        Integer maxRowNumber = Collections.max(data.rowKeySet());
        if(gapColumn!=null && data.get(previousKey, gapColumn) instanceof Date && data.get(rowNumber, gapColumn) instanceof Date){
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
          c1.setTime((Date) data.get(previousKey, gapColumn));  
          c2.setTime((Date) data.get(rowNumber, gapColumn));
          long r = (c2.getTimeInMillis()-c1.getTimeInMillis())/(timeStep * getMilisOfField(timeField));
          Calendar cx = Calendar.getInstance();
          cx.setTimeInMillis(timeStep * getMilisOfField(timeField));
          while(c2.getTimeInMillis()>(c1.getTimeInMillis()+cx.getTimeInMillis())){
              c1.setTimeInMillis(c1.getTimeInMillis()+cx.getTimeInMillis());
              data.put(++maxRowNumber, gapColumn, c1.getTime());
              for(Object s: data.columnKeySet()){
                  if(s instanceof String && !s.equals(gapColumn)){
                      String col = (String)s;
                      data.put(maxRowNumber, col, data.get(previousKey, s));
                  }
              }
          }
        }
        
        
        
    }
    
    protected long getMilisOfField(int calendarField){
        switch (calendarField){
            case Calendar.MILLISECOND : return 1;
            case Calendar.SECOND : return 1000;
            case Calendar.MINUTE : return 60000;
            case Calendar.HOUR: case Calendar.HOUR_OF_DAY: return 60*60000;
            case Calendar.DAY_OF_MONTH: case Calendar.DAY_OF_YEAR : case Calendar.DAY_OF_WEEK: case Calendar.DAY_OF_WEEK_IN_MONTH: return getMilisOfField(Calendar.HOUR) * 24;
            case Calendar.YEAR: return getMilisOfField(Calendar.DAY_OF_MONTH*365);
            default: return 0;
        }    
        }

 
}
