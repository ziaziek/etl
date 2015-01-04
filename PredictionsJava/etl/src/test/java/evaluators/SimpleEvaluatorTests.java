/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluators;

import com.przemo.etl.interfaces.IEvaluator;
import com.przemo.etl.transformations.expressions.SimpleColumnsEvaluator;
import com.przemo.etl.transformations.expressions.SimpleNumberAndStringEvaluator;
import com.przemo.etl.transformations.expressions.SimpleNumberEvaluator;
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
        String s = "((2+7)/3+(14-3)*4)/2";
        String n = ONPConverter.code(s);
        Assert.assertNotNull(n);
        System.out.println(n);
        Assert.assertTrue(n.length()>0);
        Assert.assertEquals("2", n.substring(0, 1));
        Assert.assertEquals(13, n.split(" ").length);
        Assert.assertEquals("2 7 + 3 / 14 3 - 4 * + 2 /", n);
        s="substr('abc',0,1)";
        n = ONPConverter.code(s);
        Assert.assertEquals("'abc'", n.split(" ")[0]);
    }
    
    @Test
    public void numberAndStringEvaluatorTest(){
        String s = "'abc'";
        Assert.assertTrue(new SimpleNumberAndStringEvaluator().isValue(s));
    }
    
    @Test
    public void decodeTest(){
        String s = "2 1 2 + sin *";
        IEvaluator eval = new SimpleNumberEvaluator();
        Assert.assertEquals(2*Math.sin(3), ONPConverter.decode(s, eval));
        s = "2 3 + 5 *";
        Assert.assertEquals(25.0, ONPConverter.decode(s, eval));
        s = "2 7 + 3 / 14 3 - 4 * + 2 /";
        Assert.assertEquals(23.5, ONPConverter.decode(s, eval));
        s = "'abc' 0 2 substr";
        Assert.assertEquals("ab", ONPConverter.decode(s, new SimpleNumberAndStringEvaluator()));
    }
}
