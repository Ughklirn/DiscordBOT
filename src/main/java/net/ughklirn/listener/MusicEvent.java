package net.ughklirn.listener;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.ughklirn.audio.LavaPlayer;
import net.ughklirn.audio.typeloader.DiscordAudioLoadResultHandler;
import net.ughklirn.utils.Config;
import net.ughklirn.utils.DiscordCred;

import java.util.List;

public class MusicEvent {
    private static List<String> lTextChannel;
    private static DiscordAudioLoadResultHandler alrh;
    private static LavaPlayer lp = new LavaPlayer();

    public static void play(MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentDisplay().split(" ");
        if (event.isFromType(ChannelType.TEXT)) {
            lp.setEvent(event);
            lp.setVoiceChannel(event.getMember().getVoiceState().getChannel());
            lp.loadAndPlay((TextChannel) event.getChannel(), msg[1]);
        } else {
            event.getChannel().sendMessage("It's not TextChannel");
        }
    }

//    public static void plays(MessageReceivedEvent event) {
//        String[] msg = event.getMessage().getContentDisplay().split(" ");
//        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
//            StringBuffer sb_msg = new StringBuffer();
//            GuildVoiceState state;
//            if ((state = event.getMember().getVoiceState()) != null) {
//                VoiceChannel vc;
//                if ((vc = state.getChannel()) != null) {
//                    MusicController mc = BOTImpl.INSTANCE.getPlayerManager().getController(vc.getGuild().getIdLong());
//                    AudioPlayer player = mc.getPlayer();
//                    AudioPlayerManager apm = BOTImpl.INSTANCE.getAudioPlayerManager();
//                    AudioManager manager = vc.getGuild().getAudioManager();
//                    if (event.getMessage().getContentDisplay().equals(DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_MUSIC_PLAY)) {
//                        player.setPaused(false);
//                    } else {
//                        manager.openAudioConnection(vc);
//
//                        StringBuffer sb = new StringBuffer();
//
//                        for (int i = 1; i < msg.length; i++) {
//                            sb.append(msg[i] + " ");
//                        }
//
//                        String url = sb.toString().trim();
//                        url = url.replace("<", "");
//                        url = url.replace(">", "");
//                        if (!url.startsWith("http")) {
//                            url = "ytsearch: " + url;
//                        }
//
//                        final String uri = url;
//                        //sb_msg.append("**URL:** <" + uri + ">");
//                        alrh = new DiscordAudioLoadResultHandler(uri, mc);
//                        apm.loadItem(uri, alrh);
//                    }
//                    event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_PLAY).queue();
//                    sb_msg.append(AudioTrackClone.getTrackInfo());
//                }
//            }
//            event.getChannel().sendMessage(sb_msg.toString()).queue();
//        }
//    }

    public static void stop(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT)) {
            lp.setEvent(event);
            lp.setVoiceChannel(event.getMember().getVoiceState().getChannel());
            lp.stop((TextChannel) event.getChannel());
        } else {
            event.getChannel().sendMessage("It's not TextChannel");
        }
    }

    public static void pause(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.TEXT)) {
            lp.setEvent(event);
            lp.setVoiceChannel(event.getMember().getVoiceState().getChannel());
            lp.pause((TextChannel) event.getChannel());
        } else {
            event.getChannel().sendMessage("It's not TextChannel");
        }
    }

    public static void volume(MessageReceivedEvent event) {
        lp.setEvent(event);
        lp.setVoiceChannel(event.getMember().getVoiceState().getChannel());
    }

    public static void repeat(MessageReceivedEvent event) {
        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
//            System.out.println("Volume: " + msg[1]);
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_OK).queue();
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_REPEAT_ONE).queue();
        }
    }

    public static void repeatAll(MessageReceivedEvent event) {
        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
//            System.out.println("Volume: " + msg[1]);
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_OK).queue();
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_REPEAT_ALL).queue();
        }
    }

//    public static void pause(MessageReceivedEvent event) {
//        lp.setEvent(event);
//        lp.setVoiceChannel(event.getMember().getVoiceState().getChannel());
//        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
//            BOTImpl.INSTANCE.getPlayerManager().getController(event.getGuild().getIdLong()).getPlayer().setPaused(true);
//            event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_PAUSE).queue();
//        }
//    }

    public static void info(MessageReceivedEvent event) {
        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
            event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_PAUSE).queue();
        }
    }

    public static void skip(MessageReceivedEvent event) {
        lp.setEvent(event);
        lp.setVoiceChannel(event.getMember().getVoiceState().getChannel());
        if (Config.getInstance().getTextChannels_Commands_Music().contains(event.getChannel().getId())) {
            if (event.isFromType(ChannelType.TEXT)) {
                lp.skipTrack((TextChannel) event.getChannel());
            } else {
                event.getChannel().sendMessage("It's not TextChannel");
            }
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
