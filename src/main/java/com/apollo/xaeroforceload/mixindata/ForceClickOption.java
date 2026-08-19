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
    private final int chunkCoordX;
    private final int chunkCoordZ;
    private final boolean loaded;

    public ForceClickOption(
            int index, IRightClickableElement target,
            ResourceKey<Level> dimension,
            int chunkCoordX, int chunkCoordZ,
            boolean loaded) {
        super(
                loaded ? "xaeroforceload.mapmenu.load" : "xaeroforceload.mapmenu.unload",
                Style.EMPTY.withItalic(true).withColor(
                        loaded ? ChatFormatting.AQUA : ChatFormatting.GOLD),
                index, target);

        this.dimension = dimension;
        this.chunkCoordX = chunkCoordX;
        this.chunkCoordZ = chunkCoordZ;
        this.loaded = loaded;
    }

    @Override
    public void onAction(Screen screen) {
        PacketDistributor.sendToServer(
                new ForceChunkData(
                        this.dimension,
                        this.chunkCoordX,
                        this.chunkCoordZ,
                        this.loaded
                ));
    }
}