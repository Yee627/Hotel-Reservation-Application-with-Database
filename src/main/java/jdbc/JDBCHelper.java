package main.java.jdbc;

import main.java.jdbc.JDBCConstants;

import java.sql.*;

public class JDBCHelper {
    public static Connection connection;
    static
    {
        try {
            Class.forName( JDBCConstants.DRIVER_NAME );
        } catch ( ClassNotFoundException e ) {
            System.out.println( "Driver class not found" );
        }
    }

    public static Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(JDBCConstants.URL, JDBCConstants.USERNAME, JDBCConstants.PASSWORD);
        return connection;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void closePreparedStatement(PreparedStatement stmt) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
    }

    public static void closeResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

}
