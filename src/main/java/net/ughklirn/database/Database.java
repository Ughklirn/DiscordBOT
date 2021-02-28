package net.ughklirn.database;

import net.ughklirn.exceptions.DriverFailedException;
import net.ughklirn.utils.DiscordCred;
import net.ughklirn.utils.types.TypeChannels;
import net.ughklirn.utils.types.TypeCommands;
import net.ughklirn.utils.types.TypeReactions;
import net.ughklirn.utils.types.TypeSettings;

import java.sql.*;


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
    public boolean connect() {
        try {
            this.db = new JDBCAccess(DbCred.url, DbCred.user, DbCred.password);
        } catch (DriverFailedException e) {
            e.printStackTrace();
        }
        return true;
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
            this.rsSettings(id);
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
        rs.moveToInsertRow();
        rs.updateString("ID", id);
        rs.updateString(TypeSettings.PREFIX.name(), DiscordCred.BOT_CMD_PREFIX);
        rs.updateInt(TypeSettings.MUSIC_VOLUME.name(), DiscordCred.BOT_SETTINGS_MUSIC_VOLUME);
        rs.insertRow();
    }

    private void rsChannels(String id) throws SQLException {
        String[] tmpArray = {DiscordCred.BOT_TCHANNEL_BOT_LOG_ID, DiscordCred.BOT_TCHANNEL_COMMANDS_ADMIN_ID, DiscordCred.BOT_TCHANNEL_COMMANDS_ID, DiscordCred.BOT_TCHANNEL_ROLES_ID, DiscordCred.BOT_TCHANNEL_MUSIC_ID, DiscordCred.BOT_TCHANNEL_MUSIC_TEAM_ID, DiscordCred.BOT_TCHANNEL_MUSIC_USER_ID};
        int i = 0;
        Statement stmt = this.db.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("select * from channels");
        rs.moveToInsertRow();
        rs.updateString("ID", id);
        for (TypeChannels k : TypeChannels.values()) {
            rs.updateString(k.name(), tmpArray[i++]);
        }
        rs.insertRow();
    }

    private void rsCommands(String id) throws SQLException {
        String[] tmpArray = {DiscordCred.BOT_CMD_TEST, DiscordCred.BOT_CMD_SHUTDOWN, DiscordCred.BOT_CMD_CONFIG_SAVE, DiscordCred.BOT_CMD_CONFIG_LOAD, DiscordCred.BOT_CMD_CONFIG_CLEAR, DiscordCred.BOT_CMD_ROLE_JOIN, DiscordCred.BOT_CMD_ROLE_LEAVE, DiscordCred.BOT_CMD_MUSIC_PLAY, DiscordCred.BOT_CMD_MUSIC_STOP, DiscordCred.BOT_CMD_MUSIC_VOLUME, DiscordCred.BOT_CMD_MUSIC_REPEAT, DiscordCred.BOT_CMD_MUSIC_REPEATS, DiscordCred.BOT_CMD_MUSIC_REPEAT_ALL, DiscordCred.BOT_CMD_MUSIC_PAUSE, DiscordCred.BOT_CMD_MUSIC_SKIP, DiscordCred.BOT_CMD_INIT};
        int i = 0;
        Statement stmt = this.db.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("select * from commands");
        rs.moveToInsertRow();
        rs.updateString("ID", id);
        for (TypeCommands k : TypeCommands.values()) {
            rs.updateString(k.name(), tmpArray[i++]);
        }
        rs.insertRow();
    }

    private void rsReactions(String id) throws SQLException {
        String[] tmpArray = {DiscordCred.BOT_REACTION_OK, DiscordCred.BOT_REACTION_YES, DiscordCred.BOT_REACTION_NO, DiscordCred.BOT_REACTION_ACCEPT, DiscordCred.BOT_REACTION_DENY, DiscordCred.BOT_REACTION_ERROR, DiscordCred.BOT_REACTION_MUSIC_REPEAT_ALL, DiscordCred.BOT_REACTION_MUSIC_REPEAT_ONE, DiscordCred.BOT_REACTION_MUSIC_PLAY, DiscordCred.BOT_REACTION_MUSIC_STOP, DiscordCred.BOT_REACTION_MUSIC_PAUSE, DiscordCred.BOT_REACTION_MUSIC_FORWARD, DiscordCred.BOT_REACTION_MUSIC_NEXT, DiscordCred.BOT_REACTION_MUSIC_VOLUME};
        int i = 1;
        Statement stmt = this.db.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("select * from reactions");
        rs.moveToInsertRow();
        rs.updateString("ID", id);
        for (TypeChannels k : TypeChannels.values()) {
            rs.updateString(k.name(), tmpArray[i++]);
        }
        rs.insertRow();
    }

    /*
     *
     *
     * fills for defaults
     *
     *
     */

    private ResultSet fillChannels(String id) throws SQLException {
        int i = 1;
        PreparedStatement ps = this.db.getConnection().prepareStatement("INSERT INTO channels (id, ?, ?, ?, ?, ?, ?, ?) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        for (TypeChannels k : TypeChannels.values()) {
            ps.setString(i, k.name());
            i++;
        }
        ps.setString(i++, id);
        ps.setString(i++, DiscordCred.BOT_TCHANNEL_BOT_LOG_ID);
        ps.setString(i++, DiscordCred.BOT_TCHANNEL_COMMANDS_ADMIN_ID);
        ps.setString(i++, DiscordCred.BOT_TCHANNEL_COMMANDS_ID);
        ps.setString(i++, DiscordCred.BOT_TCHANNEL_ROLES_ID);
        ps.setString(i++, DiscordCred.BOT_TCHANNEL_MUSIC_ID);
        ps.setString(i++, DiscordCred.BOT_TCHANNEL_MUSIC_TEAM_ID);
        ps.setString(i++, DiscordCred.BOT_TCHANNEL_MUSIC_USER_ID);

        return ps.executeQuery();
    }

    private ResultSet fillCommands(String id) throws SQLException {
        int i = 1;
        PreparedStatement ps = this.db.getConnection().prepareStatement("INSERT INTO commands (id, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        for (TypeCommands k : TypeCommands.values()) {
            ps.setString(i, k.name());
            i++;
        }
        ps.setString(i++, id);
        ps.setString(i++, DiscordCred.BOT_CMD_TEST);
        ps.setString(i++, DiscordCred.BOT_CMD_SHUTDOWN);
        ps.setString(i++, DiscordCred.BOT_CMD_CONFIG_SAVE);
        ps.setString(i++, DiscordCred.BOT_CMD_CONFIG_LOAD);
        ps.setString(i++, DiscordCred.BOT_CMD_CONFIG_CLEAR);
        ps.setString(i++, DiscordCred.BOT_CMD_ROLE_JOIN);
        ps.setString(i++, DiscordCred.BOT_CMD_ROLE_LEAVE);
        ps.setString(i++, DiscordCred.BOT_CMD_MUSIC_PLAY);
        ps.setString(i++, DiscordCred.BOT_CMD_MUSIC_STOP);
        ps.setString(i++, DiscordCred.BOT_CMD_MUSIC_VOLUME);
        ps.setString(i++, DiscordCred.BOT_CMD_MUSIC_REPEAT);
        ps.setString(i++, DiscordCred.BOT_CMD_MUSIC_REPEATS);
        ps.setString(i++, DiscordCred.BOT_CMD_MUSIC_REPEAT_ALL);
        ps.setString(i++, DiscordCred.BOT_CMD_MUSIC_PAUSE);
        ps.setString(i++, DiscordCred.BOT_CMD_MUSIC_SKIP);

        return ps.executeQuery();
    }

    private ResultSet fillReactions(String id) throws SQLException {
        int i = 1;
        PreparedStatement ps = this.db.getConnection().prepareStatement("INSERT INTO reactions (id, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        for (TypeReactions k : TypeReactions.values()) {
            ps.setString(i, k.name());
            i++;
        }
        ps.setString(i++, id);
        ps.setString(i++, DiscordCred.BOT_REACTION_OK);
        ps.setString(i++, DiscordCred.BOT_REACTION_YES);
        ps.setString(i++, DiscordCred.BOT_REACTION_NO);
        ps.setString(i++, DiscordCred.BOT_REACTION_ACCEPT);
        ps.setString(i++, DiscordCred.BOT_REACTION_DENY);
        ps.setString(i++, DiscordCred.BOT_REACTION_ERROR);
        ps.setString(i++, DiscordCred.BOT_REACTION_MUSIC_REPEAT_ALL);
        ps.setString(i++, DiscordCred.BOT_REACTION_MUSIC_REPEAT_ONE);
        ps.setString(i++, DiscordCred.BOT_REACTION_MUSIC_PLAY);
        ps.setString(i++, DiscordCred.BOT_REACTION_MUSIC_STOP);
        ps.setString(i++, DiscordCred.BOT_REACTION_MUSIC_PAUSE);
        ps.setString(i++, DiscordCred.BOT_REACTION_MUSIC_FORWARD);
        ps.setString(i++, DiscordCred.BOT_REACTION_MUSIC_NEXT);
        ps.setString(i++, DiscordCred.BOT_REACTION_MUSIC_VOLUME);

        return ps.executeQuery();
    }

    private ResultSet fillSettings(String id) throws SQLException {
        int i = 1;
        PreparedStatement ps = this.db.getConnection().prepareStatement("INSERT INTO settings (id, ?, ?) VALUES (?, ?, ?)");
        for (TypeSettings k : TypeSettings.values()) {
            ps.setString(i++, k.name());
            System.out.println(k.name());
        }
        ps.setString(i++, id);
        ps.setString(i++, DiscordCred.BOT_CMD_PREFIX);
        ps.setInt(i++, DiscordCred.BOT_SETTINGS_MUSIC_VOLUME);

        return ps.executeQuery();
    }
}