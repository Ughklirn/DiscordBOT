package net.ughklirn.listener;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ughklirn.bot.BotDiscord;
import net.ughklirn.scheduler.AdminEventScheduler;
import net.ughklirn.scheduler.MusicEventScheduler;
import net.ughklirn.scheduler.RolesEventScheduler;
import net.ughklirn.utils.types.TypeCommands;
import net.ughklirn.utils.types.TypeReactions;
import net.ughklirn.utils.types.TypeSettings;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        try {
            String[] msg = event.getMessage().getContentDisplay().split(" ");
            String id = event.getGuild().getId();
            try {
                String prefix = BotDiscord.getInstance().getIO().getSettings().getRow(id, TypeSettings.PREFIX);
                if (event.isFromType(ChannelType.TEXT)) {
                    if (event.getMessage().getContentDisplay().startsWith(prefix)) {
                        if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_PLAY).trim())) {
                            MusicEventScheduler.play(event);
                        } else if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_STOP).trim())) {
                            MusicEventScheduler.stop(event);
                        } else if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_VOLUME).trim())) {
                            MusicEventScheduler.volume(event);
                        } else if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_REPEAT).trim())) {
                            MusicEventScheduler.repeat(event);
                        } else if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_REPEATS).trim())) {
                            MusicEventScheduler.repeatAll(event);
                        } else if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_REPEAT_ALL).trim())) {
                            MusicEventScheduler.repeatAll(event);
                        } else if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_PAUSE).trim())) {
                            MusicEventScheduler.pause(event);
                        } else if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_SKIP).trim())) {
                            MusicEventScheduler.skip(event);
                        } else if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.ROLE_JOIN).trim())) {
                            RolesEventScheduler.join(event);
                        } else if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.ROLE_LEAVE).trim())) {
                            RolesEventScheduler.leave(event);
                        } else if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.TEST).trim())) {
                            AdminEventScheduler.test(event);
                        } else if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.SHUTDOWN).trim())) {
                            AdminEventScheduler.shutdown(event);
                        } else if (msg[0].equals(prefix + BotDiscord.getInstance().getIO().getCommands().getRow(id, TypeCommands.INIT).trim())) {
                            BotDiscord.getInstance().getIO().create(id);
                            event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(id, TypeReactions.OK));
                        } else {
                            if (msg[0].equals("%initialize")) {
                                BotDiscord.getInstance().getIO().create(id);
                                event.getMessage().addReaction("U+1F44C");
                            } else {
                                throw new IllegalArgumentException();
                            }
                        }
                    }
                }
            } catch (NullPointerException e) {
                if (msg[0].equals("%initialize")) {
                    BotDiscord.getInstance().getIO().create(id);
                    event.getMessage().addReaction("U+1F44C");
                }
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
