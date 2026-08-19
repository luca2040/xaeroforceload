package com.apollo.xaeroforceload.mixin;

import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import xaero.map.gui.GuiMap;

@Mixin(GuiMap.class)
public interface GuiMapFields {
    @Accessor("rightClickX")
    int xaeroforceload$getRightClickX();

    @Accessor("rightClickY")
    int xaeroforceload$getRightClickY();

    @Accessor("rightClickZ")
    int xaeroforceload$getRightClickZ();

    @Accessor("rightClickDim")
    ResourceKey<?> xaeroforceload$getRightClickDim();
}