package me.imbanana.nexusutils;

import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.block.entity.ModBlockEntities;
import me.imbanana.nexusutils.block.entity.renderer.ItemDisplayBlockEntityRenderer;
import me.imbanana.nexusutils.events.KeyInputHandler;
import me.imbanana.nexusutils.networking.ModPackets;
import me.imbanana.nexusutils.screen.ModScreenHandlers;
import me.imbanana.nexusutils.screen.itemdisplay.ItemDisplayScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class NexusUtilsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();

        ModPackets.registerS2CPackets();

        HandledScreens.register(ModScreenHandlers.ITEM_DISPLAY_SCREEN_HANDLER, ItemDisplayScreen::new);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ITEM_DISPLAY, RenderLayer.getCutout());

        BlockEntityRendererFactories.register(ModBlockEntities.ITEM_DISPLAY_BLOCK_ENTITY, ItemDisplayBlockEntityRenderer::new);
    }
}
