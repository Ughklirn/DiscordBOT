package net.ughklirn.database.io;

import net.ughklirn.database.JDBCAccess;
import net.ughklirn.utils.types.TypeCommands;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IOCommands {
    private JDBCAccess db;

    public IOCommands(JDBCAccess db) {
        this.db = db;
    }

    public String getRow(String id, TypeCommands type) throws SQLException {
        ResultSet rs = this.db.executeQuery("SELECT * FROM commands WHERE ID = '" + id + "'");
        String cell = null;
        System.err.println("IO CMD NAME: " + type.name());
        while (rs.next()) {
            cell = rs.getString(type.name());
            System.err.println("IO CMD: " + cell);
        }
        return cell;
    }

    public void setRow(String id, TypeCommands type, String value) throws SQLException {
        this.db.executeQuery("SELECT * FROM commands WHERE ID = " + id).updateString(type.name(), value);
    }
}
