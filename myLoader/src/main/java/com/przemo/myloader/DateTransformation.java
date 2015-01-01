/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.przemo.myloader;

import com.google.common.base.Function;
import com.google.common.collect.Table;
import com.przemo.etl.transformations.ColumnTransformation;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This transformation separates date and time intop two columns. The date/time is in EST (New York), thus should be transformed
 * into GMT, thus moved -5 hours
 * @author Przemo
 */
public class DateTransformation extends ColumnTransformation {
    
    protected void shiftHours(int[] source, int s) {
        Calendar c = Calendar.getInstance();
        c.set(source[0] / 10000, (source[0] % 10000) / 100 - 1, source[0] % 100, source[1] / 10000, (source[1] % 10000) / 100, source[1] % 100);
        c.add(Calendar.HOUR, s);
        source[0] = c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.DAY_OF_MONTH);
        source[1] = c.get(Calendar.HOUR_OF_DAY) * 10000 + c.get(Calendar.MINUTE) * 100 + c.get(Calendar.SECOND);
    }
    
    
    public DateTransformation(final int ShiftHours){
        

        this.transformation = new Function() {

            @Override
            public Object apply(Object f) {
                if(f instanceof Table){
                    Table d = (Table)f;
                    for(Object o: d.rowKeySet()){
                        try {
                            int[] dateInt = makeDate(String.valueOf(d.get(o, "Column 0")));
                            shiftHours(dateInt, ShiftHours);
                            d.put(o, "date", dateInt[0]);
                            d.put(o, "time", dateInt[1]);
                        } catch (Exception ex) {
                            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                return f;
            }

           
            
            private int[] makeDate(String dstr) throws Exception {
                int[] r = new int[2];
                String[] d = dstr.split(" ");
                if(d.length!=2){
                    throw new Exception("Wrong date format!");
                } else {
                    r[0]=Integer.parseInt(d[0]);
                    r[1]=Integer.parseInt(d[1]);
                }
                return r;
            }
        };
    }
    
    @Override
    public void setTransformation(Function f){
        
    }
}
