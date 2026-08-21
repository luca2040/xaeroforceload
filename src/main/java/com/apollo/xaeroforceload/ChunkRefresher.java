package com.apollo.xaeroforceload;

import com.apollo.xaeroforceload.mixin.MapProcessorFields;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.map.WorldMapSession;
import xaero.map.highlight.DimensionHighlighterHandler;
import xaero.map.world.MapDimension;

public class ChunkRefresher {
    public static void refreshAllChunks(ResourceKey<Level> dim) {
        WorldMapSession session = WorldMapSession.getCurrentSession();
        MapProcessorFields processor = (MapProcessorFields) session.getMapProcessor();
        MapDimension currentDim = processor.xaeroforceload$mapWorld().getCurrentDimension();

        if (currentDim == null) {
            XaeroForceload.LOGGER.error("Current map dimension is null");
            return;
        }

        DimensionHighlighterHandler highlighterHandler = currentDim.getHighlightHandler();

        ClientChunkState.getRegions(dim).forEach(region ->
                highlighterHandler.clearCachedHash(
                        ClientChunkState.xFromLong(region),
                        ClientChunkState.zFromLong(region)));
    }
}
