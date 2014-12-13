/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import settings.DefaultFileSettingProvider;
import settings.Settings;

/**
 *
 * @author Przemo
 */
public class FileSettingsProviderTests {
    
    private class DefaultFileSettingsProviderStub extends DefaultFileSettingProvider{

        private final String xml;
        
        private Document document;

        public Document getDocument() {
            return document;
        }
        
        public DefaultFileSettingsProviderStub(String XMLText) {
            super(XMLText);
            xml=XMLText;
        }
        
        @Override
        protected void doParsing(XMLReader xr){
            try {
                xr.parse(new InputSource(new StringReader(xml)));
            } catch (IOException | SAXException ex) {
                Logger.getLogger(FileSettingsProviderTests.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        @Override
        protected boolean doSaving(Document doc){
            document=doc;
            return doc!=null;
        }
        
    }
    public FileSettingsProviderTests() {
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
    public void parseTest(){
        String textToParse = "<?xml version=\"1.0\"?><Settings>"
                + "<FtpAddr>ftp.address</FtpAddr>"
                +"<DBAddr>my.db.address</DBAddr>"
                +"<SendEmailAddr>my@email.com</SendEmailAddr>"
                +"<EmailPass>emailPassword</EmailPass></Settings>";
        DefaultFileSettingsProviderStub sp = new DefaultFileSettingsProviderStub(textToParse);
        Settings s = sp.loadSettings();
        Assert.assertNotNull(s);
        Assert.assertEquals("ftp.address", s.getFtpAddress());
        Assert.assertEquals("my.db.address", s.getDatabaseAddress());
        Assert.assertEquals("my@email.com", s.getSenderLogin());
        Assert.assertEquals("emailPassword",  new String(s.getSenderPassword()));
    }
    
    @Test
    public void saveTest(){
        Settings s = new Settings();
        s.setDatabaseAddress("db.addr");
        s.setFtpAddress("ftp.addr");
        s.setSenderLogin("em");
        s.setSenderPassword("pswd".getBytes());
        s.setSenderEmail("em@");
        DefaultFileSettingsProviderStub stub = new DefaultFileSettingsProviderStub(null);
        Assert.assertTrue(stub.saveSettings(s));
        Assert.assertEquals("db.addr", stub.getDocument().getElementsByTagName(stub.DB_ADDRESS_XML).item(0).getTextContent());
        Assert.assertEquals("ftp.addr", stub.getDocument().getElementsByTagName(stub.FTP_ADDRESS_XML).item(0).getTextContent());
        Assert.assertEquals("em@", stub.getDocument().getElementsByTagName(stub.SENDER_EMAIL_XML).item(0).getTextContent());
        Assert.assertEquals("pswd", stub.getDocument().getElementsByTagName(stub.EMAIL_PASSWORD_XML).item(0).getTextContent());
        Assert.assertEquals("em", stub.getDocument().getElementsByTagName(stub.LOGIN_EMAIL_XML).item(0).getTextContent());
    }
}
