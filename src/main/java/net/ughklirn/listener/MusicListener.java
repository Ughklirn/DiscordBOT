package net.ughklirn.listener;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.ughklirn.BOTImpl;
import net.ughklirn.DiscordCred;
import net.ughklirn.audio.DiscordAudioLoadResultHandler;
import net.ughklirn.audio.MusicController;

public class MusicListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentDisplay().split(" ");
        boolean isRightChannel = (event.getChannel().getId().equals(DiscordCred.BOT_TCHANNEL_MUSIC_ID)) || (event.getChannel().getId().equals(DiscordCred.BOT_TCHANNEL_MUSIC_TEAM_ID));
        if (event.isFromType(ChannelType.TEXT)) {
            if (isRightChannel) {
                if (event.getMessage().getContentDisplay().startsWith(DiscordCred.BOT_CMD_PREFIX)) {
                    switch (args[0]) {
                        case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_MUSIC_PLAY:
                            this.play(event, args);
                            break;
                        case DiscordCred.BOT_CMD_PREFIX + DiscordCred.BOT_CMD_MUSIC_STOP:
                            this.stop(event, args);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void play(MessageReceivedEvent event, String[] args) {
        GuildVoiceState state;
        if ((state = event.getMember().getVoiceState()) != null) {
            VoiceChannel vc;
            if ((vc = state.getChannel()) != null) {
                MusicController mc = BOTImpl.INSTANCE.getPlayerManager().getController(vc.getGuild().getIdLong());
                AudioPlayer player = mc.getPlayer();
                AudioPlayerManager apm = BOTImpl.INSTANCE.getAudioPlayerManager();
                AudioManager manager = vc.getGuild().getAudioManager();
                manager.openAudioConnection(vc);

                StringBuilder sb = new StringBuilder();

                for (int i = 1; i < args.length; i++) {
                    sb.append(args[i] + " ");
                }

                String url = sb.toString().trim();
                if (!url.startsWith("http")) {
                    url = "ytsearch: " + url;
                }

                final String uri = url;
                apm.loadItem(uri, new DiscordAudioLoadResultHandler(uri, mc));
                event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_OK).queue();
            }
        }
    }

    private void stop(MessageReceivedEvent event, String[] args) {
        GuildVoiceState state;
        if ((state = event.getMember().getVoiceState()) != null) {
            VoiceChannel vc;
            if ((vc = state.getChannel()) != null) {
                MusicController mc = BOTImpl.INSTANCE.getPlayerManager().getController(vc.getGuild().getIdLong());
                AudioPlayer player = mc.getPlayer();
                AudioPlayerManager apm = BOTImpl.INSTANCE.getAudioPlayerManager();
                AudioManager manager = vc.getGuild().getAudioManager();
                player.stopTrack();
                manager.closeAudioConnection();
                event.getMessage().addReaction(DiscordCred.BOT_REACTION_MUSIC_OK).queue();
            }
        }
    }


//    // Note that we are using GuildMessageReceivedEvent to only include messages from a Guild!
//    @Override
//    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
//        boolean isRightChannel = (event.getChannel().getId().equals(DiscordCred.BOT_TCHANNEL_MUSIC_ID)) || (event.getChannel().getId().equals(DiscordCred.BOT_TCHANNEL_MUSIC_TEAM_ID));
//
//        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
//        AudioSourceManagers.registerRemoteSources(playerManager);
//        AudioPlayer player = playerManager.createPlayer();
//        TrackScheduler trackScheduler = new TrackScheduler();
//        player.addListener(trackScheduler);
//        // This makes sure we only execute our code when someone sends a message with "!play"
//        if (!event.getMessage().getContentRaw().startsWith("!play")) return;
//        // Now we want to exclude messages from bots since we want to avoid command loops in chat!
//        // this will include own messages as well for bot accounts
//        // if this is not a bot make sure to check if this message is sent by yourself!
//        if (event.getAuthor().isBot()) return;
//        Guild guild = event.getGuild();
//        // This will get the first voice channel with the name "music"
//        // matching by voiceChannel.getName().equalsIgnoreCase("music")
//        VoiceChannel channel = guild.getVoiceChannelsByName("music", true).get(0);
//        AudioManager manager = guild.getAudioManager();
//
//        // MySendHandler should be your AudioSendHandler implementation
//        manager.setSendingHandler(new AudioPlayerSendHandler(null));
//        // Here we finally connect to the target voice channel
//        // and it will automatically start pulling the audio from the MySendHandler instance
//        manager.openAudioConnection(channel);
//    }
}
