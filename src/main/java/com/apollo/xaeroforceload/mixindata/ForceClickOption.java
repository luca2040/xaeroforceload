package com.apollo.xaeroforceload.mixindata;

import com.apollo.xaeroforceload.networking.ForceChunkData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import xaero.map.gui.dropdown.rightclick.RightClickOption;
import xaero.map.gui.IRightClickableElement;

public class ForceClickOption extends RightClickOption {
    private final ResourceKey<Level> dimension;
    private final int left;
    private final int top;
    private final int right;
    private final int bottom;
    private final boolean loaded;

    private static boolean isOneChunk(int left, int top, int right, int bottom) {
        return left == right && top == bottom;
    }

    public ForceClickOption(
            int index, IRightClickableElement target,
            ResourceKey<Level> dimension,
            int left, int top,
            int right, int bottom,
            boolean loaded) {
        super(
                (loaded ? "xaeroforceload.mapmenu.load" : "xaeroforceload.mapmenu.unload") +
                        (isOneChunk(left, top, right, bottom) ? "" : ".multiple"),
                Style.EMPTY.withItalic(true).withColor(
                        loaded ? ChatFormatting.AQUA : ChatFormatting.GOLD),
                index, target);

        this.dimension = dimension;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.loaded = loaded;
    }

    @Override
    public void onAction(Screen screen) {
        PacketDistributor.sendToServer(
                new ForceChunkData(
                        this.dimension,
                        this.left,
                        this.top,
                        this.right,
                        this.bottom,
                        this.loaded
                ));
    }
}