package net.ughklirn.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import net.ughklirn.audio.typeloader.BotAudioLoadResultHandler;
import net.ughklirn.bot.BotDiscord;
import net.ughklirn.utils.types.TypeReactions;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LavaPlayer {
    private AudioPlayerManager playerManager;
    private Map<Long, GuildMusicManager> musicManagers;
    private MessageReceivedEvent event;
    private VoiceChannel vc;

    public LavaPlayer() {
        this.event = null;
        this.musicManagers = new HashMap<>();
        this.playerManager = BotDiscord.getInstance().getAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }

    public void setEvent(MessageReceivedEvent event) {
        this.event = event;
    }

    public VoiceChannel getVoiceChannel() {
        return vc;
    }

    public void setVoiceChannel(VoiceChannel vc) {
        this.vc = vc;
    }

    public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(final TextChannel channel, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new BotAudioLoadResultHandler(musicManager, trackUrl, channel, this));
    }

    public void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        connectToFirstVoiceChannel(guild.getAudioManager(), this.event.getMember().getVoiceState().getChannel());

        musicManager.scheduler.queue(track);
    }

    public void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();

        channel.sendMessage("Skipped to next track.").queue();
    }

    public void stop(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.stop();

        channel.sendMessage("Stopped the track.").queue();
    }

    public void pause(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.pause();

        try {
            channel.sendMessage(BotDiscord.getInstance().getIO().getReactions().getRow(channel.getGuild().getId(), TypeReactions.MUSIC_PAUSE)).queue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void repeat(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.changeRepeating();
    }

    public void volume(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.changeVolume(channel.getGuild().getId());
    }

    public static void connectToFirstVoiceChannel(AudioManager audioManager, VoiceChannel vc) {
        if (!audioManager.isConnected()) {
            audioManager.openAudioConnection(vc);
//            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
//                audioManager.openAudioConnection(voiceChannel);
//                break;
//            }
        }
    }
}
