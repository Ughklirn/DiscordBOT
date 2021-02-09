package net.ughklirn;

import discord4j.core.event.domain.message.MessageCreateEvent;

public interface BOT {
    void init();

    void bot();

    void playMusic(MessageCreateEvent event);
}
