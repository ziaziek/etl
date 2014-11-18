/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient;

import data.Ftpuser;
import data.HibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author Przemo
 */
public class DatabaseProxy implements IFTPManagerListener {
    
    String hostName;
    String username;
    String password;
    Session s;
    
    public DatabaseProxy(){
        s = HibernateUtil.getSessionFactory().openSession();
    }
    
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    

    public void setPassword(String password) {
        this.password = password;
    }
    
    public DatabaseProxy(String hostName){
        this.hostName=hostName;
    }

    public String getUserEmail(int id){
       Ftpuser fu = (Ftpuser) s.get(Ftpuser.class, new Integer(id));
       return fu.getEmail();
    }
    
    
    public Ftpuser findUser(String clientId){
        return  (Ftpuser)s.createQuery("from Ftpuser where idklirnta="+clientId);
    }
    
    @Override
    public void registerAction(FTPManagerEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
