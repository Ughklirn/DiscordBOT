package net.ughklirn.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.ughklirn.audio.GuildMusicManager;
import net.ughklirn.audio.PlayerManager;
import net.ughklirn.bot.BotDiscord;
import net.ughklirn.commands.CommandContext;
import net.ughklirn.commands.ICommand;
import net.ughklirn.utils.types.TypeSettings;

import java.sql.SQLException;

public class CmdMusicVolume implements ICommand {
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

        try {
            BotDiscord.getInstance().getIO().getSettings().setRow(ctx.getGuild().getId(), TypeSettings.MUSIC_VOLUME, Integer.parseInt(ctx.getArgs().get(1)));
            musicManager.scheduler.player.setVolume(Integer.parseInt(BotDiscord.getInstance().getIO().getSettings().getRow(ctx.getGuild().getId(), TypeSettings.MUSIC_VOLUME)));
            channel.sendMessage("Change the volume to " + ctx.getArgs().get(1)).queue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NumberFormatException e) {
            channel.sendMessage(ctx.getArgs().get(1) + " is not a valid integer to change the volume").queue();
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "skip";
    }

    @Override
    public String getHelp() {
        return "skips the current track";
    }
}
