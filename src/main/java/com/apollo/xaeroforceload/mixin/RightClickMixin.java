package com.apollo.xaeroforceload.mixin;

import java.util.ArrayList;

import com.apollo.xaeroforceload.mixindata.ForceClickOption;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import xaero.map.gui.IRightClickableElement;
import xaero.map.gui.dropdown.rightclick.GuiRightClickMenu;
import xaero.map.gui.dropdown.rightclick.RightClickOption;

@Mixin(GuiRightClickMenu.class)
public class RightClickMixin {
    private static final Logger LOGGER = LogUtils.getLogger();

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

        LOGGER.info("Xaero right-click target: {}", target.getClass().getName());
        LOGGER.info("Xaero right-click options: {}", options.size());

        RightClickOption forceLoadOption = new ForceClickOption(options.size(), target);
        options.add(forceLoadOption);

        for (int i = 0; i < options.size(); i++) {
            RightClickOption option = options.get(i);

            LOGGER.info(
                    "  [{}] {} | active={}",
                    i,
                    option.getDisplayName(),
                    option.isActive()
            );
        }

        return options;
    }
}