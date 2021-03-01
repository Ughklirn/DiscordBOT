package net.ughklirn.scheduler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.ughklirn.bot.BotDiscord;
import net.ughklirn.utils.types.TypeChannels;
import net.ughklirn.utils.types.TypeReactions;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AdminEventScheduler {
    private static List<String> allow_channels;

    public static void shutdown(MessageReceivedEvent event) {
        try {
            if (allowChannel(event)) {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.ACCEPT)).queue();

                event.getGuild().getJDA().shutdown();
            } else {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.ERROR)).queue();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void test(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Erfolgte :)").queue();
    }

    private static boolean allowChannel(MessageReceivedEvent event) {
        boolean allow = false;
        allow_channels = new LinkedList<>();
        String id = event.getGuild().getId();
        try {
            allow_channels.add(BotDiscord.getInstance().getIO().getChannels().getRow(id, TypeChannels.COMMANDS_ADMIN_ID));
            allow_channels.add(BotDiscord.getInstance().getIO().getChannels().getRow(id, TypeChannels.COMMANDS_ID));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("### MusicEvent Begin ###");
        System.out.println("Allow ChannelID: " + event.getChannel().getId() + "\n\t\t" + event.getChannel().getName());
        for (String k : allow_channels) {
            System.out.println("\t ChannelID: " + k);
            if (k.equals(event.getChannel().getId())) {
                allow = true;
            }
        }
        System.out.println("### MusicEvent End ###");
        return allow;
    }
}
