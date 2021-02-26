package net.ughklirn.bot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.ughklirn.audio.PlayerManager;
import net.ughklirn.listener.CommandListener;
import net.ughklirn.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.List;

public class BOTImpl implements BOT {
    public static BOT INSTANCE;
    private static final Logger L = LoggerFactory.getLogger(BOTImpl.class);
    private JDA jda;
    private AudioPlayerManager apm;
    private PlayerManager pm;
    private Config config;
    //private final GatewayDiscordClient client = DiscordClientBuilder.create(DiscordCred.BOT_TOKEN).build().login().block();
    //private static final Map<String, Commands> commands = new HashMap<>();

    public BOTImpl() throws LoginException {
        this.config = Config.getInstance();
        this.jda = JDABuilder.createDefault(Config.getInstance().getBotToken()).build();
        INSTANCE = this;
        this.apm = new DefaultAudioPlayerManager();
        this.pm = new PlayerManager();
        AudioSourceManagers.registerRemoteSources(this.apm);
        this.apm.getConfiguration().setFilterHotSwapEnabled(true);
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

    public Config getConfig() {
        return config;
    }

    public List<Guild> getGuilds() {
        return this.jda.getGuilds();
    }
//
//    private String readToken() {
//        BufferedReader br = null;
//        FileReader fr = null;
//        List<String> lToken = new ArrayList<>();
//
//        try {
//            fr = new FileReader(DiscordCred.BOT_PATH_KEY);
//            br = new BufferedReader(fr);
//            String game;
//            while ((game = br.readLine()) != null) {
//                lToken.add(game);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (br != null) {
//                    br.close();
//                }
//                if (fr != null) {
//                    fr.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return lToken.get(0);
//        }
//    }
}
