/**
 * @author Dustin Eikmeier
 * @version 1.0
 * @since 1.8
 */

package net.ughklirn.connections;

import net.ughklirn.exceptions.DriverFailedException;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.Properties;

public class JDBCAccess {
    private Connection dbcon;
    private Statement stmt;

    public JDBCAccess(String db_url, String username, String password) throws DriverFailedException {
        try {
            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);
            dbcon = DriverManager.getConnection(db_url, props);
            stmt = dbcon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (PSQLException e) {
            throw new DriverFailedException(e.getMessage());
        } catch (Exception e) {
            System.err.println("Can't get a connection: " + e.toString());
            try {
                if (stmt != null) stmt.close();

                if (dbcon != null) dbcon.close();
            } catch (SQLException f) {
            }
            //System.exit(0);
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Can't execute Query!" + e.toString());
            return null;
        }
    }

    public void close_database() {
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (dbcon != null) dbcon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("database.Database closed");
    }

    public Connection getConnection() {
        return dbcon;
    }
}
