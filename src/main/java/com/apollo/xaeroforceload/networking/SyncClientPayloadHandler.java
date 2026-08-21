package com.apollo.xaeroforceload.networking;

import com.apollo.xaeroforceload.ChunkRefresher;
import com.apollo.xaeroforceload.ClientChunkState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SyncClientPayloadHandler {
    public static void handleDataOnMain(
            final SyncClientData data, final IPayloadContext ignoredContext) {
        ClientChunkState.setChunks(data.dim(), data.chunkSet());
        ChunkRefresher.refreshAllChunks(data.dim());
    }
}
