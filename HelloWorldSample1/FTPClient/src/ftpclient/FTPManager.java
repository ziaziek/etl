/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import org.apache.commons.net.ftp.FTPClient;
/**
 *
 * @author Przemo
 */
public class FTPManager {

    FTPClient client;
    int uid;
    
    DatabaseProxy db;

    public DatabaseProxy getDb() {
        return db;
    }

    public void setDb(DatabaseProxy db) {
        this.db = db;
    }
    
    public FTPManager(String host, int portNumber) throws IOException{
        client = new FTPClient();
        client.connect(host, portNumber);
    }
    
    public boolean LogIn(String username, String password) {
        if(client.isConnected()){
            try {
                if(client.login(username, password)){
                    uid=db.findUser(username).getId();
                }
                
                return true;
            } catch (IOException ex) {
                Logger.getLogger(FTPManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } else {
            return false;
        }
    }

    public String getHomeDirectory() {
        if(client.isConnected()){
            try {
                return client.printWorkingDirectory();
            } catch (IOException ex) {
                Logger.getLogger(FTPManager.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        return null;
    }

    public boolean UploadFile(String name, InputStream object) {
        if(client.isConnected()){
            try {
                return client.storeFile(name, object) ;
            } catch (IOException ex) {
                Logger.getLogger(FTPManager.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        
        return false;
    }

    public boolean sendUserMail() {
        try {
            String userEmail=db.getUserEmail(uid);
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", "localhost");
            Session s =  Session.getDefaultInstance(props);
            MimeMessage msg = new MimeMessage(s);
            msg.setFrom("ftpclient@ftpclient.com.pl");
            msg.addRecipients(Message.RecipientType.TO, userEmail);
            msg.setSubject("FTP action");
            msg.setText("FTP action");
            Transport.send(msg);
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(FTPManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
