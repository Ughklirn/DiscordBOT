package net.ughklirn.audio.typeloader;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.TextChannel;
import net.ughklirn.audio.GuildMusicManager;
import net.ughklirn.audio.LavaPlayer;

public class BotAudioLoadResultHandler implements AudioLoadResultHandler {
    private String trackUrl;
    private MusicController controller;
    private GuildMusicManager musicManager;
    private TextChannel channel;
    private LavaPlayer player;

    public BotAudioLoadResultHandler(GuildMusicManager musicManager, String trackUrl, TextChannel channel, LavaPlayer player) {
        this.trackUrl = trackUrl;
        this.musicManager = musicManager;
        this.channel = channel;
        this.player = player;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        channel.sendMessage("Adding to queue " + track.getInfo().title).queue();

        player.play(channel.getGuild(), musicManager, track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        AudioTrack firstTrack = playlist.getSelectedTrack();

        if (firstTrack == null) {
            firstTrack = playlist.getTracks().get(0);
        }

        channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

        player.play(channel.getGuild(), musicManager, firstTrack);
    }

    @Override
    public void noMatches() {
        channel.sendMessage("Nothing found by " + trackUrl).queue();
    }

    @Override
    public void loadFailed(FriendlyException exception) {
        channel.sendMessage("Could not play: " + exception.getMessage()).queue();
    }
}
