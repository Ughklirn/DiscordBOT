package net.ughklirn.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.ughklirn.utils.Config;
import net.ughklirn.utils.DiscordCred;

import java.io.FileNotFoundException;

public class PersistenceEvent {
    public static void save(MessageReceivedEvent event) {
        if (Config.getInstance().getTextChannels_Commands_Admins().contains(event.getChannel().getId())) {
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_ACCEPT).queue();
            Config.getInstance().save();
        } else {
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_ERROR).queue();
            System.out.println(event.getChannel().getId());
            for (String k : Config.getInstance().getTextChannels_Commands_Admins()) {
                System.out.println("\t" + k);
            }
        }
//        event.getChannel().sendMessage(event.getGuild().toString()).queue();
    }

    public static void load(MessageReceivedEvent event) {
        if (Config.getInstance().getTextChannels_Commands_Admins().contains(event.getChannel().getId())) {
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_ACCEPT).queue();
            try {
                Config.getInstance().load();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_ERROR).queue();
            System.out.println(event.getChannel().getId());
            for (String k : Config.getInstance().getTextChannels_Commands_Admins()) {
                System.out.println("\t" + k);
            }
        }
//        event.getChannel().sendMessage(event.getGuild().toString()).queue();
    }
}