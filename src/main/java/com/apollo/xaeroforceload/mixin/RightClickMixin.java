package com.apollo.xaeroforceload.mixin;

import java.util.ArrayList;

import com.apollo.xaeroforceload.mixindata.ForceClickOption;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import xaero.map.gui.IRightClickableElement;
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
        int blockPosX = mapFields.xaeroforceload$getRightClickX();
        int blockPosZ = mapFields.xaeroforceload$getRightClickZ();
        int chunkCoordX = blockPosX >> 4;
        int chunkCoordZ = blockPosZ >> 4;

        RightClickOption forceLoadOption =
                new ForceClickOption(
                        options.size(), target,
                        dimension,
                        chunkCoordX, chunkCoordZ,
                        true);

        options.add(forceLoadOption);

        return options;
    }
}