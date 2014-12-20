/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclientgui;

import ftpclient.DatabaseProxy;
import ftpclient.FTPManager;
import ftpclient.FTPManagerInitializationException;
import interfaces.ISettingsProvider;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import settings.DefaultFileSettingsProvider;

/**
 *
 * @author Przemo
 */
public class FTPClientGUI {

    public static final String SETTINGS_FILENAME = "ftpClientSettings.xml";
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            SwingUtilities.invokeAndWait(new Runnable(){

                @Override
                public void run() {
                    try {
                        if(Files.notExists(Paths.get(SETTINGS_FILENAME), LinkOption.NOFOLLOW_LINKS)){
                            Files.createFile(Paths.get(SETTINGS_FILENAME));
                        }
                        ISettingsProvider settingsProvider = new DefaultFileSettingsProvider(SETTINGS_FILENAME);
                        FTPManager m = new FTPManager(settingsProvider.loadSettings());
                        m.initialiseManager();
                        m.setDb(new DatabaseProxy());
                        MainWindow mw = new MainWindow(m, settingsProvider);
                        mw.setVisible(true);
                    } catch (IOException | FTPManagerInitializationException ex) {
                        Logger.getLogger(FTPClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "FTP Manager could not be initialised. Check settings.", "Initialization", JOptionPane.ERROR_MESSAGE);
                        SettingsForm frm = new SettingsForm(new DefaultFileSettingsProvider(SETTINGS_FILENAME));
                        frm.setVisible(true);
                    }
                }
                
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(FTPClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
