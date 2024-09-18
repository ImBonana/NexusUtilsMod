package me.imbanana.nexusutils;

import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.block.entity.renderer.SleepingBagBlockEntityRenderer;
import me.imbanana.nexusutils.entity.ModEntities;
import me.imbanana.nexusutils.entity.client.ModModelLayers;
import me.imbanana.nexusutils.entity.client.SnailRenderer;
import me.imbanana.nexusutils.entity.client.models.SnailModel;
import me.imbanana.nexusutils.events.KeyInputHandler;
import me.imbanana.nexusutils.events.ModClientEvents;
import me.imbanana.nexusutils.fluids.ModFluids;
import me.imbanana.nexusutils.fluids.custom.milk.MilkFluidVariantAttributeHandler;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.item.backpack.BackpackEntityModel;
import me.imbanana.nexusutils.item.backpack.BackpackEntityModelRenderer;
import me.imbanana.nexusutils.item.backpack.PlayerBackpackFeatureRenderer;
import me.imbanana.nexusutils.screen.ModScreenHandlers;
import me.imbanana.nexusutils.screen.backpack.BackpackScreen;
import me.imbanana.nexusutils.screen.copperhopper.CopperHopperScreen;
import me.imbanana.nexusutils.screen.hopperfilter.HopperFilterScreen;
import me.imbanana.nexusutils.screen.itemdisplay.ItemDisplayScreen;
import me.imbanana.nexusutils.screen.mailbox.MailBoxScreen;
import me.imbanana.nexusutils.screen.postbox.PostBoxScreen;
import me.imbanana.nexusutils.util.inventorySortUtils.client.InventoryButtonsManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class NexusUtilsClient implements ClientModInitializer {
    public static final Identifier DARK_UI_RESOURCE_PACK_ID = NexusUtils.idOf("dark-ui");

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ModClientEvents.registerEvents();

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.SNAIL, SnailModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SNAIL, SnailRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BACKPACK, BackpackEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.SLEEPING_BAG_HEAD, SleepingBagBlockEntityRenderer::getHeadTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.SLEEPING_BAG_FOOT, SleepingBagBlockEntityRenderer::getFootTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.SLEEPING_BAG_FULL, SleepingBagBlockEntityRenderer::getFullTexturedModelData);


        HandledScreens.register(ModScreenHandlers.ITEM_DISPLAY_SCREEN_HANDLER, ItemDisplayScreen::new);
        HandledScreens.register(ModScreenHandlers.COPPER_HOPPER_SCREEN_HANDLER, CopperHopperScreen::new);
        HandledScreens.register(ModScreenHandlers.HOPPER_FILTER_SCREEN_HANDLER, HopperFilterScreen::new);
        HandledScreens.register(ModScreenHandlers.BACKPACK_SCREEN_HANDLER, BackpackScreen::new);
        HandledScreens.register(ModScreenHandlers.MAIL_BOX_SCREEN_HANDLER, MailBoxScreen::new);
        HandledScreens.register(ModScreenHandlers.POST_BOX_SCREEN_HANDLER, PostBoxScreen::new);
        InventoryButtonsManager.INSTANCE.init();

        ModBlocks.registerClientRender();

        registerFluids();

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerEntityRenderer renderer) {
                registrationHelper.register(new PlayerBackpackFeatureRenderer<>(renderer, context.getModelLoader()));
            }
        }));

        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.BACKPACK, new BackpackEntityModelRenderer());

        FabricLoader.getInstance()
                .getModContainer(NexusUtils.MOD_ID)
                .ifPresent((container) -> ResourceManagerHelper.registerBuiltinResourcePack(NexusUtilsClient.DARK_UI_RESOURCE_PACK_ID,
                        container,
                        Text.literal("Nexus Utils Dark UI"),
                        ResourcePackActivationType.NORMAL));
    }

    private void registerFluids() {
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.MILK, new SimpleFluidRenderHandler(
                NexusUtils.idOf("block/milk"),
                NexusUtils.idOf("block/milk"),
                0xFFFFFFFF
        ));

        FluidVariantAttributes.register(ModFluids.MILK, new MilkFluidVariantAttributeHandler());

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.MILK);
    }

    @Environment(EnvType.CLIENT)
    public static boolean isDarkUiEnabled() {
        for (ResourcePackProfile profile : MinecraftClient.getInstance().getResourcePackManager().getEnabledProfiles()) {
            if (profile.getId().equalsIgnoreCase(NexusUtilsClient.DARK_UI_RESOURCE_PACK_ID.toString())) return true;
        }

        return false;
    }
}
