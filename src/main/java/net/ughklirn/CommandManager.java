package net.ughklirn;

import de.coaster.commands.*;
import de.coaster.commands.types.ServerCommand;
import de.coaster.music.commands.PlayCommand;
import de.coaster.music.commands.ShuffelCommand;
import de.coaster.music.commands.StopCommand;
import de.coaster.music.commands.TrackInfoCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    public ConcurrentHashMap<String, ServerCommand> commands;

    public CommandManager() {
        this.commands = new ConcurrentHashMap<>();

        this.commands.put("clear", new ClearCommand());
        this.commands.put("help", new HelpCommand());
        this.commands.put("hug", new HugCommand());
        this.commands.put("info", new ClientInfo());
        this.commands.put("sql", new SQLCommand());
        this.commands.put("preview", new PreviewCommand());
        this.commands.put("react", new ReactCommand());
        this.commands.put("reactrole", new ReactRolesCommand());
        this.commands.put("timerank", new TimeRank());
        this.commands.put("statchannel", new StatschannelCommand());
        this.commands.put("admininvite", new AdminInvite());
        this.commands.put("createrole", new RoleCreation());

        this.commands.put("play", new PlayCommand());
        this.commands.put("stop", new StopCommand());
        this.commands.put("ti", new TrackInfoCommand());
        this.commands.put("shuffel", new ShuffelCommand());
    }

    public boolean perform(String command, Member m, TextChannel channel, Message message) {

        ServerCommand cmd;
        if ((cmd = this.commands.get(command.toLowerCase())) != null) {
            cmd.performCommand(m, channel, message);
            return true;
        }

        return false;
    }
}
