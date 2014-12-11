/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;

import interfaces.ISettingsProvider;

/**
 *
 * @author Przemo
 */
public class Settings {
    
    private ISettingsProvider setprvd = null;
    
    private String ftpAddress=null;
    private String DatabaseAddress = null;
    private String senderEmail=null;
    private String senderLogin=null;
    private byte[] senderPassword=null;
    
    public Settings(ISettingsProvider settingsProvider){
        this.setprvd=settingsProvider;
    } 
    
}
