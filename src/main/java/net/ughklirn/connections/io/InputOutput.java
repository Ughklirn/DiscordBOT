package net.ughklirn.connections.io;

import net.ughklirn.connections.Database;
import net.ughklirn.connections.DbCred;
import net.ughklirn.connections.JDBCAccess;
import net.ughklirn.exceptions.DriverFailedException;

public class InputOutput {
    private static InputOutput INSTANCE = null;
    private JDBCAccess db;
    private IOInitialize init;
    private IOChannels channels;
    private IOCommands commands;
    private IOReactions reactions;
    private IORoles roles;
    private IOSettings settings;

    private InputOutput() {
        try {
            this.db = new JDBCAccess(DbCred.url, DbCred.user, DbCred.password);
        } catch (DriverFailedException e) {
            e.printStackTrace();
        }
        this.init = new IOInitialize(db);
        this.channels = new IOChannels(db);
        this.commands = new IOCommands(db);
        this.reactions = new IOReactions(db);
        this.roles = new IORoles(db);
        this.settings = new IOSettings(db);
    }

    public static InputOutput getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InputOutput();
        }
        return INSTANCE;
    }

    public IOInitialize getInit() {
        return init;
    }

    public IOChannels getChannels() {
        return channels;
    }

    public IOCommands getCommands() {
        return commands;
    }

    public IOReactions getReactions() {
        return reactions;
    }

    public IORoles getRoles() {
        return roles;
    }

    public IOSettings getSettings() {
        return settings;
    }

    public void create(String id) {
        Database.getInstance().connect().fillDefault(id);
//        Database.getInstance().fillDefault(id);
    }
}
