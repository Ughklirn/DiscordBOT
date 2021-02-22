package net.ughklirn;

import net.dv8tion.jda.api.sharding.ShardManager;
import net.ughklirn.bot.BOT;
import net.ughklirn.bot.BOTImpl;

import javax.security.auth.login.LoginException;

public class Start {
    private ShardManager sm;

    public static void main(String[] args) throws LoginException {
        BOT bot = new BOTImpl();
        Thread t1 = new Thread(bot);
        t1.run();
    }
}
