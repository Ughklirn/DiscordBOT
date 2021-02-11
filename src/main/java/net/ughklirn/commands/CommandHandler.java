package net.ughklirn.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import net.ughklirn.DiscordCred;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {
    private final List<Commands> commands = new ArrayList<>();

    public void add(Commands cmd) {
        this.commands.add(cmd);
    }

    public void remove(Commands cmd) {
        this.commands.remove(cmd);
    }

    public void onCommand(MessageCreateEvent mce) {
        String[] args = mce.getMessage().getContent().split(" ");
        if (args[0].startsWith(DiscordCred.BOT_PREFIX)) {
            for (Commands k : this.commands) {
                if (k.getName().equals(args[0].replace(DiscordCred.BOT_PREFIX, ""))) {
                    k.onCommand(mce);
                }
            }
        }
    }
}
