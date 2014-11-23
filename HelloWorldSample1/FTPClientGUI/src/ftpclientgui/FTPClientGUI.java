/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclientgui;

import ftpclient.DatabaseProxy;
import ftpclient.FTPManager;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Przemo
 */
public class FTPClientGUI {

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
                        FTPManager m = new FTPManager("localhost", 21);
                        m.setDb(new DatabaseProxy());
                        MainWindow mw = new MainWindow(m);
                        mw.setVisible(true);
                    } catch (IOException ex) {
                        Logger.getLogger(FTPClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            });
        } catch (InterruptedException | InvocationTargetException ex) {
            Logger.getLogger(FTPClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
