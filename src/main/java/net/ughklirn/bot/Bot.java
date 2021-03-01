package net.ughklirn.bot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.ughklirn.audio.PlayerManager;
import net.ughklirn.connections.io.InputOutput;

public interface Bot extends Runnable {

    AudioPlayerManager getAudioPlayerManager();

    JDA getJDA();

    InputOutput getIO();

    Guild getGuild();

    void setGuild(Guild guild);

    PlayerManager getPlayerManager();
}
