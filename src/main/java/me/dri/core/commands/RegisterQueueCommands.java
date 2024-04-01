package me.dri.core.commands;

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import me.dri.infra.discord.interfaces.RegisterSlashCommand;
import reactor.core.publisher.Mono;

public class RegisterQueueCommands {

    private final Commands queueCommands;

    public RegisterQueueCommands(Commands queueCommands) {
        this.queueCommands = queueCommands;
    }

    @RegisterSlashCommand(name = "teste")
    public void teste() {
        System.out.println("teste");
    }

    @RegisterSlashCommand(name = "start")
    public Mono start(ApplicationCommandInteractionEvent event) {
        return this.queueCommands.handle(event);
    }

    @RegisterSlashCommand(name = "delete")
    public Mono delete(ApplicationCommandInteractionEvent event) {
        return this.queueCommands.delete(event);
    }
}
