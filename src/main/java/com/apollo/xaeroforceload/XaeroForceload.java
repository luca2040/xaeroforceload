package com.apollo.xaeroforceload;

import com.apollo.xaeroforceload.networking.ForceChunkData;
import com.apollo.xaeroforceload.networking.ForceChunkPayloadHandler;
import com.apollo.xaeroforceload.networking.SyncClientData;
import com.apollo.xaeroforceload.networking.SyncClientPayloadHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(XaeroForceload.MODID)
public class XaeroForceload {
    public static final String MODID = "xaeroforceload";
    public static final Logger LOGGER = LogUtils.getLogger();

    public XaeroForceload(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(XaeroForceload::register);
        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, XaeroForceloadConfig.SPEC);
    }

    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1").optional();

        registrar.playToServer(
                ForceChunkData.TYPE,
                ForceChunkData.STREAM_CODEC,
                ForceChunkPayloadHandler::handleDataOnMain
        );
        registrar.playToClient(
                SyncClientData.TYPE,
                SyncClientData.STREAM_CODEC,
                SyncClientPayloadHandler::handleDataOnMain
        );
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        MinecraftServer server = player.getServer();
        if (server == null) {
            LOGGER.error("Server is null");
            return;
        }

        if (player.connection.hasChannel(SyncClientData.TYPE.id()))
            for (ServerLevel level : server.getAllLevels()) {
                PacketDistributor.sendToPlayer(
                        player,
                        new SyncClientData(
                                level.dimension(),
                                level.getForcedChunks()
                        ));
            }
    }
}
