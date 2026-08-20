package com.apollo.xaeroforceload.mixindata;

import com.apollo.xaeroforceload.ClientChunkState;
import com.apollo.xaeroforceload.XaeroForceloadConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.map.highlight.ChunkHighlighter;
import net.minecraft.world.level.ChunkPos;

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

        // 0xBBGGRRAA
        int fill = XaeroForceloadConfig.getFillColor();
        int border = XaeroForceloadConfig.getBorderColor();

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
        return ClientChunkState.getVersion(dimension);
    }

    @Override
    public boolean regionHasHighlights(ResourceKey<Level> dimension, int regionX, int regionZ) {
        return ClientChunkState.getRegions(dimension).contains(ChunkPos.asLong(regionX, regionZ));
    }

    @Override
    public boolean chunkIsHighlit(ResourceKey<Level> dimension, int chunkX, int chunkZ) {
        return ClientChunkState.get(dimension).contains(ChunkPos.asLong(chunkX, chunkZ));
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

        return Component.translatable("xaeroforceload.mapmenu.alwaysloaded");
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