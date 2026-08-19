package com.apollo.xaeroforceload.mixindata;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.map.highlight.ChunkHighlighter;

import java.util.List;

public class ForceloadHighlighter extends ChunkHighlighter {
    public ForceloadHighlighter() {
        super(true);
    }

    @Override
    protected int[] getColors(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
        if (!chunkIsHighlit(dimension, chunkX, chunkZ)) {
            return null;
        }

        // 0xBBGGRRAA.
        int fill = 0x0000FF33;
        int border = 0x0000FF77;

        return new int[]{
                fill, // center
                border, // top
                border, // right
                border, // bottom
                border  // left
        };
    }

    @Override
    public int calculateRegionHash(ResourceKey<Level> dimension, int regionX, int regionZ) {
        if (regionX == 0 && regionZ == 0) {
            return 1;
        }

        return 0;
    }

    @Override
    public boolean regionHasHighlights(ResourceKey<Level> dimension, int regionX, int regionZ) {
        return (regionX == 0 && regionZ == 0);
    }

    @Override
    public boolean chunkIsHighlit(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
        return (chunkX == 0 && chunkZ == 0)
                || (chunkX == 1 && chunkZ == 1);
    }

    @Override
    public Component getChunkHighlightSubtleTooltip(
            ResourceKey<Level> dimension,
            int chunkX,
            int chunkZ
    ) {
        if (!chunkIsHighlit(dimension, chunkX, chunkZ)) {
            return null;
        }

        return Component.literal("Always loaded chunk");
    }

    @Override
    public Component getChunkHighlightBluntTooltip(
            ResourceKey<Level> dimension,
            int chunkX,
            int chunkZ
    ) {
        return null;
    }

    @Override
    public void addMinimapBlockHighlightTooltips(
            List<Component> list,
            ResourceKey<Level> dimension,
            int blockX,
            int blockZ,
            int width
    ) {
    }
}