/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.przemo.etl.dataproviders.CSVDataProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
public class CSVDataProviderTest {
    
        private String separator = ";";
        
    public CSVDataProviderTest() {
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

    private String[] data(){
        String[] ret = new String[100];
        Random r = new Random();
        for(int i=0; i<ret.length; i++){
            ret[i] = String.valueOf(r.nextInt());
        }
        return ret;
    }
    
    @Test
    public void writeTest(){
        CSVDataProvider p = new CSVDataProvider("fn", ";", false);
        List<String[]> w = new ArrayList<>();
        for(int i=0; i<100; i++){
            w.add(data());
        }
        p.saveData(w);
        File f = new File("fn");
        Assert.assertTrue(f.exists());
    }
    
    
}
