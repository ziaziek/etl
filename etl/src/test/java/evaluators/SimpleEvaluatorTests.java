/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluators;

import com.przemo.etl.transformations.expressions.SimpleColumnsEvaluator;
import com.przemo.etl.transformations.expressions.onp.ONPConverter;
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
public class SimpleEvaluatorTests {
    
    private class Eve extends SimpleColumnsEvaluator{

        public Eve(String expression) {
            super(expression);
        }
       
    }
    
    public SimpleEvaluatorTests() {
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

//    @Test
//    public void tokenizationTest(){
//        Eve ev = new Eve("[A]+sin([B])-2*[CD]");
//        Assert.assertNotNull(ev);
//        Assert.assertNotNull(ev.getTokens());
//        Assert.assertEquals(3, ev.getTokens().size());
//        Assert.assertEquals("A", ev.getTokens().get(0));
//        Assert.assertEquals(2, ev.getOperators().size());
//        Assert.assertEquals("+sin(", ev.getOperators().get(0));
//    }
    
    @Test
    public void onpTest(){
        String s = "2*sin(x+b, 2)";
        String n = ONPConverter.code(s);
        Assert.assertNotNull(n);
        System.out.println(n);
        Assert.assertTrue(n.length()>0);
        Assert.assertEquals("2", n.substring(0, 1));
        Assert.assertEquals(7, n.split(" ").length);
        
    }
}
