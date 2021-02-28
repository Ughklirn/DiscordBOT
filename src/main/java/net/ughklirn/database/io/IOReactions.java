package net.ughklirn.database.io;

import net.ughklirn.database.JDBCAccess;
import net.ughklirn.utils.types.TypeReactions;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IOReactions {
    private JDBCAccess db;

    public IOReactions(JDBCAccess db) {
        this.db = db;
    }

    public String getRow(String id, TypeReactions type) throws SQLException {
        ResultSet rs = this.db.executeQuery("SELECT * FROM reactions WHERE ID = '" + id + "'");
        String cell = null;
        while (rs.next()) {
            cell = rs.getString(type.name());
        }
        return cell;
    }

    public void setRow(String id, TypeReactions type, String value) throws SQLException {
        this.db.executeQuery("SELECT * FROM reactions WHERE ID = " + id).updateString(type.name(), value);
    }
}
