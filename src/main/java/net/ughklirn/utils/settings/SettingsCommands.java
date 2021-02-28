package net.ughklirn.utils.settings;

public class SettingsCommands {
    private static SettingsCommands INSTANCE = null;

    private SettingsCommands() {
    }


    public static SettingsCommands getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsCommands();
        }
        return INSTANCE;
    }
}
