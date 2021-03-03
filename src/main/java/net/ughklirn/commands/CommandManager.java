package net.ughklirn.commands;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.ughklirn.bot.BotDiscord;
import net.ughklirn.commands.music.*;
import net.ughklirn.commands.roles.CmdRolesJoin;
import net.ughklirn.commands.roles.CmdRolesLeave;
import net.ughklirn.utils.types.TypeChannels;
import net.ughklirn.utils.types.TypeCommands;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    private List<ICommand> lMusicCommands = new ArrayList<>();
    private List<ICommand> lRolesCommands = new ArrayList<>();
    private List<String> lChannelsMusic;
    private List<String> lChannelsControl;
    private List<String> lChannelsLog;
    private List<String> lChannelsRoles;
    private List<String> lCommandsMusic;
    private List<String> lCommandsRoles;

    public CommandManager() {
        /*
         * add commands music
         */
        this.addCommandMusic(new CmdMusicJoin());
        this.addCommandMusic(new CmdMusicPlay());
        this.addCommandMusic(new CmdMusicInfo());
        this.addCommandMusic(new CmdMusicQueue());
        this.addCommandMusic(new CmdMusicRepeat());
        this.addCommandMusic(new CmdMusicSkip());
        this.addCommandMusic(new CmdMusicStop());

        /*
         * add commands roles
         */
        this.addCommandRoles(new CmdRolesJoin());
        this.addCommandRoles(new CmdRolesLeave());
    }

    private void addCommandMusic(ICommand cmd) {
        boolean nameFound = this.lMusicCommands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        lMusicCommands.add(cmd);
    }

    private void addCommandRoles(ICommand cmd) {
        boolean nameFound = this.lRolesCommands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        lRolesCommands.add(cmd);
    }

    public List<ICommand> getCommandsMusic() {
        return lMusicCommands;
    }


    public List<ICommand> getCommandsRoles() {
        return lRolesCommands;
    }

    @Nullable
    public ICommand getCommandMusic(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.lMusicCommands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    @Nullable
    public ICommand getCommandRoles(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.lRolesCommands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    public void handle(GuildMessageReceivedEvent event, String prefix) {
        this.fillLists(event);
        String[] msg = event.getMessage().getContentRaw().split(" ");

        String invoke = msg[0].replace(prefix, "");

        if (this.lChannelsMusic.contains(event.getChannel().getId())) {
            ICommand cmdMusic = this.getCommandMusic(invoke);
            if (this.lCommandsMusic.contains(invoke)) {
                event.getChannel().sendTyping().queue();
                List<String> args = Arrays.asList(msg);
                CommandContext cmdStorage = new CommandContext(event, args);
                cmdMusic.handle(cmdStorage);
            }
        } else if (this.lChannelsRoles.contains(event.getChannel().getId())) {
            ICommand cmdRoles = this.getCommandRoles(invoke);
            if (this.lCommandsRoles.contains(invoke)) {
                event.getChannel().sendTyping().queue();
                List<String> args = Arrays.asList(msg);
                CommandContext cmdStorage = new CommandContext(event, args);
                cmdRoles.handle(cmdStorage);
            }
        } else {

        }
    }

    private void fillLists(Event event) {
        this.lChannelsMusic = new ArrayList<>();
        this.lChannelsControl = new ArrayList<>();
        this.lChannelsLog = new ArrayList<>();
        this.lChannelsRoles = new ArrayList<>();
        this.lCommandsMusic = new ArrayList<>();
        this.lCommandsRoles = new ArrayList<>();
        try {
            /*
             * Channels, Music
             */
            this.lChannelsMusic.add(BotDiscord.getInstance().getIO().getChannels().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeChannels.MUSIC_ID));
            this.lChannelsMusic.add(BotDiscord.getInstance().getIO().getChannels().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeChannels.MUSIC_USER_ID));
            this.lChannelsMusic.add(BotDiscord.getInstance().getIO().getChannels().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeChannels.MUSIC_TEAM_ID));
            /*
             * Channels, Roles
             */
            this.lChannelsRoles.add(BotDiscord.getInstance().getIO().getChannels().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeChannels.ROLES_ID));
            this.lChannelsRoles.add(BotDiscord.getInstance().getIO().getChannels().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeChannels.COMMANDS_ID));
            this.lChannelsRoles.add(BotDiscord.getInstance().getIO().getChannels().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeChannels.COMMANDS_ADMIN_ID));
            /*
             * Commands, Music
             */
            this.lCommandsMusic.add(BotDiscord.getInstance().getIO().getCommands().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeCommands.MUSIC_VOLUME));
            this.lCommandsMusic.add(BotDiscord.getInstance().getIO().getCommands().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeCommands.MUSIC_PLAY));
            this.lCommandsMusic.add(BotDiscord.getInstance().getIO().getCommands().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeCommands.MUSIC_PAUSE));
            this.lCommandsMusic.add(BotDiscord.getInstance().getIO().getCommands().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeCommands.MUSIC_STOP));
            this.lCommandsMusic.add(BotDiscord.getInstance().getIO().getCommands().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeCommands.MUSIC_SKIP));
            this.lCommandsMusic.add(BotDiscord.getInstance().getIO().getCommands().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeCommands.MUSIC_JOIN));
            this.lCommandsMusic.add(BotDiscord.getInstance().getIO().getCommands().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeCommands.MUSIC_INFO));
            this.lCommandsMusic.add(BotDiscord.getInstance().getIO().getCommands().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeCommands.MUSIC_QUEUE));
            this.lCommandsMusic.add(BotDiscord.getInstance().getIO().getCommands().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeCommands.MUSIC_REPEAT));
            /*
             * Commands, Roles
             */
            this.lCommandsRoles.add(BotDiscord.getInstance().getIO().getCommands().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeCommands.ROLE_JOIN));
            this.lCommandsRoles.add(BotDiscord.getInstance().getIO().getCommands().getRow(((GenericGuildEvent) event).getGuild().getId(), TypeCommands.ROLE_LEAVE));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
