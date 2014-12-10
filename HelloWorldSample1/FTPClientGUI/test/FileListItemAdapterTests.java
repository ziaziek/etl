/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dirList.FileListItem;
import dirList.FileListItemTypes;
import dirList.IFileListItemAdapter;
import ftpclientgui.FTPListItemAdapter;
import junit.framework.Assert;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Przemo
 */
public class FileListItemAdapterTests {
    
    private IFileListItemAdapter adapter = null;
    
    public FileListItemAdapterTests() {
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
        adapter = new FTPListItemAdapter();
        FTPFile f = new FTPFile();
        f.setName("a");
        f.setType(FTPFile.DIRECTORY_TYPE);
        FileListItem tested = adapter.getFileListItem(f);
        Assert.assertNotNull(tested);
        Assert.assertEquals("a", tested.getLabel());
        Assert.assertEquals(FileListItemTypes.DIRECTORY, tested.getType());
        
    }
            
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
