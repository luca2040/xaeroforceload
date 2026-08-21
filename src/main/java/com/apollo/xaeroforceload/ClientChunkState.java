package com.apollo.xaeroforceload;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientChunkState {
    private static final Map<ResourceKey<Level>, LongSet> LOADED =
            new ConcurrentHashMap<>();
    private static final Map<ResourceKey<Level>, LongSet> REGIONS =
            new ConcurrentHashMap<>();
    private static final Map<ResourceKey<Level>, LongSet> OLD_REGIONS =
            new ConcurrentHashMap<>();
    private static final Map<ResourceKey<Level>, Integer> VERSIONS =
            new HashMap<>();

    public static int xFromLong(long value) {
        return (int) value;
    }

    public static int zFromLong(long value) {
        return (int) (value >> 32);
    }

    public static void setChunks(
            ResourceKey<Level> dim,
            LongSet chunks
    ) {
        LOADED.put(dim, chunks);
        updateVersion(dim);

        LongSet regions = new LongOpenHashSet();
        for (long chunk : chunks) {
            int regionx = xFromLong(chunk) >> 5;
            int regionz = zFromLong(chunk) >> 5;

            regions.add(ChunkPos.asLong(regionx, regionz));
        }

        OLD_REGIONS.put(dim, REGIONS.getOrDefault(dim, LongSet.of()));
        REGIONS.put(dim, regions);
    }

    public static Set<ResourceKey<Level>> getDimensions() {
        return REGIONS.keySet();
    }

    public static LongSet get(ResourceKey<Level> dim) {
        return LOADED.getOrDefault(dim, LongSet.of());
    }

    public static LongSet getRegions(ResourceKey<Level> dim) {
        LongSet combined = new LongOpenHashSet();

        combined.addAll(OLD_REGIONS.getOrDefault(dim, LongSet.of()));
        combined.addAll(REGIONS.getOrDefault(dim, LongSet.of()));

        return combined;
    }

    public static void updateVersion(ResourceKey<Level> dim) {
        VERSIONS.merge(dim, 1, Integer::sum);
    }

    public static int getVersion(ResourceKey<Level> dim) {
        return VERSIONS.getOrDefault(dim, 0);
    }
}