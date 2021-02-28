package net.ughklirn;

import net.ughklirn.database.Database;
import net.ughklirn.database.IDatabase;
import net.ughklirn.utils.Configuration;
import net.ughklirn.utils.settings.Settings;
import net.ughklirn.utils.settings.SettingsChannels;
import net.ughklirn.utils.settings.SettingsCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartConfig {
    private static final Logger L = LoggerFactory.getLogger(StartConfig.class);
    private static String id = "809702674756665414";

    public static void main(String[] args) {
        IDatabase db = Database.getInstance();
        db.connect();
        Configuration c = Configuration.getInstance();
        Settings settings = c.getSettings(id);
        fill(settings);
        db.fillDefault(id);
    }

    private static void fill(Settings settings) {
        fillChannels(settings.getChannels());
        fillCommands(settings.getCommands());
    }

    private static void fillChannels(SettingsChannels s) {
        s.TEXT_BOT_LOG_ID.add("815300518968426516");
        s.TEXT_COMMANDS_ADMIN_ID.add("815300518968426516");
        s.TEXT_COMMANDS_ID.add("815300518968426516");
        s.TEXT_MUSIC_ID.add("815300518968426516");
        s.TEXT_ROLES_ID.add("815300518968426516");
        s.TEXT_MUSIC_TEAM_ID.add("815300518968426516");
        s.TEXT_MUSIC_USER_ID.add("815300518968426516");
    }

    private static void fillCommands(SettingsCommands s) {

    }
}
