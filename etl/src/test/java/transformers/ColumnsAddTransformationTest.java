package transformers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import helpers.SimpleTestSample;
import com.google.common.collect.Table;
import com.przemo.etl.transformations.ColumnsAddTransformation;
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
public class ColumnsAddTransformationTest {
    
    public ColumnsAddTransformationTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void test() {
         Table t = SimpleTestSample.getTable();
         com.przemo.etl.transformations.ColumnsAddTransformation transf = new ColumnsAddTransformation(new String[]{"A1", "A2"});
         transf.setData(t);
         transf.transform();
         t.put(1, "A2", 7);
         transf.transform();
         Assert.assertTrue(t.columnKeySet().contains("Result"));
         Assert.assertEquals(6.0, t.get(0, "Result"));
         Assert.assertEquals(10.0, t.get(1, "Result"));
     }
}
