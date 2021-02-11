package net.ughklirn.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;

public interface Commands {
    String getName();

    void onCommand(MessageCreateEvent mce);
}
