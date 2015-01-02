/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package joinersandfilters;

import com.google.common.collect.Table;
import com.przemo.conjunctions.Joiner;
import java.util.Map;
import java.util.Set;
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
public class JoinerTests {
    
    
    private class JStub extends Joiner{
        
        public void filterRows(Table[] t, String col, Set<Object> rows, Map cl){
            super.filterJoinedRows(t, col, rows, cl);
        }
        
        public Table joinedTable(Set<Object>rows, String columnName, Map cl, Table[] t, String[] resultingColumns){
            return super.buildJoinedTable(rows, columnName, cl, t, resultingColumns);
        }
        
        public String[] concatColumnsStub(Table[] t){
            return super.concatAllColumns(t);
        }
    }
    
    public JoinerTests() {
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
    public void filterJoinedRowsTest(){
        String col = "A1";
        Table t1 = helpers.SimpleTestSample.getTable(0);
        Table t2 = helpers.SimpleTestSample.getTable(0);
        t1.put(2, "A1", 100);
        JStub j = new JStub();
        Set<Object> rows = t1.rowKeySet();
        j.filterRows(new Table[]{t1, t2}, col, rows, t1.column(col));
        Assert.assertNotNull(rows);
        Assert.assertTrue(rows.size()>0);
        Assert.assertEquals(2, rows.size());
        
    }
    
    @Test
    public void buildJoinedTableTest(){
        String col="A1";
        Table t1 = helpers.SimpleTestSample.getTable(0);
        Table t2 = helpers.SimpleTestSample.getTable(0);
        JStub j = new JStub();
        Set<Object> rows = t1.rowKeySet();
        j.filterRows(new Table[]{t1, t2}, col, rows, t1.column(col));
        Table tres = j.joinedTable(rows, col, t1.column(col), new Table[]{t1, t2}, j.concatColumnsStub(new Table[]{t1, t2}));
        Assert.assertNotNull(tres);
        Assert.assertTrue(tres.columnKeySet().size()>0 && tres.rowKeySet().size()>0);
        Assert.assertEquals(2, tres.rowKeySet().size());
        Assert.assertEquals(5, tres.columnKeySet().size());
        tres = j.joinedTable(rows, col, t1.column(col), new Table[]{t1, t2}, new String[]{"A2"});
        Assert.assertNotNull(tres);
        Assert.assertTrue(tres.columnKeySet().size()>0 && tres.rowKeySet().size()>0);
        Assert.assertEquals(2, tres.rowKeySet().size());
        Assert.assertEquals(3, tres.columnKeySet().size());
    }
    
    
}
