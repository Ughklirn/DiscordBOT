package net.ughklirn.bot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.ughklirn.connections.io.InputOutput;
import net.ughklirn.listener.CommandListener;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public class BotDiscord implements net.ughklirn.bot.Bot {
    private String Bot;
    private static net.ughklirn.bot.Bot INSTANCE;
    //private static final Logger L = LoggerFactory.getLogger(BOTImpl.class);
    private JDA jda;
    private AudioPlayerManager apm;
    private InputOutput io;

    public BotDiscord(String bot) {
        try {
            this.io = InputOutput.getInstance();
            this.jda = JDABuilder.createDefault(io.getInit().getToken(bot)).build();
            this.apm = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerRemoteSources(this.apm);
            this.apm.getConfiguration().setFilterHotSwapEnabled(true);
            INSTANCE = this;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.jda.addEventListener(new CommandListener());
    }

    public AudioPlayerManager getAudioPlayerManager() {
        return apm;
    }

    public JDA getJDA() {
        return jda;
    }

    public static net.ughklirn.bot.Bot getInstance() {
        return INSTANCE;
    }

    public InputOutput getIO() {
        return io;
    }
}
