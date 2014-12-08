/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dirList.DirectoryList;
import dirList.FileListItem;
import dirList.FileListItemTypes;
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
public class DiFileListUpdateCurrentPathTest {
    
    private class DirFileListExtended extends DirectoryList {
        
        public void update(FileListItem item){
            updateCurrentPath(item);
        }
        
    }
    public DiFileListUpdateCurrentPathTest() {
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
    public void currentFolderTest(){
        DirFileListExtended df = new DirFileListExtended();
        Assert.assertNull(df.getCurrentFolderPath());
        df.setInitialCurrentFolderPath("/");
        Assert.assertEquals("/", df.getCurrentFolderPath());
        df.setInitialCurrentFolderPath("/D");
        Assert.assertEquals("/", df.getCurrentFolderPath());
    }
    
    @Test
    public void updateTest(){
        FileListItem item = new FileListItem("Drhjkii", FileListItemTypes.FILE);
        Assert.assertNotNull(item.getLabel());
        DirFileListExtended df = new DirFileListExtended();
        df.setInitialCurrentFolderPath("/");
        df.update(item);
        Assert.assertEquals("/", df.getCurrentFolderPath());
        item = new FileListItem("D", FileListItemTypes.DIRECTORY);
        df.update(item);
        Assert.assertEquals("/D/", df.getCurrentFolderPath());
        df.update(item);
        Assert.assertEquals("/D/D/", df.getCurrentFolderPath());
        item = new FileListItem("..", FileListItemTypes.DIRECTORY);
        df.update(item);
        Assert.assertEquals("/D/", df.getCurrentFolderPath());
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
