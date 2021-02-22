package net.ughklirn.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.Guild;
import net.ughklirn.bot.BOTImpl;
import net.ughklirn.utils.DiscordCred;

public class MusicController {
    private Guild guild;
    private AudioPlayer player;

    public MusicController(Guild guild) {
        this.guild = guild;
        this.player = BOTImpl.INSTANCE.getAudioPlayerManager().createPlayer();
        this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(this.player));
        this.player.setVolume(DiscordCred.BOT_SETTINGS_MUSIC_VOLUME);
    }

    public Guild getGuild() {
        return guild;
    }

    public AudioPlayer getPlayer() {
        return player;
    }
}
