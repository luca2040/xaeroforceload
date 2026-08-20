package com.apollo.xaeroforceload.networking;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record SyncClientData(
        ResourceKey<Level> dim, LongSet chunkSet)
        implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SyncClientData> TYPE =
            new CustomPacketPayload.Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            "xaeroforceload", "sync_client_data"));

    public static final StreamCodec<ByteBuf, SyncClientData> STREAM_CODEC =
            StreamCodec.composite(
                    ResourceKey.streamCodec(Registries.DIMENSION), SyncClientData::dim,
                    ByteBufCodecs.collection(LongOpenHashSet::new, ByteBufCodecs.VAR_LONG), SyncClientData::chunkSet,
                    SyncClientData::new
            );

    @Override
    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}