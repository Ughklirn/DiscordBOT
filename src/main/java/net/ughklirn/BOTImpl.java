package net.ughklirn;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.User;
import discord4j.core.object.presence.Presence;
import reactor.core.publisher.Flux;

public class BOTImpl implements BOT {
    private final DiscordClient client;
    private GatewayDiscordClient gateway;

    public BOTImpl() {
        this.client = DiscordClient.create(DiscordCred.BOT_TOKEN);
    }

    public void log_in() {
        this.gateway = this.client.login().block();
        this.gateway.updatePresence(Presence.online()).block();
    }

    public void log_off() {
        this.gateway.logout().block();
    }

    public void getGuilds() {
        Flux<Guild> guilds = this.gateway.getGuilds();
        System.out.println(guilds);
    }

    public void getUsers() {
        Flux<User> users = this.gateway.getUsers();
        System.out.println(users);
    }
}
