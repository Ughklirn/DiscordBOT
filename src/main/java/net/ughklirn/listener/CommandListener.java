package net.ughklirn.listener;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ughklirn.bot.BOTImpl;
import net.ughklirn.utils.types.TypeCommands;
import net.ughklirn.utils.types.TypeReactions;
import net.ughklirn.utils.types.TypeSettings;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        try {
            String id = event.getGuild().getId();
            String prefix = BOTImpl.getInstance().getIO().getSettings().getRow(id, TypeSettings.PREFIX);
            String[] msg = event.getMessage().getContentDisplay().split(" ");
            if (event.isFromType(ChannelType.TEXT)) {
                if (event.getMessage().getContentDisplay().startsWith(prefix)) {
                    if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_PLAY).trim())) {
                        MusicEvent.play(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_STOP).trim())) {
                        MusicEvent.stop(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_VOLUME).trim())) {
                        MusicEvent.volume(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_REPEAT).trim())) {
                        MusicEvent.repeat(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_REPEATS).trim())) {
                        MusicEvent.repeatAll(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_REPEAT_ALL).trim())) {
                        MusicEvent.repeatAll(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_PAUSE).trim())) {
                        MusicEvent.pause(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.MUSIC_SKIP).trim())) {
                        MusicEvent.skip(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.ROLE_JOIN).trim())) {
                        RolesEvent.join(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.ROLE_LEAVE).trim())) {
                        RolesEvent.leave(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.TEST).trim())) {
                        RolesEvent.test(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.CONFIG_SAVE).trim())) {
                        PersistenceEvent.save(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.CONFIG_LOAD).trim())) {
                        PersistenceEvent.load(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.SHUTDOWN).trim())) {
                        AdminEvent.shutdown(event);
                    } else if (msg[0].equals(prefix + BOTImpl.getInstance().getIO().getCommands().getRow(id, TypeCommands.INIT).trim())) {
                        BOTImpl.getInstance().getIO().create(id);
                        event.getMessage().addReaction(BOTImpl.getInstance().getIO().getReactions().getRow(id, TypeReactions.OK));
                    } else if (msg[0].equals("%initialize")) {
                        BOTImpl.getInstance().getIO().create(id);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
