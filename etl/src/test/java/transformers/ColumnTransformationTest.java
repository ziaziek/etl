package transformers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.common.base.Function;
import com.google.common.collect.Table;
import com.przemo.etl.transformations.ColumnTransformation;
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
public class ColumnTransformationTest {
    
    public ColumnTransformationTest() {
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
    public void sumTest(){
        Table t = SimpleTestSample.getTable();
        ColumnTransformation transformer = new ColumnTransformation();
        transformer.setData(t);
        //sum of two columns
        transformer.setTransformation(new Function() {

            @Override
            public Object apply(Object f) {
                if(f instanceof Table){
                    Table tin = (Table)f;
                    for(Object row: tin.rowKeySet()){
                        if(tin.get(row, "A1") instanceof Number && tin.get(row, "A2") instanceof Number){
                           tin.put(row, "Result", ((Number)(tin.get(row, "A1"))).doubleValue()+((Number)(tin.get(row, "A2"))).doubleValue()); 
                        }                     
                    }
                }
                return f;
            };           
        });
        transformer.transform();
        Assert.assertTrue(t.columnKeySet().contains("Result"));
        Assert.assertEquals(6.0, t.get(0, "Result"));
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
