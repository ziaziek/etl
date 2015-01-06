/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformers;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.przemo.etl.transformations.FlatDateGapFiller;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Przemo
 */
public class FlatDateGapFillerTests extends Assert {
    
    private class FlatG extends FlatDateGapFiller {
        
        public long milisOfField(int f){
            return super.getMilisOfField(f);
        }
        
        @Override
        public boolean isGapped(Object o1, Object o2){
            return super.isGapped(o1, o2);
        }
    }
    
    public FlatDateGapFillerTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void milisOfFieldTest(){
        FlatG fg = new FlatG();
        assertEquals(1, fg.milisOfField(Calendar.MILLISECOND));
        assertEquals(1000, fg.milisOfField(Calendar.SECOND));
        assertEquals(60000, fg.milisOfField(Calendar.MINUTE));
        assertEquals(24*60*60000, fg.milisOfField(Calendar.DAY_OF_MONTH));
    }
    
    @Test
    public void fillGapTest() throws ParseException{
        Table<Integer, String, Object> t = HashBasedTable.create();
        Calendar c = Calendar.getInstance();
        c.set(2014, 6, 1);
        Calendar c1 = Calendar.getInstance();
        c1.set(2014, 6, 3);
        t.put(1, "Date", c.getTime());
        t.put(2, "Date", c1.getTime());
        t.put(1, "A", 25);
        t.put(2, "A", 35);
        FlatG fg = new FlatG();
        fg.setData(t);
        fg.setGapColumn("Date");
        fg.setTimeField(Calendar.HOUR);
        Assert.assertEquals(1, ((Date)t.get(1, "Date")).getDate());
        Assert.assertEquals(3, ((Date)t.get(2, "Date")).getDate());
        Assert.assertTrue(fg.isGapped(t.get(1, "Date"), t.get(2, "Date")));
        fg.transform();
        Assert.assertEquals(49, t.rowKeySet().size(), 1);
        Assert.assertEquals(t.get(1, "A"), t.get(3, "A"));
    }
}
