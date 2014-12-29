package transformers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import helpers.SimpleTestSample;
import com.google.common.collect.Table;
import com.przemo.etl.transformations.ColumnRenameTransformation;
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
public class RenameColumnTest {
    
    public RenameColumnTest() {
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
         com.przemo.etl.transformations.ColumnRenameTransformation tr = new ColumnRenameTransformation();
         tr.setData(t);
         tr.setReName("A1", "FirstColumn");
         tr.transform();
         Assert.assertTrue(t.columnKeySet().contains("FirstColumn"));
         Assert.assertFalse(t.columnKeySet().contains("A1"));
     }
}
