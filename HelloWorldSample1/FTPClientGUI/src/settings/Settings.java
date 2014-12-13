/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

/**
 *
 * @author Przemo
 */
public class Settings {
    
    private String ftpAddress=null;

    public String getFtpAddress() {
        return ftpAddress;
    }

    public void setFtpAddress(String ftpAddress) {
        this.ftpAddress = ftpAddress;
    }

    public String getDatabaseAddress() {
        return DatabaseAddress;
    }

    public void setDatabaseAddress(String DatabaseAddress) {
        this.DatabaseAddress = DatabaseAddress;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }

    public byte[] getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(byte[] senderPassword) {
        this.senderPassword = senderPassword;
    }
    private String DatabaseAddress = null;
    //email from which the system will send the messages
    private String senderEmail=null;
    //login to the email account.
    private String senderLogin=null;
    private byte[] senderPassword=null;
    
    
}
