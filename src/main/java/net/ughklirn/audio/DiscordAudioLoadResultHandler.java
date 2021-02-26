package net.ughklirn.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.time.Duration;

public class DiscordAudioLoadResultHandler implements AudioLoadResultHandler {
    private final String uri;
    private MusicController controller;
    private boolean isRepeatAll;
    private boolean isRepeatTrack;
    private AudioTrack at;
    private AudioPlaylist ap;
    private String title;
    private String author;
    private String uri_track;
    private long length;

    public DiscordAudioLoadResultHandler(String uri, MusicController controller) {
        this.uri = uri;
        this.controller = controller;
        this.isRepeatAll = false;
        this.isRepeatTrack = false;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        AudioTrackClone.title = track.getInfo().title;
        AudioTrackClone.author = track.getInfo().author;
        AudioTrackClone.uri = track.getInfo().uri;
        AudioTrackClone.length = Duration.ofSeconds(track.getInfo().length);
        at = track.makeClone();
        do {
            this.controller.reload();
            this.controller.getPlayer().playTrack(track);
            System.out.println(getTrackInfo());
        }
        while ((this.isRepeatAll));
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        do {
            ap = playlist;
            this.controller.reload();
            for (AudioTrack track : playlist.getTracks()) {
                at = track.makeClone();
                do {
                    this.controller.getPlayer().playTrack(track);
                    AudioTrackClone.title = track.getInfo().title;
                    AudioTrackClone.author = track.getInfo().author;
                    AudioTrackClone.uri = track.getInfo().uri;
                    AudioTrackClone.length = Duration.ofSeconds(track.getInfo().length);
                    this.controller.reload();
                    this.controller.getPlayer().playTrack(track);
                } while (this.isRepeatTrack);
            }
        }
        while (this.isRepeatAll);
    }

    @Override
    public void noMatches() {

    }

    @Override
    public void loadFailed(FriendlyException exception) {

    }

    public boolean isRepeatAll() {
        return isRepeatAll;
    }

    public boolean isRepeatTrack() {
        return isRepeatTrack;
    }

    public boolean changeRepeatAll() {
        this.isRepeatAll = !(this.isRepeatAll);
        return this.isRepeatAll;
    }

    public boolean changeRepeatTrack() {
        this.isRepeatTrack = !(this.isRepeatTrack);
        return this.isRepeatTrack;
    }

    public AudioTrack getAudioTrack() {
        return at;
    }

    public AudioPlaylist getAudioPlaylist() {
        return ap;
    }

    public String getTrackInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("**TRACK INFO**\n");
        sb.append("\t**TITLE:** " + title + "\n");
        sb.append("\t**AUTHOR:** " + author + "\n");
        sb.append("\t**LENGTH:** " + length + "\n");
        sb.append("\t**URI:** <" + uri + ">\n");
        return sb.toString();
    }
}
