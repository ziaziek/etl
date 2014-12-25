package transformers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.common.collect.Table;
import com.przemo.etl.transformations.RemoveFieldTransformation;
import java.util.ArrayList;
import java.util.List;
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
public class RemoveTransformationTEst {
    
    
    public RemoveTransformationTEst() {
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
        Table t = SimpleTestSample.getTable();
        RemoveFieldTransformation remover = new RemoveFieldTransformation(null,t );        
        List<String> fields = new ArrayList<>();
        fields.add("A1");
        remover.setFieldsToRemove(fields);
        Assert.assertEquals(3, t.columnKeySet().size());;
        remover.transform();
        Assert.assertEquals(2, t.columnKeySet().size());
        Assert.assertNull(t.get(0, "A1"));
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
