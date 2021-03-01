package net.ughklirn.connections;

import net.ughklirn.exceptions.DriverFailedException;
import net.ughklirn.utils.types.TypeRoles;
import net.ughklirn.utils.types.TypeSettings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author Dustin Eikmeier
 * @version 1.0
 * @since 1.8
 */

public class Database implements IDatabase {
    private static IDatabase INSTANCE;
    private JDBCAccess db;

    private Database() {
    }

    public static IDatabase getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        return INSTANCE;
    }

    @Override
    public IDatabase connect() {
        try {
            Class.forName(DbCred.driverClass);
            this.db = new JDBCAccess(DbCred.url, DbCred.user, DbCred.password);
        } catch (DriverFailedException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public boolean disconnect() {
        this.db.close_database();
        return true;
    }

    @Override
    public Connection getConnection() {
        return this.db.getConnection();
    }

    @Override
    public void fillDefault(String id) {
        try {
            this.rsSettings(id);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        try {
            this.rsChannels(id);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        try {
            this.rsCommands(id);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        try {
            this.rsReactions(id);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        try {
            this.rsRoles(id);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    /*
     *
     *
     * fills for defaults with resultset
     *
     *
     */

    private void rsSettings(String id) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("select * from settings");
        System.err.println("IO DB rsSettings: " + id);
        rs.moveToInsertRow();
        rs.updateString("ID", id);
        rs.updateString(TypeSettings.PREFIX.name(), "%");
        rs.updateInt(TypeSettings.MUSIC_VOLUME.name(), 100);
        rs.insertRow();
        rs.close();
    }

    private void rsChannels(String id) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("select * from channels");
        rs.moveToInsertRow();
        rs.updateString("ID", id);
        rs.insertRow();
        rs.close();
    }

    private void rsCommands(String id) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("select * from commands");
        rs.moveToInsertRow();
        rs.updateString("ID", id);
        rs.insertRow();
        rs.close();
    }

    private void rsReactions(String id) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("select * from reactions");
        rs.moveToInsertRow();
        rs.updateString("ID", id);
        rs.insertRow();
        rs.close();
    }

    private void rsRoles(String id) throws SQLException {
        Statement stmt = this.db.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("select * from roles");
        rs.moveToInsertRow();
        rs.updateString("ID", id);
        for (TypeRoles k : TypeRoles.values()) {
            rs.updateString(k.name(), "roles_" + k.name());
        }
        rs.insertRow();
        rs.close();
    }
}