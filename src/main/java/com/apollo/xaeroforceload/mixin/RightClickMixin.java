package com.apollo.xaeroforceload.mixin;

import java.util.ArrayList;

import com.apollo.xaeroforceload.ClientChunkState;
import com.apollo.xaeroforceload.mixindata.ForceClickOption;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import xaero.map.gui.IRightClickableElement;
import xaero.map.gui.MapTileSelection;
import xaero.map.gui.dropdown.rightclick.GuiRightClickMenu;
import xaero.map.gui.dropdown.rightclick.RightClickOption;

@Mixin(GuiRightClickMenu.class)
public class RightClickMixin {
    @Redirect(
            method = "getMenu",
            at = @At(
                    value = "INVOKE",
                    target = "Lxaero/map/gui/IRightClickableElement;getRightClickOptions()Ljava/util/ArrayList;"
            )
    )
    private static ArrayList<RightClickOption> xaeroforceload$inspectOptions(
            IRightClickableElement target
    ) {
        ArrayList<RightClickOption> options = target.getRightClickOptions();
        GuiMapFields mapFields = (GuiMapFields) target;

        ResourceKey<Level> dimension = mapFields.xaeroforceload$getRightClickDim();
        MapTileSelection tiles = mapFields.xaeroforceload$mapTileSelection();

        long totalChunks = 0;
        long addedChunks = 0;
        LongSet loadedChunks = ClientChunkState.get(dimension);
        for (int i = tiles.getLeft(); i <= tiles.getRight(); i++) {
            for (int j = tiles.getTop(); j <= tiles.getBottom(); j++) {
                long longChunk = ChunkPos.asLong(i, j);
                totalChunks += 1;
                if (loadedChunks.contains(longChunk)) {
                    addedChunks++;
                }
            }
        }

        if (addedChunks == totalChunks || addedChunks == 0) {
            RightClickOption forceLoadOption =
                    new ForceClickOption(
                            options.size(), target,
                            dimension,
                            tiles.getLeft(), tiles.getTop(),
                            tiles.getRight(), tiles.getBottom(),
                            addedChunks == 0);

            options.add(forceLoadOption);
        } else {
            RightClickOption forceLoadOptionTrue =
                    new ForceClickOption(
                            options.size(), target,
                            dimension,
                            tiles.getLeft(), tiles.getTop(),
                            tiles.getRight(), tiles.getBottom(),
                            true);
            RightClickOption forceLoadOptionFalse =
                    new ForceClickOption(
                            options.size(), target,
                            dimension,
                            tiles.getLeft(), tiles.getTop(),
                            tiles.getRight(), tiles.getBottom(),
                            false);

            options.add(forceLoadOptionTrue);
            options.add(forceLoadOptionFalse);
        }

        return options;
    }
}