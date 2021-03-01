package net.ughklirn.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.Guild;
import net.ughklirn.bot.BotDiscord;
import net.ughklirn.utils.types.TypeSettings;

import java.sql.SQLException;

public class MusicController {
    private Guild guild;
    private AudioPlayer player;
    private TrackScheduler scheduler;

    public MusicController(Guild guild) {
        this.guild = guild;
        this.player = BotDiscord.getInstance().getAudioPlayerManager().createPlayer();
        this.scheduler = new TrackScheduler(this.player, this.guild);
        this.guild.getAudioManager().setSendingHandler(new DiscordAudioSendHandler(this.player));
        this.player.addListener(this.scheduler);
        try {
            this.player.setVolume(Integer.parseInt(BotDiscord.getInstance().getIO().getSettings().getRow(guild.getId(), TypeSettings.MUSIC_VOLUME)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            this.player.setVolume(100);
        }
    }

    public Guild getGuild() {
        return guild;
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public TrackScheduler getTrackScheduler() {
        return scheduler;
    }

    public void reload() {
        try {
            this.player.setVolume(Integer.parseInt(BotDiscord.getInstance().getIO().getSettings().getRow(guild.getId(), TypeSettings.MUSIC_VOLUME)));
            System.out.println("Finish :)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            this.player.setVolume(100);
        }
    }
}
