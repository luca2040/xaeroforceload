package com.apollo.xaeroforceload.mixin;

import com.apollo.xaeroforceload.mixindata.ForceloadHighlighter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.highlight.AbstractHighlighter;
import xaero.map.highlight.HighlighterRegistry;

@Mixin(HighlighterRegistry.class)
public abstract class HighlighterRegistryMixin {
    @Shadow
    public abstract void register(AbstractHighlighter highlighter);

    @Inject(method = "end", at = @At("HEAD"))
    private void xaeroforceload$registerCustomHighlighter(CallbackInfo ci) {
        this.register(new ForceloadHighlighter());
    }
}