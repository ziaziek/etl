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
public class FTPManagerInitializationException extends Exception {
    
    @Override
    public String getMessage(){
        return "FTP Manager could not be initialized. Check if settings were provided.";
    }
}
