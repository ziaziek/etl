package data;
// Generated 2014-11-16 20:23:20 by Hibernate Tools 4.3.1



/**
 * Ftpuser generated by hbm2java
 */
public class Ftpuser  implements java.io.Serializable {


     private int id;
     private String idklirnta;
     private String email;

    public Ftpuser() {
    }

    public Ftpuser(int id, String idklirnta, String email) {
       this.id = id;
       this.idklirnta = idklirnta;
       this.email = email;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getIdklirnta() {
        return this.idklirnta;
    }
    
    public void setIdklirnta(String idklirnta) {
        this.idklirnta = idklirnta;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }




}


