package com.apollo.xaeroforceload;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = XaeroForceload.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = XaeroForceload.MODID, value = Dist.CLIENT)
public class XaeroForceloadClient {
    public XaeroForceloadClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() != XaeroForceloadConfig.SPEC) {
            return;
        }

        ClientChunkState.getDimensions().forEach(
                dimension -> {
                    ClientChunkState.updateVersion(dimension);
                    ChunkRefresher.refreshAllChunks(dimension);
                }
        );
    }
}
