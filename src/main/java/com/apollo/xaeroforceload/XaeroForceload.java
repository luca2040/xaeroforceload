package com.apollo.xaeroforceload;

import com.apollo.xaeroforceload.networking.ForceChunkData;
import com.apollo.xaeroforceload.networking.ForceChunkPayloadHandler;
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

    public XaeroForceload(IEventBus modEventBus, ModContainer ignoredModContainer) {
        modEventBus.addListener(XaeroForceload::register);
    }

    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                ForceChunkData.TYPE,
                ForceChunkData.STREAM_CODEC,
                ForceChunkPayloadHandler::handleDataOnMain
        );
    }
}
