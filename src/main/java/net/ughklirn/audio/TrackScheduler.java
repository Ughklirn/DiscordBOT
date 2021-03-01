package net.ughklirn.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.ughklirn.bot.BotDiscord;
import net.ughklirn.utils.types.TypeSettings;

import java.awt.*;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;
    private boolean repeat = false;
    private EmbedBuilder builder;
    private Guild guild;

    /**
     * @param player The audio player this scheduler uses
     */
    public TrackScheduler(AudioPlayer player, Guild guild) {
        this.player = player;
        this.guild = guild;
        this.queue = new LinkedBlockingQueue<>();
        this.builder = new EmbedBuilder();
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
//        super.onTrackStart(player, track);
        StringBuffer sb = new StringBuffer();
        builder.setColor(Color.YELLOW);
        AudioTrackInfo info = track.getInfo();
        builder.setDescription("Actually plays " + info.title);
        builder.addField("Channel: " + info.author, "[" + info.title + "](<" + info.uri + ">)", false);
        builder.addField("Length: ", info.isStream ? ":red_circle: STREAMING" : this.getDurationAsString(info.length), true);
//        if (info.uri.startsWith("https://www.youtube.com/watch?v=")) {
//            String videoID = info.uri.replace("https://www.youtube.com/watch?v=", "");
//            InputStream file;
//            try {
//                file = new URL("https://img.youtube.com/vi/" + videoID + "/hqdefault.jpg").openStream();
//                eb.setImage("attachment://thumbnail.png");
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }


    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(AudioTrack track) {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        if (!player.startTrack(track, true)) {
            this.queue.offer(track);
        } else {
            this.queue.add(track);
        }
        try {
            this.player.setVolume(Integer.parseInt(BotDiscord.getInstance().getIO().getSettings().getRow(this.guild.getId(), TypeSettings.MUSIC_VOLUME)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack() {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        try {
            player.startTrack(queue.poll(), false);
        } catch (IllegalStateException e1) {
            try {
                player.startTrack(queue.poll().makeClone(), false);
            } catch (NullPointerException e2) {
                e2.getMessage();
            }
        }
        try {
            this.player.setVolume(Integer.parseInt(BotDiscord.getInstance().getIO().getSettings().getRow(this.guild.getId(), TypeSettings.MUSIC_VOLUME)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (!repeat) {
            if (endReason.mayStartNext) {
                nextTrack();
            }
        } else {
            player.playTrack(track.makeClone());
        }
    }

    public void repeat() {
        this.repeat = !this.repeat;
    }

    public void volume() {
        try {
            this.player.setVolume(Integer.parseInt(BotDiscord.getInstance().getIO().getSettings().getRow(this.guild.getId(), TypeSettings.MUSIC_VOLUME)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String getDurationAsString(long length) {
        StringBuffer sb = new StringBuffer();
        long s = length / 1000;
        long min = s / 60;
        long h = min / 60;
        min %= 60;
        h %= 60;
        sb.append(h);
        sb.append(":");
        if (min > 10) {
            sb.append("0" + min);
        } else {
            sb.append(min);
        }
        sb.append(":");
        if (s > 10) {
            sb.append("0" + s);
        } else {
            sb.append(s);
        }
        return sb.toString();
    }


}
