package net.ughklirn.listener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ughklirn.utils.DiscordCred;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RolesListener extends ListenerAdapter {
    private JDA jda;
    private List<String> lRoleGames;
    private List<String> lRoleClans;

    public RolesListener(JDA jda) {
        this.jda = jda;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean isRightChannel = (event.getChannel().getId().equals(DiscordCred.BOT_TCHANNEL_COMMANDS_ID)) || (event.getChannel().getId().equals(DiscordCred.BOT_TCHANNEL_COMMANDS_ADMIN_ID)) || (event.getChannel().getId().equals(DiscordCred.BOT_TCHANNEL_ROLES_ID));
        if (event.isFromType(ChannelType.TEXT)) {
            if (isRightChannel) {
                if (event.getMessage().getContentDisplay().startsWith(DiscordCred.BOT_CMD_PREFIX)) {
                    this.readLists();
                    String[] msg = event.getMessage().getContentDisplay().split(" ");
                    switch (msg[0]) {
                        case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_ROLE_JOIN:
                            this.join(event, msg[1]);
                            break;
                        case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_ROLE_LEAVE:
                            this.leave(event, msg[1]);
                            break;
                        case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_TEST:
                            this.test(event);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void join(MessageReceivedEvent event, String role_name) {
        if (this.lRoleGames.contains(role_name)) {
            Role role = event.getGuild().getRoleById(event.getGuild().getRolesByName(role_name, false).get(0).getId());
            event.getGuild().addRoleToMember(event.getMember(), role).queue();
            System.out.println("Added " + event.getAuthor() + " to " + role + ".");
            event.getChannel().sendMessage(event.getAuthor().getName() + " wurde zur Gruppe " + role.getName() + " hinzugefügt.").queue();
            this.jda.getTextChannelById(DiscordCred.BOT_TCHANNEL_BOT_LOG_ID).sendMessage(event.getAuthor().getName() + " wurde zur Gruppe " + role.getName() + " hinzugefügt.").queue();
        }
    }

    private void leave(MessageReceivedEvent event, String role_name) {
        if (this.lRoleGames.contains(role_name)) {
            Role role = event.getGuild().getRoleById(event.getGuild().getRolesByName(role_name, false).get(0).getId());
            event.getGuild().removeRoleFromMember(event.getMember(), role).queue();
            System.out.println("Remove " + event.getAuthor() + " from " + role + ".");
            event.getChannel().sendMessage(event.getAuthor().getName() + " wurde aus der Gruppe " + role.getName() + " entfernt.").queue();
        }
    }

    private void test(MessageReceivedEvent event) {

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

    private void readLists() {
        BufferedReader br = null;
        FileReader fr = null;
        this.lRoleGames = new ArrayList<>();
        this.lRoleClans = new ArrayList<>();

        try {
            fr = new FileReader(DiscordCred.BOT_PATH_GAMES);
            br = new BufferedReader(fr);
            String game;
            while ((game = br.readLine()) != null) {
                lRoleGames.add(game);
            }

            fr = new FileReader(DiscordCred.BOT_PATH_CLAN);
            br = new BufferedReader(fr);
            String clan;
            while ((clan = br.readLine()) != null) {
                lRoleClans.add(clan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
