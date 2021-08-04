package net.ughklirn.connections.io;

import net.ughklirn.connections.JDBCAccess;
import net.ughklirn.utils.types.TypeChannels;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IOChannels {
    private JDBCAccess db;

    public IOChannels(JDBCAccess db) {
        this.db = db;
    }

    public String getRow(String id, TypeChannels type) throws SQLException {
        ResultSet rs = this.db.executeQuery("SELECT * FROM channels WHERE ID = '" + id + "'");
        String cell = null;
        while (rs.next()) {
            cell = rs.getString(type.name());
        }
        return cell;
    }

    public void setRow(String id, TypeChannels type, String value) throws SQLException {
        this.db.executeQuery("SELECT * FROM channels WHERE ID = " + id).updateString(type.name(), value);
    }
}
