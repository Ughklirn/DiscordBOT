package net.ughklirn.audio;

import net.ughklirn.bot.BotDiscord;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
    private ConcurrentHashMap<String, MusicController> controller;

    public PlayerManager() {
        this.controller = new ConcurrentHashMap<>();
    }

    public MusicController getController(String guild_id) {
        MusicController mc;
        if (this.controller.containsKey(guild_id)) {
            mc = this.controller.get(guild_id);
        } else {
            mc = new MusicController(BotDiscord.getInstance().getJDA().getGuildById(guild_id));
            this.controller.put(guild_id, mc);
        }
        System.out.println("MusicController: " + mc);
        return mc;
    }
}
