package net.ughklirn.utils.settings;

import java.util.LinkedList;
import java.util.List;

public class SettingsChannels {
    private static SettingsChannels INSTANCE = null;
    public List<String> TEXT_BOT_LOG_ID;
    public List<String> TEXT_COMMANDS_ADMIN_ID;
    public List<String> TEXT_COMMANDS_ID;
    public List<String> TEXT_ROLES_ID;
    public List<String> TEXT_MUSIC_ID;
    public List<String> TEXT_MUSIC_TEAM_ID;
    public List<String> TEXT_MUSIC_USER_ID;

    private SettingsChannels() {
        this.TEXT_BOT_LOG_ID = new LinkedList<>();
        this.TEXT_COMMANDS_ADMIN_ID = new LinkedList<>();
        this.TEXT_COMMANDS_ID = new LinkedList<>();
        this.TEXT_ROLES_ID = new LinkedList<>();
        this.TEXT_MUSIC_ID = new LinkedList<>();
        this.TEXT_MUSIC_TEAM_ID = new LinkedList<>();
        this.TEXT_MUSIC_USER_ID = new LinkedList<>();
    }

    public static SettingsChannels getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsChannels();
        }
        return INSTANCE;
    }
}
