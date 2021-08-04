package net.ughklirn.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.ughklirn.connections.io.InputOutput;
import net.ughklirn.listener.CommandListener;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public class BotDiscord implements Bot {
    private String Bot;
    private static net.ughklirn.bot.Bot INSTANCE;
    //private static final Logger L = LoggerFactory.getLogger(BOTImpl.class);
    private JDA jda;
    private InputOutput io;
    private Guild guild;

    public BotDiscord(String bot) {
        try {
            /*
             * THIS
             */
            this.io = InputOutput.getInstance();
            this.jda = JDABuilder.createDefault(io.getInit().getToken(bot)).build();
            /*
             * MUSIC BOT
             */
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        INSTANCE = this;
    }

    public static Bot getInstance() {
        return INSTANCE;
    }

    @Override
    public void run() {
        this.jda.addEventListener(new CommandListener());
    }

    @Override
    public JDA getJDA() {
        return jda;
    }

    @Override
    public InputOutput getIO() {
        return io;
    }

    @Override
    public Guild getGuild() {
        return guild;
    }

    @Override
    public void setGuild(Guild guild) {
        this.guild = guild;
    }
}
