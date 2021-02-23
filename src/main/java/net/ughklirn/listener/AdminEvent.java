package net.ughklirn.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.ughklirn.utils.Config;
import net.ughklirn.utils.DiscordCred;

public class AdminEvent {
    public static void shutdown(MessageReceivedEvent event) {
        if (Config.getInstance().getTextChannels_Commands_Admins().contains(event.getChannel().getId())) {
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_ACCEPT).queue();
            event.getGuild().getJDA().shutdown();
        } else {
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_ERROR).queue();
        }
    }
}
