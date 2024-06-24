package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPoolImpl implements ConnectionPool{
    private String driver;
    private String username;
    private String password;
    private String url;
    private Stack<Connection> pool;

    public ConnectionPoolImpl() {
        //driver identification
    	this.driver = "com.mysql.cj.jdbc.Driver";
    	//load driver
    	try {
			Class.forName(this.driver);
		} catch (ClassNotFoundException e) {
            System.err.println("Driver not found: " + e.getMessage());
            throw new RuntimeException();
        }
        //account
        this.username = "truong2";
        this.password = "Truong@1234";
        //database url
        this.url = "jdbc:mysql://localhost:3306/university?allowMultiQueries=true";
        this.pool = new Stack<>();
    }

    @Override
    public Connection getConnection(String objectName) throws SQLException {
    	if (this.pool.isEmpty()) {
    		System.out.println(objectName + "init'd a new connection");
    		return DriverManager.getConnection(this.url,this.username,this.password);
    	} else {
    		System.out.println(objectName + "retrieved a connection");
    		return this.pool.pop();
    	}
    }

    @Override
    public void releaseConnection(Connection con, String objectName) throws SQLException {
        System.out.println("released a connection");
        this.pool.push(con);
    }

    @SuppressWarnings("removal")
	@Override
    protected void finalize() throws Throwable {
        try {
            this.pool.clear();
            this.pool=null;
            System.out.println("CPool closed!");
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            super.finalize();
        }
    }
}
