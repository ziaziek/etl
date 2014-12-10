/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dirList.DirectoryList;
import dirList.FileListItem;
import dirList.FileListItemTypes;
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
public class DirListTests {
    
    public DirListTests() {
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
    public void itemsCountTest(){
        DirectoryList dl = new DirectoryList();
        FileListItem[] v = new FileListItem[]{new FileListItem("A", FileListItemTypes.FILE), new FileListItem("B", FileListItemTypes.FILE)};
        dl.setListData(v);
        Assert.assertEquals(2, dl.getModel().getSize());
        v = new FileListItem[]{new FileListItem("C", FileListItemTypes.FILE)};
        Assert.assertEquals(2, dl.getModel().getSize());
        dl.setListData(v);
        Assert.assertEquals(1, dl.getModel().getSize());
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
