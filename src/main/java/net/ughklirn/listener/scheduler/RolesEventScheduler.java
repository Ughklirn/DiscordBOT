package net.ughklirn.listener.scheduler;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.ughklirn.bot.BotDiscord;
import net.ughklirn.utils.types.TypeChannels;
import net.ughklirn.utils.types.TypeRoles;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RolesEventScheduler {
    private static List<String> allow_channels;
    private static List<String> lRoleGames;
    private static List<String> lRoleClans;

    public static void join(MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentDisplay().split(" ");
        readLists(event.getGuild().getId());
        String role_name = msg[1];
        if (lRoleGames.contains(role_name)) {
            Role role = event.getGuild().getRoleById(event.getGuild().getRolesByName(role_name, false).get(0).getId());
            event.getGuild().addRoleToMember(event.getMember(), role).queue();
            System.out.println("Added " + event.getAuthor() + " to " + role + ".");
            event.getChannel().sendMessage(event.getAuthor().getName() + " joins to " + role.getName() + ".").queue();
//            event.getGuild().getTextChannelById(DiscordCred.BOT_TCHANNEL_BOT_LOG_ID).sendMessage(event.getAuthor().getName() + " wurde zur Gruppe " + role.getName() + " hinzugefügt.").queue();
        } else if (lRoleClans.contains(role_name)) {
            leaveAllClans(event);
            Role role = event.getGuild().getRoleById(event.getGuild().getRolesByName(role_name, false).get(0).getId());
            event.getGuild().addRoleToMember(event.getMember(), role).queue();
            System.out.println("Added " + event.getAuthor() + " to " + role + ".");
            event.getChannel().sendMessage(event.getAuthor().getName() + " joins to " + role.getName() + ".").queue();
//            event.getGuild().getTextChannelById(DiscordCred.BOT_TCHANNEL_BOT_LOG_ID).sendMessage(event.getAuthor().getName() + " wurde zur Gruppe " + role.getName() + " hinzugefügt.").queue();
        } else {
            event.getChannel().sendMessage(role_name + " is not a valid group.").queue();
//            event.getGuild().getTextChannelById(DiscordCred.BOT_TCHANNEL_BOT_LOG_ID).sendMessage(event.getAuthor().getName() + ": " + role_name + " ist keine gültige Gruppe.").queue();
        }
    }

    public static void leave(MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentDisplay().split(" ");
        readLists(event.getGuild().getId());
        String role_name = msg[1];
        if ((lRoleGames.contains(role_name)) || lRoleClans.contains(role_name)) {
            Role role = event.getGuild().getRoleById(event.getGuild().getRolesByName(role_name, false).get(0).getId());
            event.getGuild().removeRoleFromMember(event.getMember(), role).queue();
            System.out.println("Remove " + event.getAuthor() + " from " + role + ".");
            event.getChannel().sendMessage(event.getAuthor().getName() + " leaves " + role.getName() + ".").queue();
        }
    }

    private static void readLists(String id) {
        try {
            lRoleClans = BotDiscord.getInstance().getIO().getRoles().getRow(id, TypeRoles.CLANS);// Config.getInstance().getTextChannels_Commands_Roles().get(TypeRoles.CLANS);
            lRoleGames = BotDiscord.getInstance().getIO().getRoles().getRow(id, TypeRoles.GAMES);//Config.getInstance().getTextChannels_Commands_Roles().get(TypeRoles.GAMES);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void leaveAllClans(MessageReceivedEvent event) {
        for (String k : lRoleClans) {
            Role role = event.getGuild().getRoleById(event.getGuild().getRolesByName(k, false).get(0).getId());
            event.getGuild().removeRoleFromMember(event.getMember(), role).queue();
        }
    }

    private static boolean allowChannel(MessageReceivedEvent event) {
        boolean allow = false;
        allow_channels = new LinkedList<>();
        String id = event.getGuild().getId();
        try {
            allow_channels.add(BotDiscord.getInstance().getIO().getChannels().getRow(id, TypeChannels.COMMANDS_ADMIN_ID));
            allow_channels.add(BotDiscord.getInstance().getIO().getChannels().getRow(id, TypeChannels.COMMANDS_ID));
            allow_channels.add(BotDiscord.getInstance().getIO().getChannels().getRow(id, TypeChannels.ROLES_ID));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("### RolesEvent Begin ###");
        System.out.println("Allow ChannelID: " + event.getChannel().getId() + "\n\t\t" + event.getChannel().getName());
        for (String k : allow_channels) {
            System.out.println("\t ChannelID: " + k);
            if (k.equals(event.getChannel().getId())) {
                allow = true;
            }
        }
        System.out.println("### RolesEvent End ###");
        return allow;
    }
}
