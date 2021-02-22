package net.ughklirn.bot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.JDA;
import net.ughklirn.audio.PlayerManager;

public interface BOT extends Runnable {

    AudioPlayerManager getAudioPlayerManager();

    PlayerManager getPlayerManager();

    JDA getJDA();
}
