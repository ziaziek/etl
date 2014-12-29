/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Przemo
 */
public class DbConnectorFactory {
    
    public enum DatabaseType{
        MYSQL,
        POSTGRESQL
    }
    
    private static String getDriverName(DatabaseType type){
        String r = null;
        switch(type){
            case MYSQL:
                r="com.mysql.jdbc.Driver";
                break;
            case POSTGRESQL:
                r = "org.postgresql.Driver";
                break;
        }
        return r;
    }
    
    private static String getJDBCString(String driverName){
        if(driverName.contains("mysql")){
            return "jdbc:mysql://";
        } else if (driverName.contains("postgresql")){
            return "jdbc:postgresql://";
        }
        return null;
    }
    
    private static String getJDBCString(DatabaseType type){
        return getJDBCString(getDriverName(type));
    }
    
    public static DbConnector createDbConnector(String driverName, String host, String user, String pass, String database){
        return new DbConnector(driverName, getJDBCString(driverName), host, user, pass, database);
    }
    
    public static DbConnector createDbConnector(DatabaseType type, String host, String user, String pass, String database){
        return createDbConnector(getDriverName(type), host, user, pass, database);
    }
}
