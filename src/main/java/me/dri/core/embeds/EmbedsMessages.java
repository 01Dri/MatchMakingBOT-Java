package me.dri.core.embeds;

import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;

public class EmbedsMessages {

    public static EmbedCreateSpec embedCreateQueues() {
        return EmbedCreateSpec.builder()
                .color(Color.RED)
                .title("MATCHMAKING FILA")
                .description("Filas")
                .addField("PLAYERS EM FILA:", "0", false)
                .addField("PARTIDAS EM ANDAMENTO:", "0", false)
                .build();
    }
}
