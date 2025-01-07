package core.saveLoad.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database connection manager
 */
public class DBManager {
    Connection conn;
    String url;
    private final String user;
    private final String pswd;
    
    /**
     * Full constructor
     * @param url the database url
     * @param user the database user 
     * @param pswd the user password
     */
    public DBManager(String url, String user, String pswd){
        this.url=url;
        this.user=user;
        this.pswd = pswd;
    }
    
    /**
     * Localhost database constructor
     * @param user the database user 
     * @param pswd the user password
     */
    public DBManager(String user, String pswd){
        this.url="localhost/testapp";
        this.user=user;
        this.pswd = pswd;
    }
    
    /**
     * Anon user constructor
     * @param url the database url
     */
    public DBManager(String url){
        this.url=url;
        this.user="Anon";
        this.pswd="abc123..";
    }
    
    /**
     * Localhost database + anon user constructor
     */
    public DBManager(){
        this.url="localhost/testapp";
        this.user="Anon";
        this.pswd="abc123..";
    }
    
    /**
     * Connects to the database and/or returns a valid connection
     * @return the database connection
     */
    public Connection getConnection(){
        try {
            if(conn==null || !conn.isValid(0)){
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://"+url, getUser(), pswd);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    conn=null;
                }
            }
            
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Checks if the current user is an admin (has grant_priv privilege)
     * @return Whether the user is an admin or not
     */
    public boolean checkPrivileges(){
        getConnection();
        if(conn==null){
            return false;
        }
        
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("SELECT Grant_priv from mysql.user WHERE USER=?");
            ps.setString(1, getUser());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                if(rs.getString("Grant_priv").equals("Y")){
                    return true;
                }                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);            
        }
        return false;
    }
    
    /**
     * Deletes a test from the database
     * @param id the test id
     * @return operation status
     */
    public boolean deleteFromDB(int id){
        if(conn==null){
            return false;
        }
        
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("DELETE FROM test WHERE testID=?");
            ps.setInt(1, id);
            
            if(ps.executeUpdate()>0){
                return true;
            }     
            
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);            
        }
        return false;
    }   
    

    /**
     * Gets the connected user
     * @return the user
     */
    public String getUser() {
        return user;
    }
        
}
