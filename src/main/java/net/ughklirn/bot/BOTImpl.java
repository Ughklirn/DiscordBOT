package net.ughklirn.bot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.ughklirn.audio.PlayerManager;
import net.ughklirn.listener.LogMessageListener;
import net.ughklirn.listener.MusicListener;
import net.ughklirn.listener.RolesListener;
import net.ughklirn.utils.DiscordCred;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BOTImpl implements BOT {
    private JDA jda;
    private AudioPlayerManager apm;
    private PlayerManager pm;
    public static BOT INSTANCE;
    //private final GatewayDiscordClient client = DiscordClientBuilder.create(DiscordCred.BOT_TOKEN).build().login().block();
    //private static final Map<String, Commands> commands = new HashMap<>();

    public BOTImpl() throws LoginException {
        INSTANCE = this;
        this.jda = JDABuilder.createDefault(this.readToken()).build();
        this.apm = new DefaultAudioPlayerManager();
        this.pm = new PlayerManager();
        AudioSourceManagers.registerRemoteSources(this.apm);
        this.apm.getConfiguration().setFilterHotSwapEnabled(true);
    }

    @Override
    public void run() {
        jda.addEventListener(new LogMessageListener());
        jda.addEventListener(new RolesListener(this.jda));
        jda.addEventListener(new MusicListener());
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

    private String readToken() {
        BufferedReader br = null;
        FileReader fr = null;
        List<String> lToken = new ArrayList<>();

        try {
            fr = new FileReader(DiscordCred.BOT_PATH_KEY);
            br = new BufferedReader(fr);
            String game;
            while ((game = br.readLine()) != null) {
                lToken.add(game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lToken.get(0);
        }
    }
}
