package net.ughklirn.audio.typeloader;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    private AudioPlayer player;
    private BlockingQueue<AudioTrack> queue;
    private String uri;
    private MusicController controller;

    public TrackScheduler(String uri, MusicController controller) {
        this.uri = uri;
        this.controller = controller;
        this.player = this.controller.getPlayer();
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

//    @Override
//    public void onPlayerPause(AudioPlayer player) {
//        // Player was paused
//    }
//
//    @Override
//    public void onPlayerResume(AudioPlayer player) {
//        // Player was resumed
//    }
//
//    @Override
//    public void onTrackStart(AudioPlayer player, AudioTrack track) {
//        // A track started playing
//    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            // Start next track
            nextTrack();
        }

        // endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
        // endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
        // endReason == STOPPED: The player was stopped.
        // endReason == REPLACED: Another track started playing while this had not finished
        // endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
        //                       clone of this back to your queue
    }

//    @Override
//    public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
//        // An already playing track threw an exception (track end event will still be received separately)
//    }
//
//    @Override
//    public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
//        // Audio track has been unable to provide us any audio, might want to just start a new track
//    }

    public void nextTrack() {
        this.player.startTrack(this.queue.poll(), false);
    }
}
