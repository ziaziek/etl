/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transformers;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.przemo.etl.transformations.ColumnTransformation;
import com.przemo.etl.transformations.WindowSampleTransformation;
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
public class WindowSampleTransformationTests {
    
    public WindowSampleTransformationTests() {
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
    public void test(){
        ColumnTransformation tr;
        try {
        tr = new WindowSampleTransformation("A", 2);
        Table t = getTable();
        tr.setData(t);
        Assert.assertFalse(t.containsColumn("A_(-1)"));
        Assert.assertTrue(t.columnKeySet().size()==1);
        tr.transform();
        Assert.assertTrue(t.containsColumn("A_(-1)"));
        Assert.assertTrue(t.columnKeySet().size()==3);
        Assert.assertNull(t.get(1, "A_1"));
        Assert.assertEquals(1.0, t.get(3, "A_(-2)"));
        Assert.assertEquals(2.0, t.get(3, "A_(-1)"));
        Assert.assertEquals(3.0, t.get(3, "A"));
        Assert.assertNull(t.get(2, "A_(-1)"));   
        } catch(Exception ex){
            Assert.fail(ex.getMessage());
        }
        
        try{
            tr = new WindowSampleTransformation("A", 0);
            Assert.fail();
        } catch(Exception e){
            
        }
        
         try{
            tr = new WindowSampleTransformation("A", -2);
            Assert.fail();
        } catch(Exception e){
            
        }
    }
}
