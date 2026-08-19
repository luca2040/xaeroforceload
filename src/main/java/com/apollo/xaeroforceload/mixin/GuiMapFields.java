package com.apollo.xaeroforceload.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import xaero.map.gui.GuiMap;
import xaero.map.gui.MapTileSelection;

@Mixin(GuiMap.class)
public interface GuiMapFields {
    @Accessor("rightClickDim")
    ResourceKey<Level> xaeroforceload$getRightClickDim();

    @Accessor("mapTileSelection")
    MapTileSelection xaeroforceload$mapTileSelection();
}