package net.ughklirn.database.io;

import net.ughklirn.database.JDBCAccess;

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
