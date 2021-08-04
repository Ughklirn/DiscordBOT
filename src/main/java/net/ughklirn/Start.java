package net.ughklirn;

import net.ughklirn.bot.Bot;
import net.ughklirn.bot.BotDiscord;

public class Start {
    //private static final Logger L = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {
        try {
            Bot bot = new BotDiscord(args[0]);
            bot.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
