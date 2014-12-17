/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryption;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Przemo
 */
public class Encryptor {
    
    public enum ENCRYPTOR_MODE {
        ALL_INIT,
        NO_INIT,
        LOAD_INIT
    }
    
    Cipher cipher = null;
    SecretKeySpec key = null;
    javax.crypto.spec.IvParameterSpec ivSpec = null;        
    protected final String FILENAME = "dgjesjiko";
    protected final String PARAM_FILE = "jjdsyj";
    
    
    public Encryptor(ENCRYPTOR_MODE mode){
        switch(mode){
            case ALL_INIT:
                initialize();
                break;
            case LOAD_INIT:
                loadKeyAndParams();
                break;
            case NO_INIT:
                break;          
        }
    }
    
    public Encryptor() {
        this(ENCRYPTOR_MODE.ALL_INIT);
    }
    
    private void initialize(){
      try {
            KeyGenerator keygen = KeyGenerator.getInstance("DES");
            keygen.init(56);
            SecureRandom random = new SecureRandom();
            byte iv[] = new byte[8]; //generate random 8 byte IV. 
            random.nextBytes(iv);
            createKeyAndParams(keygen.generateKey().getEncoded(), iv, true);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    protected Cipher getCipher(){
        if (cipher == null && key != null && ivSpec != null) {
            try {
                cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
                Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cipher;
    }
    
    protected void saveKey(){
        try (OutputStream o = new FileOutputStream(FILENAME)) {
            o.write(key.getEncoded());
            o.close();
        } catch (IOException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected final void loadKeyAndParams(){
        try {
            byte[] b = Files.readAllBytes(Paths.get(FILENAME));
            byte[] b1 = Files.readAllBytes(Paths.get(PARAM_FILE));
            createKeyAndParams(b, b1, false);
        } catch (IOException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void saveParams() {
        try (OutputStream o = new FileOutputStream(PARAM_FILE)) {
            o.write(ivSpec.getIV());
            o.close();
        } catch (IOException ex) {
            Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
    public final void createKeyAndParams(byte[] keyBytes, byte[] ivParams, boolean save){  
        key = new SecretKeySpec(keyBytes, "DES");
        ivSpec = new IvParameterSpec(ivParams);
        if(save && getCipher()!=null){
           saveKey(); 
            saveParams();
        }
    }
    
    public byte[] encrypt(byte[] s) {
        
        if (getCipher() != null) {
            try {
                cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
                return cipher.doFinal(s);
            } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        return null;
    }
    
    public byte[] decrypt(byte[] s){
        loadKeyAndParams();
        if(getCipher()!=null){
            try {
                cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
                return cipher.doFinal(s);
            } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(Encryptor.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        return null;
    }
}
