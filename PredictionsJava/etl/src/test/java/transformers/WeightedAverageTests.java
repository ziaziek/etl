/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformers;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.przemo.etl.transformations.WeightedAverageTransformation;
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
public class WeightedAverageTests {
    
    public WeightedAverageTests() {
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
    public void averageTest(){
        int period = 3;
        double[] weights = new double[]{0.5, 0.3, 0.2};
        Table t = getTable();
        WeightedAverageTransformation tr = new WeightedAverageTransformation("A", period, weights);
        tr.setData(t);
        tr.transform();
        Assert.assertTrue(t.containsColumn("WAvg("+period+")"));
        String c = "WAvg("+period+")";
        double delta = 0.001;
        Assert.assertEquals(1.7, (double)t.get(3, c), delta);
        Assert.assertEquals(2.7, (double)t.get(4, c), delta);
        Assert.assertEquals(12.6, (double)t.get(8, c), delta);
        weights[1]=0.5;
        tr = new WeightedAverageTransformation("A", period, weights);
        tr.setData(t);
        tr.transform();
        Assert.assertEquals(1.75, (double)t.get(3, c), delta);
        Assert.assertEquals(2.75, (double)t.get(4, c), delta);
        Assert.assertEquals(12.5, (double)t.get(8, c), delta);
        weights = new double[]{2.5};
        tr =new WeightedAverageTransformation("A", period, weights);
        t = getTable();
        tr.setData(t);
        tr.transform();
        Assert.assertFalse(t.containsColumn(c));
    }
}
