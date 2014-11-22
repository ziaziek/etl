/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ftpclient.DatabaseProxy;
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
        mngr.setDb(new DBProxyStub(0));
        Assert.assertTrue(mngr.LogIn("asia.klich@gmail.com", "derek"));
        Assert.assertNotNull(mngr.getHomeDirectory());
    }
    
    //Create DBProxy stub
    private class DBProxyStub extends DatabaseProxy {
        
        public int  registrationAttempt = 0;
        
        public DBProxyStub(int i){};
        
        @Override
        public String getUserEmail(int id){
            return "przmnwck@gmail.com";
        }
        
        @Override
        public void registerAction(ftpclient.FTPManagerEvent ev){
            setUsername("Success");
        }
    }
    
    @Test
    public void sendMessage() throws IOException{
        FTPManager mngr = new FTPManager("localhost", 21);
        mngr.setDb(new DBProxyStub(0));
        Assert.assertTrue(mngr.sendUserMail());
    }
    
    @Test
    public void loginRegistrationTest() throws IOException{
        FTPManager mngr = new FTPManager("localhost", 21);
        mngr.setDb(new DBProxyStub(0));
        mngr.addListener(mngr.getDb());
        mngr.LogIn("asia.klich@gmail.com", "derek");
        Assert.assertEquals("Success", mngr.getDb().getUsername());
    }
}
