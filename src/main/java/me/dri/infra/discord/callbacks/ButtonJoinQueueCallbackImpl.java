package me.dri.infra.discord.callbacks;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import me.dri.core.entities.Button;
import me.dri.core.services.button.ButtonService;
import me.dri.core.services.queues.QueueCallbackServices;
import me.dri.infra.discord.interfaces.ButtonCallbacks;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class ButtonJoinQueueCallbackImpl implements ButtonCallbacks {


    private final ButtonService buttonService;
    private final QueueCallbackServices queueCallbackServices;

    public ButtonJoinQueueCallbackImpl(ButtonService buttonService, QueueCallbackServices queueCallbackServices) {
        this.buttonService = buttonService;
        this.queueCallbackServices = queueCallbackServices;
    }

    public Function<ButtonInteractionEvent, Mono<Void>> handle(GatewayDiscordClient gatewayDiscordClient) {
        return event -> {
            Button button = this.buttonService.findButtonCustomId(event.getCustomId());
            if (button.getIdButton().equals("buttonQueue")) {
                return queueCallbackServices.joinQuitOnQueueCallback(event, gatewayDiscordClient);
            }
            return null;
        };
    }
}
