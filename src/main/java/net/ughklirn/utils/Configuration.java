package net.ughklirn.utils;

import net.ughklirn.utils.settings.Settings;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private static Configuration INSTANCE = null;
    private String botToken;
    private List<Settings> lSettings;


    private Configuration() {
        this.lSettings = new ArrayList<>();
    }

    public static Configuration getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Configuration();
        }
        return INSTANCE;
    }

    public Settings getSettings(String id) {
        for (Settings k : this.lSettings) {
            if (k != null) {
                if (k.getID().equals(id)) {
                    return k;
                }
            }
        }
        this.lSettings.add(new Settings(id));
        return this.lSettings.get(this.lSettings.size() - 1);
    }
}
