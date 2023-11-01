package me.imbanana.nexusutils;

import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.block.entity.ModBlockEntities;
import me.imbanana.nexusutils.block.entity.renderer.ItemDisplayBlockEntityRenderer;
import me.imbanana.nexusutils.events.KeyInputHandler;
import me.imbanana.nexusutils.networking.ModPackets;
import me.imbanana.nexusutils.screen.ModScreenHandlers;
import me.imbanana.nexusutils.screen.itemdisplay.ItemDisplayScreen;
import me.imbanana.nexusutils.util.inventorySortUtils.client.InventoryButtonsManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class NexusUtilsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModPackets.registerS2CPackets();

        HandledScreens.register(ModScreenHandlers.ITEM_DISPLAY_SCREEN_HANDLER, ItemDisplayScreen::new);
        InventoryButtonsManager.INSTANCE.init();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ITEM_DISPLAY, RenderLayer.getCutout());

        BlockEntityRendererFactories.register(ModBlockEntities.ITEM_DISPLAY_BLOCK_ENTITY, ItemDisplayBlockEntityRenderer::new);

        FabricLoader.getInstance()
                .getModContainer(NexusUtils.MOD_ID)
                .ifPresent((container) -> ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(
                                NexusUtils.MOD_ID,
                                "nexusutils-dark-ui"),
                        container,
                        Text.literal("Nexus Utils Dark UI"),
                        ResourcePackActivationType.NORMAL));
    }
}
