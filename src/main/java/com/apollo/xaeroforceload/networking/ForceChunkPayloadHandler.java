package com.apollo.xaeroforceload.networking;

import com.apollo.xaeroforceload.XaeroForceload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ForceChunkPayloadHandler {
    public static void handleDataOnMain(
            final ForceChunkData data, final IPayloadContext context) {
        ResourceKey<Level> dim = data.dim();
        int left = data.left();
        int top = data.top();
        int right = data.right();
        int bottom = data.bottom();
        boolean loaded = data.loaded();

        MinecraftServer server = context.player().getServer();
        if (server == null) {
            XaeroForceload.LOGGER.error("server is null");
            return;
        }

        ServerLevel level = server.getLevel(dim);
        if (level == null) {
            XaeroForceload.LOGGER.error("dim is null");
            return;
        }

        for (int i = left; i <= right; i++) {
            for (int j = top; j <= bottom; j++) {
                level.setChunkForced(i, j, loaded);
            }
        }

        for (ServerPlayer player : level.players()) {
            if (player.connection.hasChannel(SyncClientData.TYPE.id())) {
                PacketDistributor.sendToPlayer(
                        player,
                        new SyncClientData(
                                level.dimension(),
                                level.getForcedChunks()
                        ));
            }
        }
    }
}