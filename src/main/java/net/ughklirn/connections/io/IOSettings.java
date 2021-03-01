package net.ughklirn.connections.io;

import net.ughklirn.connections.JDBCAccess;
import net.ughklirn.utils.types.TypeSettings;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IOSettings {
    private JDBCAccess db;

    public IOSettings(JDBCAccess db) {
        this.db = db;
    }

    public String getRow(String id, TypeSettings type) throws SQLException {
        System.err.println("IO SET ID: " + id);
        ResultSet rs = this.db.executeQuery("SELECT * FROM settings WHERE ID = '" + id + "'");
        String cell = null;
        while (rs.next()) {
            cell = rs.getString(type.name());
            System.err.println("IO SET: " + cell);
        }
        rs.close();
        return cell;
    }

    public void setRow(String id, TypeSettings type, String value) throws SQLException {
        ResultSet rs = this.db.executeQuery("SELECT * FROM settings WHERE ID = '" + id + "'");
        while (rs.next()) {
            rs.updateString(type.name(), value);
            rs.updateRow();
        }
        rs.close();
    }

    public void setRow(String id, TypeSettings type, int value) throws SQLException {
        ResultSet rs = this.db.executeQuery("SELECT * FROM settings WHERE ID = '" + id + "'");
        while (rs.next()) {
            rs.updateInt(type.name(), value);
            rs.updateRow();
        }
        rs.close();
    }
}
