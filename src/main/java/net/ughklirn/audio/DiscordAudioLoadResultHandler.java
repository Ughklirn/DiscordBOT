package net.ughklirn.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class DiscordAudioLoadResultHandler implements AudioLoadResultHandler {
    private final String uri;
    private MusicController controller;

    public DiscordAudioLoadResultHandler(String uri, MusicController controller) {
        this.uri = uri;
        this.controller = controller;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        this.controller.reload();
        this.controller.getTrackScheduler().queue(track);
//        this.controller.getPlayer().playTrack(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        for (AudioTrack track : playlist.getTracks()) {
            this.controller.reload();
            this.controller.getTrackScheduler().queue(track);
//        this.controller.getPlayer().playTrack(track);
        }
    }

    @Override
    public void noMatches() {

    }

    @Override
    public void loadFailed(FriendlyException exception) {

    }
}
