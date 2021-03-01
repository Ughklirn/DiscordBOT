package net.ughklirn.audio.typeloader;

import net.ughklirn.bot.BotDiscord;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
    private ConcurrentHashMap<Long, MusicController> controller;

    public PlayerManager() {
        this.controller = new ConcurrentHashMap<>();
    }

    public MusicController getController(long guild_id) {
        MusicController mc = null;
        if (this.controller.containsKey(guild_id)) {
            mc = this.controller.get(guild_id);
        } else {
            mc = new MusicController(BotDiscord.getInstance().getJDA().getGuildById(guild_id));
            this.controller.put(guild_id, mc);
        }
        return mc;
    }
}
