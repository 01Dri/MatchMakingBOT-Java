package me.dri.infra.discord.commands;

import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.discordjson.json.ApplicationCommandRequest;
import me.dri.infra.discord.interfaces.RegisterSlashCommand;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

public class SlashCommandManager {

    public static void registerCommands(GatewayDiscordClient gatewayDiscordClient, Object obj) {
        Class<?> clazz = obj.getClass();
        for (var method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RegisterSlashCommand.class)) {
                RegisterSlashCommand annotation = method.getAnnotation(RegisterSlashCommand.class);
                String name = annotation.name();
                System.out.println(name);

                ApplicationCommandRequest commandRequest = ApplicationCommandRequest.builder()
                        .name(name)
                        .description("Test command")
                        .build();
//                commands.put(clazz, method);
                long applicationId = gatewayDiscordClient.getRestClient().getApplicationId().block();
                gatewayDiscordClient.getRestClient().getApplicationService().createGlobalApplicationCommand(
                                applicationId, commandRequest)
                        .subscribe();
            }
        }
    }
    public static Function<ApplicationCommandInteractionEvent, Mono<Void>> handleInteraction(Object obj) {
            return event -> {
                String commandName = event.getCommandName();
                Class<?> clazz = obj.getClass();
                try {
                    System.out.println(Arrays.toString(clazz.getDeclaredMethods()));
                    Method[] methods = clazz.getDeclaredMethods();

                    for (Method method : methods) {
                        if (method.getName().equals(commandName)) {
                            return (Mono<Void>) method.invoke(obj, event);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                return null;
            };
        }
    }
