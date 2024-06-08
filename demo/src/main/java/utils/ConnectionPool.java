package utils;

import java.sql.Connection;
import java.sql.SQLException;
public interface ConnectionPool {
    Connection getConnection(String objectName) throws SQLException;
    void releaseConnection(Connection con, String objectName) throws SQLException;
}
