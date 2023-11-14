package me.imbanana.nexusutils;

import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.block.entity.ModBlockEntities;
import me.imbanana.nexusutils.block.entity.renderer.ItemDisplayBlockEntityRenderer;
import me.imbanana.nexusutils.entity.ModEntities;
import me.imbanana.nexusutils.entity.client.ModModelLayers;
import me.imbanana.nexusutils.entity.client.SnailRenderer;
import me.imbanana.nexusutils.entity.client.models.SnailModel;
import me.imbanana.nexusutils.events.KeyInputHandler;
import me.imbanana.nexusutils.networking.ModPackets;
import me.imbanana.nexusutils.screen.ModScreenHandlers;
import me.imbanana.nexusutils.screen.copperhopper.CopperHopperScreen;
import me.imbanana.nexusutils.screen.hopperfilter.HopperFilterScreen;
import me.imbanana.nexusutils.screen.itemdisplay.ItemDisplayScreen;
import me.imbanana.nexusutils.util.inventorySortUtils.client.InventoryButtonsManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class NexusUtilsClient implements ClientModInitializer {
    public static final Identifier DARK_UI_RESOURCE_PACK_ID = new Identifier(NexusUtils.MOD_ID, "dark-ui");

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModPackets.registerS2CPackets();

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.SNAIL, SnailModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SNAIL, SnailRenderer::new);

        HandledScreens.register(ModScreenHandlers.ITEM_DISPLAY_SCREEN_HANDLER, ItemDisplayScreen::new);
        HandledScreens.register(ModScreenHandlers.COPPER_HOPPER_SCREEN_HANDLER, CopperHopperScreen::new);
        HandledScreens.register(ModScreenHandlers.HOPPER_FILTER_SCREEN_HANDLER, HopperFilterScreen::new);
        InventoryButtonsManager.INSTANCE.init();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ITEM_DISPLAY, RenderLayer.getCutout());

        BlockEntityRendererFactories.register(ModBlockEntities.ITEM_DISPLAY_BLOCK_ENTITY, ItemDisplayBlockEntityRenderer::new);

        FabricLoader.getInstance()
                .getModContainer(NexusUtils.MOD_ID)
                .ifPresent((container) -> ResourceManagerHelper.registerBuiltinResourcePack(NexusUtilsClient.DARK_UI_RESOURCE_PACK_ID,
                        container,
                        Text.literal("Nexus Utils Dark UI"),
                        ResourcePackActivationType.NORMAL));
    }
}
