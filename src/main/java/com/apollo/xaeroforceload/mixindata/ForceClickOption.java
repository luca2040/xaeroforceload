package com.apollo.xaeroforceload.mixindata;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.Screen;
import org.slf4j.Logger;
import xaero.map.gui.dropdown.rightclick.RightClickOption;
import xaero.map.gui.IRightClickableElement;

public class ForceClickOption extends RightClickOption {
    public ForceClickOption(int index, IRightClickableElement target) {
        super("translation needed here", index, target);
    }

    @Override
    public void onAction(Screen screen) {
        final Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("hello there, clicked here!");
    }
}