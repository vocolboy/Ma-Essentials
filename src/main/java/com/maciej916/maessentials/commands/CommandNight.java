package com.maciej916.maessentials.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldInfo;

public class CommandNight {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("night").requires(source -> source.hasPermissionLevel(2));
        builder.executes(context -> night(context));
        dispatcher.register(builder);
    }

    private static int night(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerWorld world = context.getSource().getWorld();
        ServerPlayerEntity player = context.getSource().asPlayer();
        WorldInfo worldData = world.getWorldInfo();
        world.setDayTime(20000);
        player.sendMessage(new TranslationTextComponent("command.maessentials.night", worldData.getWorldName(), true));
        return Command.SINGLE_SUCCESS;
    }
}