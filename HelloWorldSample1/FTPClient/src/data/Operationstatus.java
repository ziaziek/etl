package data;
// Generated 2014-11-22 00:30:39 by Hibernate Tools 4.3.1



/**
 * Operationstatus generated by hbm2java
 */
public class Operationstatus  implements java.io.Serializable {


     private int id;
     private String statusname;
     private String description;

    public Operationstatus() {
    }

	
    public Operationstatus(int id, String statusname) {
        this.id = id;
        this.statusname = statusname;
    }
    public Operationstatus(int id, String statusname, String description) {
       this.id = id;
       this.statusname = statusname;
       this.description = description;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getStatusname() {
        return this.statusname;
    }
    
    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }




}


