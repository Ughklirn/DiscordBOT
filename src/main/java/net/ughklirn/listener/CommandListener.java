package net.ughklirn.listener;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ughklirn.utils.DiscordCred;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentDisplay().split(" ");
        if (event.isFromType(ChannelType.TEXT)) {
            if (event.getMessage().getContentDisplay().startsWith(DiscordCred.BOT_CMD_PREFIX)) {
                switch (msg[0]) {
                    case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_MUSIC_PLAY:
                        MusicEvent.play(event);
                        break;
                    case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_MUSIC_STOP:
                        MusicEvent.stop(event);
                        break;
                    case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_MUSIC_VOLUME:
                        MusicEvent.volume(event);
                        break;
                    case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_ROLE_JOIN:
                        RolesEvent.join(event);
                        break;
                    case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_ROLE_LEAVE:
                        RolesEvent.leave(event);
                        break;
                    case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_TEST:
                        RolesEvent.test(event);
                        break;
                    case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_CONFIG_SAVE:
                        PersistenceEvent.save(event);
                        break;
                    case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_CONFIG_LOAD:
                        PersistenceEvent.load(event);
                        break;
                    case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_SHUTDOWN:
                        AdminEvent.shutdown(event);
                        break;
                    default:
                        break;
                }

            }
        }
    }
}
