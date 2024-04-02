package me.dri.core.commands;

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.core.spec.MessageEditSpec;
import me.dri.core.embeds.EmbedsMessages;
import me.dri.core.entities.ButtonQueues;
import me.dri.core.entities.Player;
import me.dri.core.entities.Queue;
import me.dri.core.enums.Rank;
import me.dri.core.services.button.ButtonService;
import me.dri.core.services.queues.QueueService;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class QueueCommands implements Commands {
    private final QueueService queueService;
    private final ButtonService buttonService;
    private Message messageButtonCreated;

    private  Button buttonCreated;

    public QueueCommands(QueueService queueService, ButtonService buttonService) {
        this.queueService = queueService;
        this.buttonService = buttonService;
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

    @Override
    public Mono<Void> updateButtonQueues(List<Player> players) {
        return this.messageButtonCreated.edit(MessageEditSpec.builder()
                        .addEmbed(EmbedsMessages.embedCreateQueues(players))
                .addComponent(ActionRow.of(this.buttonCreated))
                .build()).then();
    }

    protected MessageCreateSpec start() {
        Queue queue = this.queueService.createQueues(Rank.RANK_A);
        Queue queue2 = this.queueService.createQueues(Rank.RANK_B);
        this.queueService.saveQueue(queue);
        this.queueService.saveQueue(queue2);
        String queuesId = queue.getId().toString() + " - " + queue2.getId().toString();
        Button button = Button.primary("buttonQueue", "Entrar/Sair");
        this.buttonCreated = button;
        this.buttonService.saveButton(new ButtonQueues(button.getCustomId().get(), queuesId, button.getLabel().get()));
        return MessageCreateSpec.builder()
                .content("Filas iniciadas!!!")
                .embeds(EmbedsMessages.embedCreateQueues(new ArrayList<>()))
                .addComponent(ActionRow.of(button))
                .build();

    }
}
