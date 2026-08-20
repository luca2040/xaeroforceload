package com.apollo.xaeroforceload.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import xaero.map.MapProcessor;
import xaero.map.world.MapWorld;

@Mixin(MapProcessor.class)
public interface MapProcessorFields {
    @Accessor("mapWorld")
    MapWorld xaeroforceload$mapWorld();
}
