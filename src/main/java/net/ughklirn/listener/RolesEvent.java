package net.ughklirn.listener;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.ughklirn.utils.Config;
import net.ughklirn.utils.types.TypeRoles;

import java.util.List;

public class RolesEvent {
    //    private JDA jda;
    private static List<String> lRoleGames;
    private static List<String> lRoleClans;

//    public RolesEvent(JDA jda) {
//        this.jda = jda;
//    }

//    public static void onMessageReceived(MessageReceivedEvent event) {
//        boolean isRightChannel = (event.getChannel().getId().equals(DiscordCred.BOT_TCHANNEL_COMMANDS_ID)) || (event.getChannel().getId().equals(DiscordCred.BOT_TCHANNEL_COMMANDS_ADMIN_ID)) || (event.getChannel().getId().equals(DiscordCred.BOT_TCHANNEL_ROLES_ID));
//        if (event.isFromType(ChannelType.TEXT)) {
//            if (isRightChannel) {
//                if (event.getMessage().getContentDisplay().startsWith(DiscordCred.BOT_CMD_PREFIX)) {
//                    this.readLists();
//                    String[] msg = event.getMessage().getContentDisplay().split(" ");
//                    switch (msg[0]) {
//                        case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_ROLE_JOIN:
//                            this.join(event, msg[1]);
//                            break;
//                        case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_ROLE_LEAVE:
//                            this.leave(event, msg[1]);
//                            break;
//                        case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_TEST:
//                            this.test(event);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            }
//        }
//    }

    public static void join(MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentDisplay().split(" ");
        readLists();
        String role_name = msg[1];
        if (lRoleGames.contains(role_name)) {
            Role role = event.getGuild().getRoleById(event.getGuild().getRolesByName(role_name, false).get(0).getId());
            event.getGuild().addRoleToMember(event.getMember(), role).queue();
            System.out.println("Added " + event.getAuthor() + " to " + role + ".");
            event.getChannel().sendMessage(event.getAuthor().getName() + " wurde zur Gruppe " + role.getName() + " hinzugefügt.").queue();
//            event.getGuild().getTextChannelById(DiscordCred.BOT_TCHANNEL_BOT_LOG_ID).sendMessage(event.getAuthor().getName() + " wurde zur Gruppe " + role.getName() + " hinzugefügt.").queue();
        } else if (lRoleClans.contains(role_name)) {
            leaveAllClans(event);
            Role role = event.getGuild().getRoleById(event.getGuild().getRolesByName(role_name, false).get(0).getId());
            event.getGuild().addRoleToMember(event.getMember(), role).queue();
            System.out.println("Added " + event.getAuthor() + " to " + role + ".");
            event.getChannel().sendMessage(event.getAuthor().getName() + " wurde zur Gruppe " + role.getName() + " hinzugefügt.").queue();
//            event.getGuild().getTextChannelById(DiscordCred.BOT_TCHANNEL_BOT_LOG_ID).sendMessage(event.getAuthor().getName() + " wurde zur Gruppe " + role.getName() + " hinzugefügt.").queue();
        } else {
            event.getChannel().sendMessage(role_name + " ist keine gültige Gruppe.").queue();
//            event.getGuild().getTextChannelById(DiscordCred.BOT_TCHANNEL_BOT_LOG_ID).sendMessage(event.getAuthor().getName() + ": " + role_name + " ist keine gültige Gruppe.").queue();
        }
    }

    public static void leave(MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentDisplay().split(" ");
        readLists();
        String role_name = msg[1];
        if ((lRoleGames.contains(role_name)) || lRoleClans.contains(role_name)) {
            Role role = event.getGuild().getRoleById(event.getGuild().getRolesByName(role_name, false).get(0).getId());
            event.getGuild().removeRoleFromMember(event.getMember(), role).queue();
            System.out.println("Remove " + event.getAuthor() + " from " + role + ".");
//            event.getChannel().sendMessage(event.getAuthor().getName() + " wurde aus der Gruppe " + role.getName() + " entfernt.").queue();
        }
    }

    public static void test(MessageReceivedEvent event) {

//        System.out.println(this.jda.getUserByTag("Dokriseum#9979"));
//        System.out.println(event.getAuthor().getAsTag());
//        for (User k : this.jda.getUsers()) {
//            System.out.println(k.getName());
//        }
//        System.out.println("##############");
//        for (Role k : this.jda.getRoles()) {
//            System.out.println("Role: ");
//            System.out.println("\t" + k);
//            System.out.println("\t" + k.getName());
//            System.out.println("\t" + k.getId());
//            System.out.println("\t" + k.getGuild());
//        }
//        System.out.println("##############");
        event.getChannel().sendMessage("Erfolgte :)").queue();
    }

    private static void readLists() {
        lRoleClans = Config.getInstance().getTextChannels_Commands_Roles().get(TypeRoles.CLANS);
        lRoleGames = Config.getInstance().getTextChannels_Commands_Roles().get(TypeRoles.GAMES);
//        BufferedReader br = null;
//        FileReader fr = null;
//        lRoleGames = new ArrayList<>();
//        lRoleClans = new ArrayList<>();
//
//        try {
//            fr = new FileReader(DiscordCred.BOT_PATH_GAMES);
//            br = new BufferedReader(fr);
//            String game;
//            while ((game = br.readLine()) != null) {
//                lRoleGames.add(game);
//            }
//
//            fr = new FileReader(DiscordCred.BOT_PATH_CLAN);
//            br = new BufferedReader(fr);
//            String clan;
//            while ((clan = br.readLine()) != null) {
//                lRoleClans.add(clan);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (br != null) {
//                    br.close();
//                }
//                if (fr != null) {
//                    fr.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private static void leaveAllClans(MessageReceivedEvent event) {
        for (String k : lRoleClans) {
            Role role = event.getGuild().getRoleById(event.getGuild().getRolesByName(k, false).get(0).getId());
            event.getGuild().removeRoleFromMember(event.getMember(), role).queue();
        }
    }
}
