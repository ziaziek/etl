/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformers;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.przemo.etl.transformations.MovingAverageTransformation;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Przemo
 */
public class MovingAverageTests {
    
    public MovingAverageTests() {
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

    private Table getTable(){
        Table<Integer, String, Double> t = HashBasedTable.create();
        t.put(1, "A", 1.0);
        t.put(2, "A", 2.0);
        t.put(3, "A", 3.0);
        t.put(4, "A", 4.0);
        t.put(5, "A", 10.0);
        t.put(6, "A", 12.0);
        t.put(8, "A", 20.0);
        return t;
    }
    
    @Test
    public void avgTest(){
        Table t = getTable();
        Assert.assertNotNull(t);
        Assert.assertTrue(t.rowKeySet().size()>0);
        MovingAverageTransformation tr = new MovingAverageTransformation("A", 3);
        tr.setData(t);
        Assert.assertEquals("MovAvg", tr.getResultingColumnName());
        tr.setResultingColumnName("MA");
        Assert.assertEquals("MA", tr.getResultingColumnName());
        tr.transform();
        String mc ="MA(3)";
        Assert.assertTrue(t.containsColumn(mc));
        Assert.assertNull(t.get(1, mc));
        Assert.assertEquals(2.0, (double)t.get(3, mc));
        Assert.assertEquals(3.00, (double)t.get(4, mc), 0.01);
        Assert.assertEquals(14.0, (double)t.get(8, mc), 0.0);
    }
}
