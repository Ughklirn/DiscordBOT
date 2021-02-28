package net.ughklirn.audio.typeloader;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.time.Duration;

public class DiscordAudioLoadResultHandler implements AudioLoadResultHandler {
    private final String uri;
    private MusicController controller;
    private AudioTrack at;
    private AudioPlaylist ap;

    public DiscordAudioLoadResultHandler(String uri, MusicController controller) {
        this.uri = uri;
        this.controller = controller;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        AudioTrackClone.title = track.getInfo().title;
        AudioTrackClone.author = track.getInfo().author;
        AudioTrackClone.uri = track.getInfo().uri;
        AudioTrackClone.length = Duration.ofSeconds(track.getInfo().length);
        at = track.makeClone();

        this.controller.reload();
        this.controller.getPlayer().playTrack(track);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        ap = playlist;
        this.controller.reload();
        for (AudioTrack track : playlist.getTracks()) {
            at = track.makeClone();
            this.controller.getPlayer().playTrack(track);
            AudioTrackClone.title = track.getInfo().title;
            AudioTrackClone.author = track.getInfo().author;
            AudioTrackClone.uri = track.getInfo().uri;
            AudioTrackClone.identifier = track.getInfo().identifier;
            AudioTrackClone.length = Duration.ofSeconds(track.getInfo().length);
            this.controller.reload();
            this.controller.getPlayer().playTrack(track);
        }
    }

    @Override
    public void noMatches() {

    }

    @Override
    public void loadFailed(FriendlyException exception) {

    }

    public AudioTrack getAudioTrack() {
        return at;
    }

    public AudioPlaylist getAudioPlaylist() {
        return ap;
    }
}
