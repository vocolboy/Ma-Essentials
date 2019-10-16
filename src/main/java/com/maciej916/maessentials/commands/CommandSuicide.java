package com.maciej916.maessentials.commands;

import com.maciej916.maessentials.classes.Location;
import com.maciej916.maessentials.data.DataManager;
import com.maciej916.maessentials.libs.Methods;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandSuicide {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("suicide").requires(source -> source.hasPermissionLevel(0));
        builder
                .executes(context -> kill(context));
        dispatcher.register(builder);


        LiteralArgumentBuilder<CommandSource> builder2 = Commands.literal("suicide").requires(source -> source.hasPermissionLevel(2));
        builder2
                .then(Commands.argument("targetPlayer", EntityArgument.players())
                        .executes(context -> killArgs(context)));
        dispatcher.register(builder2);
    }

    private static int kill(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();
        player.setHealth(0);
        player.sendMessage(Methods.formatText("command.maessentials.suicide.self", TextFormatting.WHITE));
        DataManager.getPlayerData(player).setLastLocation(new Location(player));
        return Command.SINGLE_SUCCESS;
    }

    private static int killArgs(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();
        ServerPlayerEntity requestedPlayer = EntityArgument.getPlayer(context, "targetPlayer");
        if (requestedPlayer == player) {
            kill(context);
        } else {
            requestedPlayer.setHealth(0);
            DataManager.getPlayerData(player).setLastLocation(new Location(player));
            player.sendMessage(Methods.formatText("command.maessentials.suicide.player", TextFormatting.WHITE, requestedPlayer.getDisplayName()));
            requestedPlayer.sendMessage(Methods.formatText("command.maessentials.suicide.killed", TextFormatting.WHITE, player.getDisplayName()));
        }
        return Command.SINGLE_SUCCESS;
    }
}