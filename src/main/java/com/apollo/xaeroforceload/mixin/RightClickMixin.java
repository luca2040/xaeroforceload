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

import xaero.map.gui.GuiMap;
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
        if (!(target instanceof GuiMap))
            // clicking on waypoints gives a xaero.map.element.HoveredMapElementHolder instead
            return options;

        GuiMapFields mapFields = (GuiMapFields) target;

        ResourceKey<Level> dimension = mapFields.xaeroforceload$getRightClickDim();
        MapTileSelection tiles = mapFields.xaeroforceload$mapTileSelection();
        LongSet loadedChunks = ClientChunkState.get(dimension);

        int totalChunks = 0;
        int addedChunks = 0;
        for (int i = tiles.getLeft(); i <= tiles.getRight(); i++) {
            for (int j = tiles.getTop(); j <= tiles.getBottom(); j++) {
                totalChunks++;

                if (loadedChunks.contains(ChunkPos.asLong(i, j))) {
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