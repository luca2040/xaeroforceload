package com.apollo.xaeroforceload.mixin;

import com.apollo.xaeroforceload.networking.SyncClientData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.ForceLoadCommand;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ForceLoadCommand.class)
public abstract class ForceloadCommandMixin {
    @Inject(
            method = "removeAll",
            at = @At("RETURN")
    )
    private static void xaeroforceload$removeAll(
            CommandSourceStack source,
            CallbackInfoReturnable<Integer> cir
    ) {
        ServerLevel level = source.getLevel();
        PacketDistributor.sendToAllPlayers(
                new SyncClientData(
                        level.dimension(),
                        level.getForcedChunks()
                ));
    }

    @Inject(
            method = "changeForceLoad",
            at = @At("RETURN")
    )
    private static void xaeroforceload$changeForceLoad(
            CommandSourceStack source,
            ColumnPos from,
            ColumnPos to,
            boolean add,
            CallbackInfoReturnable<Integer> cir
    ) {
        ServerLevel level = source.getLevel();
        PacketDistributor.sendToAllPlayers(
                new SyncClientData(
                        level.dimension(),
                        level.getForcedChunks()
                ));
    }
}
