package net.ughklirn.utils.settings;

public class SettingsReactions {
    private static SettingsReactions INSTANCE = null;
    public String ok;
    public String yes;
    public String no;
    public String accept;
    public String deny;
    public String error;
    public String play;
    public String stop;
    public String pause;
    public String volume;
    public String skip;
    public String repeat_track;
    public String repeat_playlist;

    private SettingsReactions() {

    }

    public static SettingsReactions getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsReactions();
        }
        return INSTANCE;
    }
}
