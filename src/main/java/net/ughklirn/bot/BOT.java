package net.ughklirn.bot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.ughklirn.audio.PlayerManager;
import net.ughklirn.utils.Config;

import java.util.List;

public interface BOT extends Runnable {

    AudioPlayerManager getAudioPlayerManager();

    PlayerManager getPlayerManager();

    JDA getJDA();

    Config getConfig();

    List<Guild> getGuilds();
}
