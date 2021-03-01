package net.ughklirn.bot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.ughklirn.audio.PlayerManager;
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
    private Guild guild;
    private PlayerManager pm;

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
            this.apm = new DefaultAudioPlayerManager();
            this.pm = new PlayerManager();
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
        AudioSourceManagers.registerRemoteSources(this.apm);
        this.apm.getConfiguration().setFilterHotSwapEnabled(true);
    }

    @Override
    public AudioPlayerManager getAudioPlayerManager() {
        return apm;
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

    @Override
    public PlayerManager getPlayerManager() {
        return this.pm;
    }
}
