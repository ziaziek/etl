/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient;

/**
 *
 * @author Przemo
 */
public class FTPManagerEvent {
 
    public static final String EVENT_LOGIN_ACTION="Login";
    
    public static final String EVENT_UPLOAD_ACTION="Upload";
    
    
    public String action;
    
    public Object result;
    
    public Object details;
    
    public int FTPResponseCode;
    
}
