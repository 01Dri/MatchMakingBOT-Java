package me.dri.core.embeds;

import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import me.dri.core.entities.Player;
import me.dri.core.entities.Queue;

import java.util.List;
import java.util.stream.Collectors;

public class EmbedsMessages {

    public static EmbedCreateSpec embedCreateQueues(List<Player> playerList) {
        if (playerList.isEmpty()) {
            return EmbedCreateSpec.builder()
                    .color(Color.RED)
                    .title("MATCHMAKING FILA")
                    .description("Filas")
                    .addField("PLAYERS EM FILA:", "", false)
                    .addField("PARTIDAS EM ANDAMENTO:", "0", false)
                    .build();
        } else {
            return EmbedCreateSpec.builder()
                    .color(Color.RED)
                    .title("MATCHMAKING FILA")
                    .description("Filas")
                    .addField("PLAYERS EM FILA:",
                            playerList.stream()
                                    .map(Player::getDiscordName) // Obtém o nome do Discord de cada jogador
                                    .collect(Collectors.joining("\n")), // Junta os nomes com uma quebra de linha
                            false)
                    .addField("PARTIDAS EM ANDAMENTO:", "0", false)
                    .build();
        }
    }
}
