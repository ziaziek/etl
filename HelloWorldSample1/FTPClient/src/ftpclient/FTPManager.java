/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient;

import data.Ftpuser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
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
    int uid=-1;
    List<IFTPManagerListener> listeners = null;
    
    DatabaseProxy db;

    public DatabaseProxy getDb() {
        return db;
    }

    public void setDb(DatabaseProxy db) {
        this.db = db;
        addListener(db);
    }
    
    public FTPManager(String host, int portNumber) throws IOException{
        client = new FTPClient();
        client.connect(host, portNumber);
        listeners = new ArrayList<>();
    }
    
    public void addListener(IFTPManagerListener l){
        if(!listeners.contains(l)){
            listeners.add(l);
        }
    }
    
    public void removeListener(IFTPManagerListener l){
        listeners.remove(l);
    }
    
    public void notifyListeners(FTPManagerEvent ev){
        for(IFTPManagerListener l: listeners){
            l.registerAction(ev);
        }
    }
    
    public boolean LogIn(String username, String password) {
        if(client.isConnected()){
            try {
                if(client.login(username, password)){
                    Ftpuser usr;
                    if ((usr = db.findUser(username)) != null) {
                        uid = usr.getId();
                        FTPManagerEvent ev = new FTPManagerEvent();
                        ev.action = "Login";
                        ev.result=1;
                        ev.details=uid;
                        notifyListeners(ev);
                    }

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
                if(!client.storeFile(name, object)){
                    //do something with the response code
                    return false;
                } else {
                    return true;
                }
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
            props.setProperty("mail.smtp.host", "www.poczta.fm");
            props.setProperty("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.smtp.port", "587");
            Session s =  Session.getDefaultInstance(props, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("ziaziek@poczta.fm", "Saba123");
                }
            });
            MimeMessage msg = new MimeMessage(s);
            msg.setFrom("ziaziek@poczta.fm");
            msg.addRecipients(Message.RecipientType.TO, userEmail);
            msg.setSubject("FTP action");
            msg.setText("FTP action has been recorded");
            Transport.send(msg);
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(FTPManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
