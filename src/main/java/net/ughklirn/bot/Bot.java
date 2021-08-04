package net.ughklirn.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.ughklirn.connections.io.InputOutput;

public interface Bot extends Runnable {

    JDA getJDA();

    InputOutput getIO();

    Guild getGuild();

    void setGuild(Guild guild);
}
