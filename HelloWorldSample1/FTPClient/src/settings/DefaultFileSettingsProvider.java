/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import encryption.Encryptor;
import interfaces.ISettingsProvider;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author Przemo
 */
public class DefaultFileSettingsProvider extends DefaultHandler implements ISettingsProvider{

    public final String FTP_ADDRESS_XML = "FtpAddr";
    public final String FTP_PORT_XML = "FtpPort";
    public final String DB_ADDRESS_XML = "DBAddr";
    public final String SENDER_EMAIL_XML="SendEmailAddr";
    public final String SENDER_EMAIL_PASSWORD_XML = "EmailPass";
    public final String SENDER_EMAIL_LOGIN_XML = "LoginEmail";
    public final String DB_LOGIN_XML="DBLogin";
    public final String DB_PASSWORD_XML="DBPass";
    public final String SENDER_EMAIL_HOST_XML = "EmailHost";
    
    protected String filename;

    private Settings _settings;
    private String currentValue;
    
    public DefaultFileSettingsProvider(String FileName) {
        this.filename=FileName;
    }
    
    protected void doParsing(XMLReader xr) throws SAXException{
        try {
                xr.parse(new InputSource(new BufferedInputStream(new FileInputStream(filename))));
            } catch (IOException | SAXParseException ex) {
                Logger.getLogger(DefaultFileSettingsProvider.class.getName()).log(Level.WARNING, null, ex);
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
            Logger.getLogger(DefaultFileSettingsProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return _settings;
    }
 
    protected boolean doSaving(Document doc){
        try {
            TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(filename));
            return true;
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(DefaultFileSettingsProvider.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (TransformerException ex) {
            Logger.getLogger(DefaultFileSettingsProvider.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
            
    @Override
    public boolean saveSettings(final Settings settings) {
        Map<String, String> settingsMAp = new HashMap<String, String>(){{
                put(FTP_ADDRESS_XML, settings.getFtpAddress());
                put(DB_ADDRESS_XML, settings.getDatabaseAddress());
                put(DB_LOGIN_XML, settings.getDatabaseLogin());
                put(DB_PASSWORD_XML, settings.getDatabasePassword()); 
                put(SENDER_EMAIL_XML, settings.getSenderEmail());
                put(SENDER_EMAIL_LOGIN_XML, settings.getSenderLogin());
                put(FTP_PORT_XML, String.valueOf(settings.getFtpPortNumber()));
                put(SENDER_EMAIL_HOST_XML, settings.getSenderEmailHost());
                put(SENDER_EMAIL_PASSWORD_XML, new String(settings.getSenderPassword()));
        }};

        try {
            DocumentBuilder builder = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();
            Element rootNode = doc.createElement("Settings");
            for (String k : settingsMAp.keySet()) {
                if (settingsMAp.get(k) != null) {
                    Element element = doc.createElement(k);
                    element.appendChild(doc.createTextNode(settingsMAp.get(k)));
                    rootNode.appendChild(element);
                }
            }
            doc.appendChild(rootNode);
            return doSaving(doc);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DefaultFileSettingsProvider.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    @Override
    public void endElement(String uri, String name, String qName) {

        try {
            if (_settings != null) {
                switch (name) {

                    case FTP_ADDRESS_XML:
                        _settings.setFtpAddress(currentValue);
                        break;
                    case DB_ADDRESS_XML:
                        _settings.setDatabaseAddress(currentValue);
                        break;
                    case DB_LOGIN_XML:
                        _settings.setDatabaseLogin(currentValue);
                        break;
                    case DB_PASSWORD_XML:
                        _settings.setDatabasePassword(currentValue);
                        break;
                    case SENDER_EMAIL_PASSWORD_XML:
                         Encryptor enc = new Encryptor();                      
                        _settings.setSenderPassword(new String(enc.encrypt(currentValue.getBytes())).toCharArray());
                        break;
                    case SENDER_EMAIL_XML:
                        _settings.setSenderEmail(currentValue);
                        break;
                    case SENDER_EMAIL_LOGIN_XML:
                        _settings.setSenderLogin(currentValue);
                        break;
                    case FTP_PORT_XML:
                        _settings.setFtpPortNumber(Integer.parseInt(currentValue));
                        break;
                    case SENDER_EMAIL_HOST_XML:
                        _settings.setSenderEmailHost(currentValue);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DefaultFileSettingsProvider.class.getName()).log(Level.WARNING, ex.getMessage());
            
        } finally {
            currentValue="";
        }

    }
    
    @Override
    public void characters(char ch[], int start, int length){
        char[] tmp = new char[length];
        for(int i=0; i<length; i++){
            tmp[i]=ch[start+i];
        }
        if(tmp.length>0){
          currentValue=new String(tmp);  
        } else {
            currentValue="";
        }
        
    }
}
