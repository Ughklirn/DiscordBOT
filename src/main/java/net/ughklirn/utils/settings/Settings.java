package net.ughklirn.utils.settings;

public class Settings {
    private String id;
    private SettingsChannels sChannels;
    private SettingsCommands sCommands;
    private SettingsReactions sReactions;
    private SettingsRoles sRoles;

    public Settings(String id) {
        this.id = id;
        this.sChannels = SettingsChannels.getInstance();
        this.sCommands = SettingsCommands.getInstance();
        this.sReactions = SettingsReactions.getInstance();
        this.sRoles = SettingsRoles.getInstance();
    }

    public SettingsChannels getChannels() {
        return sChannels;
    }

    public SettingsCommands getCommands() {
        return sCommands;
    }

    public SettingsReactions getReactions() {
        return sReactions;
    }

    public SettingsRoles getRoles() {
        return sRoles;
    }

    public String getID() {
        return id;
    }
}
