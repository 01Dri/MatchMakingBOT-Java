package me.dri.infra.discord.config;

import discord4j.common.util.Snowflake;

public class GuildConfig {
    private Snowflake guildId;
    private String voiceChannelWaitingQueueName;

    public GuildConfig(Snowflake guildId, String voiceChannelWaitingQueueName) {
        this.guildId = guildId;
        this.voiceChannelWaitingQueueName = voiceChannelWaitingQueueName;
    }

    public Snowflake getGuildId() {
        return guildId;
    }

    public void setGuildId(Snowflake guildId) {
        this.guildId = guildId;
    }

    public String getVoiceChannelWaitingQueueName() {
        return voiceChannelWaitingQueueName;
    }

    public void setVoiceChannelWaitingQueueName(String voiceChannelWaitingQueueName) {
        this.voiceChannelWaitingQueueName = voiceChannelWaitingQueueName;
    }
}
