/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ftpclient.FTPManager;
import java.io.IOException;
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
    public void ftpConnectionTest() throws IOException{
        FTPManager mngr = new FTPManager("localhost", 21);
        Assert.assertTrue(mngr.LogIn("asia.klich@gmail.com", "derek"));
        Assert.assertNotNull(mngr.getHomeDirectory());
        Assert.assertFalse(mngr.UploadFile(null, null));
        Assert.assertTrue(mngr.sendUserMail());
    }
}
