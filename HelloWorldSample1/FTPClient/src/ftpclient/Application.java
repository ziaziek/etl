/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Przemo
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try {
            FTPManager ftpmngr = new FTPManager("localhost", 21);
            ftpmngr.setDb(new DatabaseProxy());
            ftpmngr.LogIn("asia.klich@gmail.com", "derek");
            
            ftpmngr.UploadFile("csb.log", new java.io.BufferedInputStream(new java.io.FileInputStream("D:/TheFile.txt")) );
            
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    
}
