package me.dri.core.commands;

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.MessageCreateSpec;
import me.dri.core.embeds.EmbedsMessages;
import me.dri.core.entities.Queue;
import me.dri.core.enums.Rank;
import me.dri.core.services.QueueService;
import reactor.core.publisher.Mono;

public class QueueCommands implements Commands {

    private Message messageButtonCreated;
    private final QueueService queueService;

    public QueueCommands(QueueService queueService) {
        this.queueService = queueService;
    }

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
    protected MessageCreateSpec start() {
        Queue queue = this.queueService.createQueues(Rank.RANK_A);
        Queue queue2 = this.queueService.createQueues(Rank.RANK_B);
        String queuesId = queue.getId().toString() + " - " + queue2.getId().toString();
        Button button = Button.primary(queuesId, "Entrar/Sair");
//        this.buttonService.saveButton(new ButtonQueues("button_queue", button.getCustomId(), button.getLabel()));
        return MessageCreateSpec.builder()
                .content("Filas iniciadas!!!")
                .embeds(EmbedsMessages.embedCreateQueues())
                .addComponent(ActionRow.of(button))
                .build();

    }
}
