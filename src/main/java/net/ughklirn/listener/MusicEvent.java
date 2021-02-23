package net.ughklirn.listener;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import net.ughklirn.audio.DiscordAudioLoadResultHandler;
import net.ughklirn.audio.MusicController;
import net.ughklirn.bot.BOTImpl;
import net.ughklirn.utils.Config;
import net.ughklirn.utils.DiscordCred;

import java.util.List;

public class MusicEvent {
    private static List<String> lTextChannel;

    public static void play(MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentDisplay().split(" ");
//
//        try {
//            Config.getInstance().getTextChannel_Music_Volume().get(event.getMember().getGuild());
//        } catch (Exception e) {
//            Config.getInstance().getTextChannel_Music_Volume().put(event.getMember().getGuild(), Config.getInstance().getBotMusic_Volume());
//        }

        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
            GuildVoiceState state;
            if ((state = event.getMember().getVoiceState()) != null) {
                VoiceChannel vc;
                if ((vc = state.getChannel()) != null) {
                    MusicController mc = BOTImpl.INSTANCE.getPlayerManager().getController(vc.getGuild().getIdLong());
//                    AudioPlayer player = mc.getPlayer();
                    AudioPlayerManager apm = BOTImpl.INSTANCE.getAudioPlayerManager();
                    AudioManager manager = vc.getGuild().getAudioManager();
                    manager.openAudioConnection(vc);

                    StringBuilder sb = new StringBuilder();

                    for (int i = 1; i < msg.length; i++) {
                        sb.append(msg[i] + " ");
                    }

                    String url = sb.toString().trim();
                    if (!url.startsWith("http")) {
                        url = "ytsearch: " + url;
                    }

                    final String uri = url;
                    apm.loadItem(uri, new DiscordAudioLoadResultHandler(uri, mc));
                    event.getMessage().addReaction(DiscordCred.BOT_REACTION_OK).queue();
                }
            }
        }
    }

    public static void stop(MessageReceivedEvent event) {
        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
            GuildVoiceState state;
            if ((state = event.getMember().getVoiceState()) != null) {
                VoiceChannel vc;
                if ((vc = state.getChannel()) != null) {
                    MusicController mc = BOTImpl.INSTANCE.getPlayerManager().getController(vc.getGuild().getIdLong());
                    mc.reload();
                    AudioPlayer player = mc.getPlayer();
//                    AudioPlayerManager apm = BOTImpl.INSTANCE.getAudioPlayerManager();
                    AudioManager manager = vc.getGuild().getAudioManager();
                    player.stopTrack();
                    manager.closeAudioConnection();
                    event.getMessage().addReaction(DiscordCred.BOT_REACTION_OK).queue();
                }
            }
        }
    }

    public static void volume(MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentDisplay().split(" ");
        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
            Config.getInstance().setVolume(event.getMember().getGuild(), Integer.parseInt(msg[1]));
//            System.out.println("Volume: " + msg[1]);
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_OK).queue();
            BOTImpl.INSTANCE.getPlayerManager().getController(event.getMember().getGuild().getIdLong()).reload();
        }
    }


//    private static boolean checkChannelID(MessageReceivedEvent event) {
//        BufferedReader br = null;
//        FileReader fr = null;
//        lTextChannel = new ArrayList<>();
//
//        try {
//            fr = new FileReader(DiscordCred.BOT_PATH_CHANNELS_TEXT_MUSIC);
//            br = new BufferedReader(fr);
//            String game;
//            while ((game = br.readLine()) != null) {
//                lTextChannel.add(game);
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
//            for (String k : lTextChannel) {
//                if (event.getChannel().getId().equals(k)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
}
