package dataproviders;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.przemo.etl.dataproviders.CSVDataProvider;
import java.io.File;
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

        private String[] defaultHeaders(int length){
        String[] r = new String[length];
        for(int i=0; i<length; i++){
            r[i]="Column "+ i;
        }
        return r;
    }
        
    private Table data(){
        int p = 100; int pp = 10;
        Table d = HashBasedTable.create(p, pp);
        String[] headers = defaultHeaders(pp);
        Random r = new Random();
        for(int i=0; i<p; i++){
            for(int j=0; j<pp; j++){
                d.put(i, headers[j], String.valueOf(r.nextInt()));
            }
        }
        return d;
    }
    
    @Test
    public void writeTest(){
        CSVDataProvider p = new CSVDataProvider("fn.csv", ";", false);
        Table data = data();
        Assert.assertNotNull(data);
        Assert.assertEquals(10, data.columnKeySet().size());
        Assert.assertEquals(100, data.rowKeySet().size());
        Assert.assertNotNull( data.column("Column 5"));
        Assert.assertNotNull(data.get(0, "Column 1"));
        Assert.assertNotNull(data.get(0, "Column 5"));
        p.saveData(data());
        File f = new File("fn.csv");
        Assert.assertTrue(f.exists());
    }
    
    @Test
    public void headersWriteTest(){
        CSVDataProvider p = new CSVDataProvider("fnh.csv", ";", true);
        Table data = data();
        Assert.assertTrue(p.saveData(data));
        Assert.assertNotNull(p.getHeaders());
    }
    
    @Test
    public void readTest(){
        CSVDataProvider p = new CSVDataProvider("fn.csv", ";", false);
        Table d = p.readData();
        Assert.assertNotNull(d);
        Assert.assertNotNull(p.getHeaders());
        Assert.assertEquals("Column 0", p.getHeaders()[0]);
        Assert.assertTrue(d.rowKeySet().size()>0);
        Assert.assertTrue(d.columnKeySet().size()>0);
    }
    
}
