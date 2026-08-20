package com.apollo.xaeroforceload.networking;

import com.apollo.xaeroforceload.ClientChunkState;
import com.apollo.xaeroforceload.mixin.MapProcessorFields;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xaero.map.WorldMapSession;
import xaero.map.highlight.DimensionHighlighterHandler;
import xaero.map.world.MapDimension;

public class SyncClientPayloadHandler {
    public static void handleDataOnMain(
            final SyncClientData data, final IPayloadContext ignoredContext) {
        ClientChunkState.setChunks(data.dim(), data.chunkSet());

        WorldMapSession session = WorldMapSession.getCurrentSession();
        MapProcessorFields processor = (MapProcessorFields) session.getMapProcessor();
        MapDimension currentDim = processor.xaeroforceload$mapWorld().getCurrentDimension();
        DimensionHighlighterHandler highlighterHandler = currentDim.getHighlightHandler();

        ClientChunkState.getRegions(data.dim()).forEach(region ->
                highlighterHandler.clearCachedHash(
                        ClientChunkState.xFromLong(region),
                        ClientChunkState.zFromLong(region)));
    }
}
