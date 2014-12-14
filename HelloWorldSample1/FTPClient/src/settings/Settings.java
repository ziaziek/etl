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
    
    private String senderEmailHost=null;

    public String getSenderEmailHost() {
        return senderEmailHost;
    }

    public void setSenderEmailHost(String senderEmailHost) {
        this.senderEmailHost = senderEmailHost;
    }
    
    private int ftpPortNumber = 21;

    public int getFtpPortNumber() {
        return ftpPortNumber;
    }

    public void setFtpPortNumber(int ftpPortNumber) {
        this.ftpPortNumber = ftpPortNumber;
    }
    
    private String databasePassword=null;
    
    private String databaseLogin=null;

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseLogin() {
        return databaseLogin;
    }

    public void setDatabaseLogin(String databaseLogin) {
        this.databaseLogin = databaseLogin;
    }
    
    
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

    public char[] getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(char[] senderPassword) {
        this.senderPassword = senderPassword;
    }
    private String DatabaseAddress = null;
    //email from which the system will send the messages
    private String senderEmail=null;
    //login to the email account.
    private String senderLogin=null;
    private char[] senderPassword=null;
    
    
}
