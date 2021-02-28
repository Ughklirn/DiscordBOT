package net.ughklirn.database.io;

import net.ughklirn.database.JDBCAccess;
import net.ughklirn.utils.types.TypeRoles;

import java.sql.SQLException;

public class IORoles {
    private JDBCAccess db;

    public IORoles(JDBCAccess db) {
        this.db = db;
    }

    public String getRow(String id, TypeRoles type) throws SQLException {
        return this.db.executeQuery("SELECT * FROM channels WHERE ID = " + id).getString(type.name());
    }

    public void setRow(String id, TypeRoles type, String value) throws SQLException {
        this.db.executeQuery("SELECT * FROM channels WHERE ID = " + id).updateString(type.name(), value);
    }
}
