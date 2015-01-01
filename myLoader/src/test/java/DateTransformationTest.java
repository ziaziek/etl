/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.przemo.myloader.DateTransformation;
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
public class DateTransformationTest {
    
    private class D extends DateTransformation {

        public D(int ShiftHours) {
            super(ShiftHours);
        }
        
        public void sh(int[] src,int s){
            super.shiftHours(src, s);
        }
    }
    
    public DateTransformationTest() {
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
        D d = new D(1);
        int[] date = new int[]{20070101, 0};
        d.sh(date, 1);
        Assert.assertEquals(20070101, date[0]);
        Assert.assertEquals(10000, date[1]);
        d.sh(date, -4);
        Assert.assertEquals(20061231, date[0]);
        Assert.assertEquals(210000, date[1]);
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
