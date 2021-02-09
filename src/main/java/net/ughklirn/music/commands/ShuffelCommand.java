package net.ughklirn.music.commands;

import de.coaster.DiscordTutorial;
import de.coaster.commands.types.ServerCommand;
import de.coaster.music.MusicController;
import net.dv8tion.jda.api.entities.*;

public class ShuffelCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        GuildVoiceState state;
        if ((state = m.getVoiceState()) != null) {
            VoiceChannel vc;
            if ((vc = state.getChannel()) != null) {
                MusicController controller = DiscordTutorial.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
                controller.getQueue().shuffel();
                message.addReaction("U+1F500").queue();
            }
        }
    }
}
