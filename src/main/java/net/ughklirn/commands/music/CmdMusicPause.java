package net.ughklirn.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.ughklirn.audio.GuildMusicManager;
import net.ughklirn.audio.PlayerManager;
import net.ughklirn.commands.CommandContext;
import net.ughklirn.commands.ICommand;

public class CmdMusicPause implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("I need to be in a voice channel for this to work").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to be in a voice channel for this command to work").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You need to be in the same voice channel as me for this to work").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;

        if (audioPlayer.getPlayingTrack() == null) {
            channel.sendMessage("There is no track playing currently").queue();
            return;
        }

        if (musicManager.scheduler.player.isPaused()) {
            musicManager.scheduler.player.setPaused(false);
            channel.sendMessage("Play the track now").queue();
        } else {
            musicManager.scheduler.player.setPaused(true);
            channel.sendMessage("Pause the track now").queue();
        }

    }

    @Override
    public String getName() {
        return "pause";
    }

    @Override
    public String getHelp() {
        return "pause the current track";
    }
}
