package net.ughklirn.listener.scheduler;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.ughklirn.audio.LavaPlayer;
import net.ughklirn.bot.BotDiscord;
import net.ughklirn.utils.texts.Text;
import net.ughklirn.utils.types.TypeChannels;
import net.ughklirn.utils.types.TypeReactions;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MusicEventScheduler {
    private static LavaPlayer lp = new LavaPlayer();
    private static List<String> allow_channels;

    public static void play(MessageReceivedEvent event) {
        if (allowChannel(event)) {
            StringBuffer keywords = new StringBuffer();
            String uri;
            String[] msg = event.getMessage().getContentDisplay().split(" ");
            if (event.isFromType(ChannelType.TEXT)) {
                lp.setEvent(event);
                lp.setVoiceChannel(event.getMember().getVoiceState().getChannel());
                if (!msg[1].startsWith("http")) {
                    System.out.println("Length:\n\t" + msg.length);
                    for (int i = 1; i < msg.length; i++) {
                        keywords.append(msg[i] + " ");
                        System.out.println("KEYWORD:\n\t\t" + msg[i]);
                    }
                    uri = "ytsearch: " + keywords.toString();
                } else {
                    System.out.println(msg[1]);
                    uri = msg[1];
                }
                System.out.println("\t\t\tURI: " + uri);
                lp.loadAndPlay((TextChannel) event.getChannel(), uri);//.replace("<", "").replace(">", ""));
            } else {
                event.getChannel().sendMessage(Text.TXT_NOT_TEXT_CHANNEL).queue();
            }
        } else {
            try {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.DENY)).queue();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void stop(MessageReceivedEvent event) {
        if (allowChannel(event)) {
            if (event.isFromType(ChannelType.TEXT)) {
                lp.setEvent(event);
                lp.setVoiceChannel(event.getMember().getVoiceState().getChannel());
                lp.stop((TextChannel) event.getChannel());
                try {
                    event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.MUSIC_STOP)).queue();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                event.getChannel().sendMessage(Text.TXT_NOT_TEXT_CHANNEL);
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
            if (event.isFromType(ChannelType.TEXT)) {
                lp.setEvent(event);
                lp.setVoiceChannel(event.getMember().getVoiceState().getChannel());
                lp.pause((TextChannel) event.getChannel());
                try {
                    event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.MUSIC_PAUSE)).queue();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                event.getChannel().sendMessage("It's not TextChannel");
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
        if (allowChannel(event)) {
            lp.setEvent(event);
            lp.setVoiceChannel(event.getMember().getVoiceState().getChannel());
            lp.volume(event.getTextChannel());
            try {
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.OK)).queue();
                event.getMessage().addReaction(BotDiscord.getInstance().getIO().getReactions().getRow(event.getGuild().getId(), TypeReactions.MUSIC_VOLUME)).queue();
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

    public static void repeat(MessageReceivedEvent event) {
        if (allowChannel(event)) {
//            System.out.println("Volume: " + msg[1]);
            try {
                lp.setEvent(event);
                lp.setVoiceChannel(event.getMember().getVoiceState().getChannel());
                lp.repeat(event.getTextChannel());
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
//            System.out.println("Volume: " + msg[1]);
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
            lp.setEvent(event);
            lp.setVoiceChannel(event.getMember().getVoiceState().getChannel());
            if (event.isFromType(ChannelType.TEXT)) {
                lp.skipTrack((TextChannel) event.getChannel());
            } else {
                event.getChannel().sendMessage(Text.TXT_NOT_TEXT_CHANNEL);
            }
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
