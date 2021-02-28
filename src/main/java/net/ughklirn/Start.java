package net.ughklirn;

import net.ughklirn.bot.BOT;
import net.ughklirn.bot.BOTImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Start {
    private static final Logger L = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) throws LoginException {
        try {
            BOT bot = new BOTImpl();
            Thread t1 = new Thread(bot);
            t1.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
