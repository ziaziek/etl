/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataproviders;

import com.google.common.collect.Table;
import com.przemo.etl.dataproviders.DefaultDatabaseDataProvider;
import com.przemo.etl.dataproviders.SimpleMapper;
import com.przemo.etl.interfaces.IDataMapper;
import database.DbConnector;
import helpers.SimpleTestSample;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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
public class DatabaseProviderTest {
    
    private class DBProviderMock extends DefaultDatabaseDataProvider{

        public DBProviderMock(DbConnector connector, IDataMapper columnsMapper, String tableName) {
            super(connector, columnsMapper, tableName);
        }
        
        public String insertQueryHeader(Table Data){
            return prepareMappedQuery(getMapper(), Data);
        }
        
        public String getValuesPart(Table Data, Object row){
            return prepareQueryValues(Data, row);
        }
        
        public String getValsReplacer(){
            return super.valsReplacer;
        }
    }
    
    public DatabaseProviderTest() {
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
    public void queryNoMappingPrepareTest(){
        Table t = SimpleTestSample.getTable();
        DBProviderMock mock = new DBProviderMock(null, null, "Tab1");     
        String q = mock.insertQueryHeader(t);
        Assert.assertTrue(q.contains("A1"));
        Assert.assertTrue(q.contains("A2"));
        Assert.assertTrue(q.contains("A3"));
        Assert.assertTrue(q.contains("Tab1"));
        System.out.println(q);
    }
    
    @Test
    public void queryMappingPrepareTest(){
        Table t = SimpleTestSample.getTable();
        IDataMapper mapper = new SimpleMapper();
        Map<String, String> colMapping = new HashMap<>();
        colMapping.put("A1", "X1");
        colMapping.put("A2", "X2");
        mapper.setMapping(colMapping);
        DBProviderMock mock = new DBProviderMock(null, mapper, "Tab1");  
        String q = mock.insertQueryHeader(t);
        Assert.assertTrue(q.contains("Tab1"));
        Assert.assertTrue(q.contains("X1"));
        Assert.assertTrue(q.contains("X2"));
        Assert.assertFalse(q.contains("A1"));
        Assert.assertFalse(q.contains("A2"));
        Assert.assertFalse(q.contains("A3"));
        System.out.println(q);
    }
    
    @Test
    public void queryValsMappingTest(){
        Table t = SimpleTestSample.getTable();
        IDataMapper mapper = new SimpleMapper();
        Map<String, String> colMapping = new LinkedHashMap<>();
        colMapping.put("A1", "X1");
        colMapping.put("A2", "X2");
        mapper.setMapping(colMapping);
        DBProviderMock mock = new DBProviderMock(null, mapper, "Tab1");  
        String q = mock.insertQueryHeader(t);
        String c = mock.getValuesPart(t, 0);
        Assert.assertNotNull(c);
        Assert.assertTrue(c.length()>0);
        String firstPosition = c.substring(0, c.indexOf(","));
        Assert.assertTrue(firstPosition.length()>0);
        Assert.assertFalse(firstPosition.contains(","));
        String firstColumn = (String)colMapping.keySet().toArray()[0];
        Assert.assertEquals(firstPosition, String.valueOf(t.get(0, firstColumn)));
        Assert.assertEquals(mapper.getMapping().keySet().size(), c.split(",").length);
        System.out.println(q.replace(mock.getValsReplacer(), c));
        
    }
}
