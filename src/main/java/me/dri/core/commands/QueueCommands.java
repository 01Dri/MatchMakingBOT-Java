package me.dri.core.commands;

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.MessageCreateSpec;
import me.dri.core.embeds.EmbedsMessages;
import reactor.core.publisher.Mono;

public class QueueCommands implements Commands {

    private Message messageButtonCreated;

    @Override
    public Mono handle(ApplicationCommandInteractionEvent event) {
        MessageChannel messageChannel = event.getInteraction().getChannel().block();
        if (messageChannel != null) {
            return messageChannel.createMessage(this.start()).flatMap(message -> {
                this.messageButtonCreated = message;
                return Mono.just(message);
            });
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> delete(ApplicationCommandInteractionEvent event) {
        if (this.messageButtonCreated != null) {
            return event.reply("Filas encerradas!")
                    .withEphemeral(true)
                    .then(this.messageButtonCreated.delete());
        }
        return Mono.empty();
    }
    private MessageCreateSpec start() {
        return MessageCreateSpec.builder()
                .content("Filas iniciadas!!!")
                .embeds(EmbedsMessages.embedCreateQueues())
                .build();

    }
}
