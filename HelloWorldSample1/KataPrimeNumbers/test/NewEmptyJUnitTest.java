/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.Assert;
import kataprimenumbers.Primes;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Przemo
 */
public class NewEmptyJUnitTest {
    
    public NewEmptyJUnitTest() {
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
    // @Test
    // public void hello() {}
    
    @Test
    public void isPrime(){
        int[] primesToBe = new int[]{1, 2, 3, 5, 17, 23, 101, 7907, 18232943};
        for(int i=0;i<primesToBe.length-1;i++){
          Assert.assertTrue(String.valueOf(primesToBe[i]),Primes.isPrime(primesToBe[i]));  
        }    
        Assert.assertFalse(Primes.isPrime(14));
    }
    
    @Test
    public void nextPrime(){
        Assert.assertEquals(5, Primes.nextPrime(4));
        Assert.assertEquals(11, Primes.nextPrime(10));
        Assert.assertEquals(17, Primes.nextPrime(13));
        Assert.assertEquals(7919, Primes.nextPrime(7907));
    }
    
}
