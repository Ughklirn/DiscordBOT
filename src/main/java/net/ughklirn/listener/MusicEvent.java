package net.ughklirn.listener;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import net.ughklirn.audio.AudioTrackClone;
import net.ughklirn.audio.DiscordAudioLoadResultHandler;
import net.ughklirn.audio.MusicController;
import net.ughklirn.bot.BOTImpl;
import net.ughklirn.utils.Config;
import net.ughklirn.utils.DiscordCred;

import java.util.List;

public class MusicEvent {
    private static List<String> lTextChannel;
    private static DiscordAudioLoadResultHandler alrh;

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
                    AudioPlayer player = mc.getPlayer();
                    AudioPlayerManager apm = BOTImpl.INSTANCE.getAudioPlayerManager();
                    AudioManager manager = vc.getGuild().getAudioManager();
                    StringBuffer sb_msg = new StringBuffer();
                    if (event.getMessage().getContentDisplay().equals(DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_MUSIC_PLAY)) {
                        player.setPaused(false);
                    } else {
                        manager.openAudioConnection(vc);

                        StringBuffer sb = new StringBuffer();

                        for (int i = 1; i < msg.length; i++) {
                            sb.append(msg[i] + " ");
                        }

                        String url = sb.toString().trim();
                        url = url.replace("<", "");
                        url = url.replace(">", "");
                        if (!url.startsWith("http")) {
                            url = "ytsearch: " + url;
                        }

                        final String uri = url;
                        //sb_msg.append("**URL:** <" + uri + ">");
                        alrh = new DiscordAudioLoadResultHandler(uri, mc);
                        apm.loadItem(uri, alrh);
                    }
                    event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_PLAY).queue();
                    sb_msg.append(AudioTrackClone.getTrackInfo());
                    event.getChannel().sendMessage(sb_msg.toString()).queue();
//                    event.getChannel().sendMessage("Ok").queue();
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
                    event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_STOP).queue();
                }
            }
        }
    }

    public static void volume(MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentDisplay().split(" ");
        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
            Config.getInstance().setVolume(event.getMember().getGuild(), Integer.parseInt(msg[1]));
//            System.out.println("Volume: " + msg[1]);
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_VOLUME).queue();
            BOTImpl.INSTANCE.getPlayerManager().getController(event.getMember().getGuild().getIdLong()).reload();
        }
    }

    public static void repeat(MessageReceivedEvent event) {
        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
            boolean repeat = alrh.changeRepeatAll();
//            System.out.println("Volume: " + msg[1]);
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_OK).queue();
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_REPEAT_ONE).queue();
            checkRepeat(event);
        }
    }

    public static void repeatAll(MessageReceivedEvent event) {
        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
            boolean repeat = alrh.changeRepeatAll();
//            System.out.println("Volume: " + msg[1]);
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_OK).queue();
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_REPEAT_ALL).queue();
            checkRepeat(event);
        }
    }

    public static void pause(MessageReceivedEvent event) {
        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
            BOTImpl.INSTANCE.getPlayerManager().getController(event.getGuild().getIdLong()).getPlayer().setPaused(true);
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_PAUSE).queue();
        }
    }

    public static void info(MessageReceivedEvent event) {
        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_PAUSE).queue();
        }
    }

    private static void checkRepeat(MessageReceivedEvent event) {
        StringBuffer sb = new StringBuffer();
        if (alrh.isRepeatAll()) {
            sb.append("**repeat all**: on");
        } else {
            sb.append("**repeat all**: off");
        }
        sb.append("\n");
        if (alrh.isRepeatTrack()) {
            sb.append("**repeat one**: on");
        } else {

            sb.append("**repeat one**: off");
        }
        event.getChannel().sendMessage(sb.toString()).queue();
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
