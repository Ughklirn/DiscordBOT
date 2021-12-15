package net.ughklirn.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.Guild;
import net.ughklirn.bot.BOTImpl;
import net.ughklirn.utils.Config;

public class MusicController {
    private Guild guild;
    private AudioPlayer player;

    public MusicController(Guild guild) {
        this.guild = guild;
        this.player = BOTImpl.INSTANCE.getAudioPlayerManager().createPlayer();
        this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(this.player));
        if (Config.getInstance().getTextChannel_Music_Volume().get(guild) != null) {
            this.player.setVolume(Config.getInstance().getTextChannel_Music_Volume().get(guild));
        } else {
            Config.getInstance().setVolume(guild, Config.getInstance().getBotMusic_Volume());
            this.player.setVolume(Config.getInstance().getTextChannel_Music_Volume().get(guild));
        }
    }

    public Guild getGuild() {
        return guild;
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public void reload() {
        this.player.setVolume(Config.getInstance().getTextChannel_Music_Volume().get(guild));
    }
}
