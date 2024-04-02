package me.dri.core.repositories;

import me.dri.core.entities.Player;

public interface PlayerRepository {

    void save(Player player);

    Player findPlayerByDiscordName(String discordName);

}
