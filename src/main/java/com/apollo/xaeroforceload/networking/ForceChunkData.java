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
        ResourceKey<Level> dim, int left, int top, int right, int bottom, boolean loaded)
        implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ForceChunkData> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            "xaeroforceload", "force_chunk_data"));

    public static final StreamCodec<ByteBuf, ForceChunkData> STREAM_CODEC =
            StreamCodec.composite(
                    ResourceKey.streamCodec(Registries.DIMENSION), ForceChunkData::dim,
                    ByteBufCodecs.VAR_INT, ForceChunkData::left,
                    ByteBufCodecs.VAR_INT, ForceChunkData::top,
                    ByteBufCodecs.VAR_INT, ForceChunkData::right,
                    ByteBufCodecs.VAR_INT, ForceChunkData::bottom,
                    ByteBufCodecs.BOOL, ForceChunkData::loaded,
                    ForceChunkData::new
            );

    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}