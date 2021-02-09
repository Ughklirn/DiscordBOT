package net.ughklirn.commands;

import de.coaster.commands.types.ServerCommand;
import de.coaster.manage.LiteSQL;
import net.dv8tion.jda.api.entities.*;

import java.util.List;

public class TimeRank implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        //!timerank @User

        //	System.out.println("Test1");

        //RollenID 582279555021012992


        List<Member> members = message.getMentionedMembers();

        if (members.size() >= 1) {
            for (Member memb : members) {
                Guild guild = channel.getGuild();
                Role role = guild.getRoleById(582279555021012992l);
                if (!memb.getRoles().contains(role)) {
                    guild.addRoleToMember(memb, role).queue();

                    LiteSQL.onUpdate("INSERT INTO timeranks(userid, guildid) VALUES(" + memb.getIdLong() + ", " + guild.getIdLong() + ")");

                    channel.sendMessage("Rolle hinzugefügt.").queue();
                }
            }
        }
    }

}
