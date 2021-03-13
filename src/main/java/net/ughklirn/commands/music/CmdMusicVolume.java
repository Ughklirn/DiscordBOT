package net.ughklirn.commands.music;

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
        TextChannel channel = ctx.getChannel();
        Member self = ctx.getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if (!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("I need to be in a voice channel for this to work").queue();
            return;
        }

        Member member = ctx.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("You need to be in a voice channel for this command to work").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You need to be in the same voice channel as me for this to work").queue();
            return;
        }

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());

        try {
            BotDiscord.getInstance().getIO().getSettings().setRow(ctx.getGuild().getId(), TypeSettings.MUSIC_VOLUME, Integer.parseInt(ctx.getArgs().get(1)));
            musicManager.scheduler.setVolume(ctx.getGuild().getId());
            channel.sendMessage("Change the volume to " + ctx.getArgs().get(1)).queue();
            musicManager.audioPlayer.setVolume(Integer.parseInt(BotDiscord.getInstance().getIO().getSettings().getRow(ctx.getGuild().getId(), TypeSettings.MUSIC_VOLUME)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NumberFormatException e) {
            channel.sendMessage(ctx.getArgs().get(1) + " is not a valid integer to change the volume").queue();
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "volume";
    }

    @Override
    public String getHelp() {
        return "volume the audio player";
    }
}
