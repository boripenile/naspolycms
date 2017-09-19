package cms.academic.academicapp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cms.academic.academicapp.handler.TechnicalException;

public class ConnectionManager {	
	private static final String URL = "jdbc:mysql://localhost:3306/";
	private static final String DATABASE = "cms_academic";
    private static final String USERNAME = "root";    
    private static final String PASSWORD = "second";    
    private static final String CLASSNAME = "com.mysql.jdbc.Driver";
    
    private ConnectionManager(){
    	throw new AssertionError();
    }
    /**
     * Get valid DB connection
     * @return
     * @throws TechnicalException
     */
    public static Connection getConnection() throws TechnicalException {
        Connection connection = null;
        try {        	
        	try {
				Class.forName(CLASSNAME).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}			
            connection = DriverManager.getConnection(URL+DATABASE, USERNAME, PASSWORD);         
        } catch (SQLException ex) {
            throw new TechnicalException("can't open connection.");
        }
        return connection;
    }
    
    /**
     * Release a DB connection
     * @param connection
     * @throws TechnicalException
     */
    public static void releaseConnection(Connection connection) throws TechnicalException {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new TechnicalException("couldn't close the connection");
        }
    }
}
