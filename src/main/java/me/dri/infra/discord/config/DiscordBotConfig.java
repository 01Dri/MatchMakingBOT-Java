package me.dri.infra.discord.config;

import discord4j.core.DiscordClient;

public class DiscordBotConfig {
    private String token;
    private String prefix;



    public DiscordBotConfig(String token, String prefix) {
        this.token = token;
        this.prefix = prefix;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public DiscordClient getClient() {
        return DiscordClient.create(this.token);
    }
}
