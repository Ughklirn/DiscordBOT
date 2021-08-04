package net.ughklirn.connections.io;

import net.ughklirn.connections.JDBCAccess;
import net.ughklirn.utils.types.TypeRoles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IORoles {
    private JDBCAccess db;

    public IORoles(JDBCAccess db) {
        this.db = db;
    }

    public List<String> getRow(String id, TypeRoles type) throws SQLException {
        List<String> lRoles = new ArrayList<>();
        ResultSet rs = this.db.executeQuery("SELECT * FROM roles_" + type.name() + " WHERE ID = '" + id + "'");
        while (rs.next()) {
            lRoles.add(rs.getString("role_name"));
        }
        return lRoles;
    }

    public void setRow(String id, TypeRoles type, String value) throws SQLException {
        this.db.executeQuery("SELECT * FROM roles WHERE SERVER_ID = " + id).updateString(type.name(), value);
    }
}
