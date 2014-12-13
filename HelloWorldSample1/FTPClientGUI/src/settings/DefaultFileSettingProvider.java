/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import interfaces.ISettingsProvider;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author Przemo
 */
public class DefaultFileSettingProvider extends DefaultHandler implements ISettingsProvider{

    public final String FTP_ADDRESS_XML = "FtpAddr";
    public final String DB_ADDRESS_XML = "DBAddr";
    public final String SENDER_EMAIL_XML="SendEmailAddr";
    public final String EMAIL_PASSWORD_XML = "EmailPass";
    public final String LOGIN_EMAIL_XML = "LoginEmail";
    
    protected String filename;

    private Settings _settings;
    private String currentValue;
    
    public DefaultFileSettingProvider(String FileName) {
        this.filename=FileName;
    }
    
    protected void doParsing(XMLReader xr) throws SAXException{
        try {
                xr.parse(new InputSource(new BufferedInputStream(new FileInputStream(filename))));
            } catch (IOException ex) {
                Logger.getLogger(DefaultFileSettingProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Override
    public Settings loadSettings() {
        _settings = new Settings();
        try {
            XMLReader xr =  XMLReaderFactory.createXMLReader();
            xr.setContentHandler(this);
            doParsing(xr);
        } catch (SAXException ex) {
            Logger.getLogger(DefaultFileSettingProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return _settings;
    }
 
    protected boolean doSaving(Document doc){
        try {
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(filename));
            return true;
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(DefaultFileSettingProvider.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (TransformerException ex) {
            Logger.getLogger(DefaultFileSettingProvider.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
            
    @Override
    public boolean saveSettings(Settings settings) {
        try {
            DocumentBuilder builder = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootNode = doc.createElement("Settings");
            Element ftpaddr = doc.createElement(FTP_ADDRESS_XML);
            ftpaddr.appendChild(doc.createTextNode(settings.getFtpAddress()));
            Element dbaddr = doc.createElement(DB_ADDRESS_XML);
            dbaddr.appendChild(doc.createTextNode(settings.getDatabaseAddress()));
            Element emailP=doc.createElement(EMAIL_PASSWORD_XML);
            emailP.appendChild(doc.createTextNode(new String(settings.getSenderPassword())));
            Element email = doc.createElement(SENDER_EMAIL_XML);
            email.appendChild(doc.createTextNode(settings.getSenderEmail()));
            Element login = doc.createElement(LOGIN_EMAIL_XML);
            login.appendChild(doc.createTextNode(settings.getSenderLogin()));
            rootNode.appendChild(ftpaddr);
            rootNode.appendChild(dbaddr);
            rootNode.appendChild(email);
            rootNode.appendChild(emailP);
            rootNode.appendChild(login);
            doc.appendChild(rootNode);
            return doSaving(doc);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DefaultFileSettingProvider.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    @Override
    public void endElement(String uri, String name, String qName) {

        if (_settings != null) {
            switch (name) {

                case FTP_ADDRESS_XML:
                    _settings.setFtpAddress(currentValue);
                    break;
                case DB_ADDRESS_XML:
                    _settings.setDatabaseAddress(currentValue);
                    break;
                case EMAIL_PASSWORD_XML:
                    _settings.setSenderPassword(currentValue.getBytes());
                    break;
                case SENDER_EMAIL_XML:
                    _settings.setSenderLogin(currentValue);
                    break;
                case LOGIN_EMAIL_XML:
                    _settings.setSenderLogin(currentValue);
                default:
                    break;
            }
        }
    }
    
    @Override
    public void characters(char ch[], int start, int length){
        char[] tmp = new char[length];
        for(int i=0; i<length; i++){
            tmp[i]=ch[start+i];
        }
        currentValue=new String(tmp);
    }
}
