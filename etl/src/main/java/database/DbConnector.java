
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package database;

//~--- JDK imports ------------------------------------------------------------

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author przemo
 */
public class DbConnector {
    protected Connection conn = null;
    
    public DbConnector(String driverString, String JDBCConnectionString, String host, String user, String pass, String database) {
        try {
            Class.forName(driverString);

            try {
                conn = DriverManager.getConnection(JDBCConnectionString + host + "/" + database, user, pass);
            } catch (SQLException ex) {
                Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
            }

            conn = null;
        }
    }

    protected boolean checkConn() throws Exception {
        if (conn == null) {
            throw new Exception("No connection established!");
        }
        return true;
    }

    public ResultSet query(String qry) throws Exception {
        checkConn();

        Statement stmt = conn.createStatement();

        return stmt.executeQuery(qry);
    }

    public boolean execute(String qry) throws Exception {
        return (checkConn() && conn.createStatement().execute(qry));      
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
