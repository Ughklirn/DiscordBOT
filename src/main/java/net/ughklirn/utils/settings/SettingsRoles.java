package net.ughklirn.utils.settings;

import java.util.ArrayList;
import java.util.List;

public class SettingsRoles {
    private static SettingsRoles INSTANCE = null;
    public List<String> lStaffs;
    public List<String> lTeams;
    public List<String> lClans;
    public List<String> lGames;

    private SettingsRoles() {
        this.lStaffs = new ArrayList<>();
        this.lTeams = new ArrayList<>();
        this.lClans = new ArrayList<>();
        this.lGames = new ArrayList<>();
    }


    public static SettingsRoles getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsRoles();
        }
        return INSTANCE;
    }
}
