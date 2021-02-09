package net.ughklirn;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOTImpl implements BOT {
    private GatewayDiscordClient client;
    private DiscordClientBuilder builder;
    DefaultShardManager dBuilder;

    public BOTImpl() {
        this.builder = DiscordClientBuilder.create(DiscordCred.BOT_TOKEN);
        this.client = DiscordClientBuilder.create(DiscordCred.BOT_TOKEN).build().login().block();
        this.dBuilder = new DefaultShardManager(DiscordCred.BOT_TOKEN);
    }

    @Override
    public void init() {
        dBuilder.setActivity(Activity.playing("Minecraft"));
        dBuilder.setStatus(OnlineStatus.ONLINE);

        System.out.println("BOT logged on.");
    }

    @Override
    public void shutdown() {
        new Thread(() -> {
            String line = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                while ((line = br.readLine()) != null) {
                    if (line.equals(DiscordCred.BOT_EXIT)) {
                        if (this.dBuilder != null) {
                            this.dBuilder.setStatus(OnlineStatus.OFFLINE);
                            this.dBuilder.shutdown();
                            System.out.println("BOT logged off.");
                        }
                        br.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void bot() {
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    final User self = event.getSelf();
                    System.out.println(String.format(
                            "Logged in as %s#%s", self.getUsername(), self.getDiscriminator()
                    ));
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!ping"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Pong!"))
                .subscribe();

        client.onDisconnect().block();
    }

    @Override
    public void playMusic(MessageCreateEvent event) {

    }
}
