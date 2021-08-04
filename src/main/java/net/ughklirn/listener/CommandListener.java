package net.ughklirn.listener;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.ughklirn.bot.BotDiscord;
import net.ughklirn.commands.CommandManager;
import net.ughklirn.utils.types.TypeChannels;
import net.ughklirn.utils.types.TypeSettings;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandListener extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandListener.class);
    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        try {
            final List<TextChannel> lChannels = new ArrayList<>();
            lChannels.add(event.getGuild().getTextChannelById(BotDiscord.getInstance().getIO().getChannels().getRow(event.getGuild().getId(), TypeChannels.BOT_LOG_ID)));

            if (lChannels.isEmpty()) {
                return;
            }

            final TextChannel pleaseDontDoThisAtAll = lChannels.get(0);

            final String useGuildSpecificSettingsInstead = String.format("Welcome %s to %s",
                    event.getMember().getUser().getAsTag(), event.getGuild().getName());

            pleaseDontDoThisAtAll.sendMessage(useGuildSpecificSettingsInstead).queue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        try {
            final List<TextChannel> lChannels = new ArrayList<>();
            lChannels.add(event.getGuild().getTextChannelById(BotDiscord.getInstance().getIO().getChannels().getRow(event.getGuild().getId(), TypeChannels.BOT_LOG_ID)));
            if (lChannels.isEmpty()) {
                return;
            }

            final TextChannel pleaseDontDoThisAtAll = lChannels.get(0);

            final String useGuildSpecificSettingsInstead = String.format("Goodbye %s",
                    event.getMember().getUser().getAsTag());

            pleaseDontDoThisAtAll.sendMessage(useGuildSpecificSettingsInstead).queue();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        try {
            String prefix = BotDiscord.getInstance().getIO().getSettings().getRow(event.getGuild().getId(), TypeSettings.PREFIX);
            if (event.getMessage().getContentDisplay().startsWith(prefix)) {
                this.manager.handle(event, prefix);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
