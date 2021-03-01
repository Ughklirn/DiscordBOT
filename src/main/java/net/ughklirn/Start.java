package net.ughklirn;

import net.ughklirn.bot.Bot;
import net.ughklirn.bot.BotDiscord;

public class Start {
    //private static final Logger L = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {
        try {
            Bot bot = new BotDiscord(args[0]);
            System.out.println(args[0]);
            Thread t1 = new Thread(bot);
            t1.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
