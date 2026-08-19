package com.apollo.xaeroforceload.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record ForceChunkData(
        ResourceKey<Level> dim, int chunkx, int chunkz, boolean loaded)
        implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ForceChunkData> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            "xaeroforceload", "force_chunk_data"));

    public static final StreamCodec<ByteBuf, ForceChunkData> STREAM_CODEC =
            StreamCodec.composite(
                    ResourceKey.streamCodec(Registries.DIMENSION), ForceChunkData::dim,
                    ByteBufCodecs.VAR_INT, ForceChunkData::chunkx,
                    ByteBufCodecs.VAR_INT, ForceChunkData::chunkz,
                    ByteBufCodecs.BOOL, ForceChunkData::loaded,
                    ForceChunkData::new
            );

    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}