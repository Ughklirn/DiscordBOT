package net.ughklirn.connections.io;

import net.ughklirn.connections.JDBCAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IOInitialize {
    private JDBCAccess db;

    public IOInitialize(JDBCAccess db) {
        this.db = db;
    }

    public String getToken(String bot) throws SQLException {
        ResultSet rs = this.db.executeQuery("SELECT * FROM initialize WHERE bot = '" + bot + "'");
        String token = null;
        while (rs.next()) {
            token = rs.getString("TOKEN");
        }
        return token;
    }
}
