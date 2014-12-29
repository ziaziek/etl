package transformers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import helpers.SimpleTestSample;
import com.google.common.collect.Table;
import com.przemo.etl.transformations.AddFieldTransformation;
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
public class AddFieldTransformationTest {
    
    public AddFieldTransformationTest() {
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
    public void test(){
        Table t  = SimpleTestSample.getTable();
        Assert.assertEquals(12, t.row(0).get("A3"));
        AddFieldTransformation adder = new AddFieldTransformation();
        adder.setData(t);
        adder.setFieldDescriptor("A50");
        Assert.assertEquals(3, t.columnKeySet().size());
        adder.transform();
        Assert.assertEquals(4, t.columnKeySet().size());
        t.put(0, "A50", 2);
        Assert.assertEquals(2, t.get(0, "A50"));
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
