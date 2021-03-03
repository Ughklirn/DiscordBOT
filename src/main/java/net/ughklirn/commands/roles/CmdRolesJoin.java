package net.ughklirn.commands.roles;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.ughklirn.bot.BotDiscord;
import net.ughklirn.commands.CommandContext;
import net.ughklirn.commands.ICommand;
import net.ughklirn.utils.types.TypeRoles;

import java.sql.SQLException;
import java.util.List;

public class CmdRolesJoin implements ICommand {
    private List<String> lRoleGames;
    private List<String> lRoleClans;

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        this.readLists(ctx.getGuild().getId());
        String role_name = ctx.getArgs().get(1);
        if (lRoleGames.contains(role_name)) {
            Role role = ctx.getGuild().getRoleById(ctx.getGuild().getRolesByName(role_name, false).get(0).getId());
            ctx.getGuild().addRoleToMember(ctx.getMember(), role).queue();
            channel.sendMessage(ctx.getMessage().getAuthor().getAsTag() + " joins " + ctx.getArgs().get(1)).queue();
        } else if (lRoleClans.contains(role_name)) {
            leaveAllClans(ctx);
            Role role = ctx.getGuild().getRoleById(ctx.getGuild().getRolesByName(role_name, false).get(0).getId());
            channel.sendMessage(ctx.getMessage().getAuthor().getAsTag() + " joins " + ctx.getArgs().get(1)).queue();
            ctx.getGuild().addRoleToMember(ctx.getMember(), role).queue();
        } else {
            ctx.getChannel().sendMessage(role_name + " is not a valid group.").queue();
        }
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Makes the user join to a role";
    }

    private void readLists(String id) {
        try {
            lRoleClans = BotDiscord.getInstance().getIO().getRoles().getRow(id, TypeRoles.CLANS);
            lRoleGames = BotDiscord.getInstance().getIO().getRoles().getRow(id, TypeRoles.GAMES);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void leaveAllClans(CommandContext ctx) {
        for (String k : lRoleClans) {
            Role role = ctx.getGuild().getRoleById(ctx.getGuild().getRolesByName(k, false).get(0).getId());
            ctx.getGuild().removeRoleFromMember(ctx.getMember(), role).queue();
        }
    }
}
