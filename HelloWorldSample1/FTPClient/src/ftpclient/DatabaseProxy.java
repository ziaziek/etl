/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient;

import data.Ftpuser;
import data.HibernateUtil;
import data.Loginhistory;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        List users = s.createQuery("from Ftpuser where idklirnta='"+clientId+"'").list();
        if (users !=null && users.size()>0){
          return  (Ftpuser)users.get(0);  
        } else {
            return null;
        }
    }
    
    @Override
    public void registerAction(FTPManagerEvent evt) {
        if(evt!=null && s.isOpen()){
            int idOperation=0;
            if(evt.action.equals(FTPManagerEvent.EVENT_LOGIN_ACTION)){
                idOperation=2;             
            } else {
                if(evt.action.equals(FTPManagerEvent.EVENT_UPLOAD_ACTION)){
                    idOperation=1;
                }
            }
            logAction(evt, idOperation);
        }
    }

    private void logAction(FTPManagerEvent evt, int idOperation) {
        Transaction tx = s.beginTransaction();
        Loginhistory lgh = new Loginhistory(getNewId("Loginhistory"), (int)evt.details, new Date(), idOperation, (int)evt.result, new Date());
        s.save(lgh);
        tx.commit();
    }
    
    private int getNewId(String classname){
        int id=0;
                List<Integer> ids = s.createQuery("select max(id) from "+classname).list();
                if(ids!=null && ids.size()>0 && ids.get(0)!=null){
                    id=ids.get(0);
                }
        return id+1;
    }
    
}
