package net.ughklirn.scheduler;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import net.ughklirn.audio.DiscordAudioLoadResultHandler;
import net.ughklirn.audio.MusicController;
import net.ughklirn.bot.BotDiscord;
import net.ughklirn.utils.texts.Text;
import net.ughklirn.utils.types.TypeChannels;
import net.ughklirn.utils.types.TypeReactions;
import net.ughklirn.utils.types.TypeSettings;

import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MusicEventScheduler {
    private static List<String> allow_channels;

    public static void play(MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentDisplay().split(" ");
        if (allowChannel(event)) {
            GuildVoiceState state;
            if ((state = event.getMember().getVoiceState()) != null) {
                VoiceChannel vc;
                if ((vc = state.getChannel()) != null) {
                    MusicController mc = BotDiscord.getInstance().getPlayerManager().getController(vc.getGuild().getId());
                    AudioPlayer player = mc.getPlayer();
                    AudioPlayerManager apm = BotDiscord.getInstance().getAudioPlayerManager();
                    AudioManager manager = vc.getGuild().getAudioManager();
                    if (msg.length == 1) {
                        player.setPaused(false);
                    } else {
                        manager.openAudioConnection(vc);

                        StringBuffer sb = new StringBuffer();

                        for (int i = 1; i < msg.length; i++) {
                            sb.append(msg[i] + " ");
                        }

                        String url = sb.toString().trim().replace("<", "").replace(">", "");
//                        url = url.replace("<", "");
//                        url = url.replace(">", "");
                        if (!url.startsWith("http")) {
                            url = "ytsearch: " + url;
                        }

                        final String uri = url;
                        apm.loadItem(uri, new DiscordAudioLoadResultHandler(uri, mc));
                    }
                    try {
                        event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.MUSIC_PLAY)).queue();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription(Text.TXT_NOT_VOICE_CHANNEL);
                    builder.setColor(Color.RED);
                    event.getChannel().sendMessage(builder.build()).queue();
                }
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setDescription(Text.TXT_NOT_IN_VOICE_CHANNEL);
                builder.setColor(Color.RED);
                event.getChannel().sendMessage(builder.build()).queue();
            }
        }
    }

    public static void stop(MessageReceivedEvent event) {
        if (allowChannel(event)) {
            GuildVoiceState state;
            if ((state = event.getMember().getVoiceState()) != null) {
                VoiceChannel vc;
                if ((vc = state.getChannel()) != null) {
                    MusicController mc = BotDiscord.getInstance().getPlayerManager().getController(event.getGuild().getId());
                    AudioManager manager = vc.getGuild().getAudioManager();
                    AudioPlayer player = mc.getPlayer();
                    player.stopTrack();
                    manager.closeAudioConnection();
                }
            }
            try {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.MUSIC_STOP)).queue();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.DENY)).queue();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void pause(MessageReceivedEvent event) {
        if (allowChannel(event)) {
            GuildVoiceState state;
            if ((state = event.getMember().getVoiceState()) != null) {
                VoiceChannel vc;
                if ((vc = state.getChannel()) != null) {
                    MusicController mc = BotDiscord.getInstance().getPlayerManager().getController(event.getGuild().getId());
                    AudioPlayer player = mc.getPlayer();
                    player.setPaused(true);
                }
            }
            try {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.MUSIC_PAUSE)).queue();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.DENY)).queue();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void volume(MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentDisplay().split(" ");
        GuildVoiceState state;
        if ((state = event.getMember().getVoiceState()) != null) {
            VoiceChannel vc;
            if ((vc = state.getChannel()) != null) {
                MusicController mc = BotDiscord.getInstance().getPlayerManager().getController(event.getGuild().getId());
                AudioManager manager = vc.getGuild().getAudioManager();
                AudioPlayer player = mc.getPlayer();
                try {
                    BotDiscord.getInstance().getIO().getSettings().setRow(event.getGuild().getId(), TypeSettings.MUSIC_VOLUME, Integer.parseInt(msg[1]));
                    //player.setVolume(Integer.parseInt(BotDiscord.getInstance().getIO().getSettings().getRow(event.getGuild().getId(), TypeSettings.MUSIC_VOLUME)));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    player.setVolume(Integer.parseInt(msg[1]));
                }
                mc.reload();
            }
        }
        try {
            event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.OK)).queue();
            event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.MUSIC_VOLUME)).queue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void repeat(MessageReceivedEvent event) {
        if (allowChannel(event)) {
            String[] msg = event.getMessage().getContentDisplay().split(" ");
            if (event.getMember().getVoiceState() != null) {
                MusicController mc = BotDiscord.getInstance().getPlayerManager().getController(event.getGuild().getId());
                mc.getTrackScheduler().repeat();
                mc.reload();

            }
            try {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.OK)).queue();
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.MUSIC_REPEAT_ALL)).queue();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.DENY)).queue();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void repeatAll(MessageReceivedEvent event) {
        try {
            if (allowChannel(event)) {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.OK)).queue();
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.MUSIC_REPEAT_ALL)).queue();
            } else {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.DENY)).queue();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void info(MessageReceivedEvent event) {
        if (allowChannel(event)) {
            try {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.MUSIC_PAUSE)).queue();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.DENY)).queue();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void skip(MessageReceivedEvent event) {
        if (allowChannel(event)) {

        } else {
            try {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.DENY)).queue();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private static boolean allowChannel(MessageReceivedEvent event) {
        boolean allow = false;
        allow_channels = new LinkedList<>();
        String id = event.getGuild().getId();
        try {
            allow_channels.add(BotDiscord.getInstance().getIO().getChannels().getRow(id, TypeChannels.COMMANDS_ADMIN_ID));
            allow_channels.add(BotDiscord.getInstance().getIO().getChannels().getRow(id, TypeChannels.COMMANDS_ID));
            allow_channels.add(BotDiscord.getInstance().getIO().getChannels().getRow(id, TypeChannels.MUSIC_ID));
            allow_channels.add(BotDiscord.getInstance().getIO().getChannels().getRow(id, TypeChannels.MUSIC_TEAM_ID));
            allow_channels.add(BotDiscord.getInstance().getIO().getChannels().getRow(id, TypeChannels.MUSIC_USER_ID));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("### MusicEvent Begin ###");
        System.out.println("Allow ChannelID: " + event.getChannel().getId() + "\n\t\t" + event.getChannel().getName());
        for (String k : allow_channels) {
            System.out.println("\t ChannelID: " + k);
            if (k.equals(event.getChannel().getId())) {
                allow = true;
            }
        }
        System.out.println("### MusicEvent End ###");
        return allow;
    }
}
