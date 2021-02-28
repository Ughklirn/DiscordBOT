package net.ughklirn.bot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.ughklirn.audio.typeloader.PlayerManager;
import net.ughklirn.database.io.InputOutput;
import net.ughklirn.listener.CommandListener;
import net.ughklirn.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
import java.util.List;

public class BOTImpl implements BOT {
    private static BOT INSTANCE;
    private static final Logger L = LoggerFactory.getLogger(BOTImpl.class);
    private JDA jda;
    private AudioPlayerManager apm;
    private PlayerManager pm;
    private InputOutput io;
    //private final GatewayDiscordClient client = DiscordClientBuilder.create(DiscordCred.BOT_TOKEN).build().login().block();
    //private static final Map<String, Commands> commands = new HashMap<>();

    public BOTImpl() {
        try {
            this.io = InputOutput.getInstance();
            this.jda = JDABuilder.createDefault(Config.getInstance().getBotToken()).build();
            this.jda = JDABuilder.createDefault(io.getInit().getToken("UghklirnDev")).build();
            this.apm = new DefaultAudioPlayerManager();
            this.pm = new PlayerManager();
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

    public PlayerManager getPlayerManager() {
        return this.pm;
    }

    public List<Guild> getGuilds() {
        return this.jda.getGuilds();
    }

    public static BOT getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BOTImpl();
        }
        return INSTANCE;
    }

    public InputOutput getIO() {
        return io;
    }
}
