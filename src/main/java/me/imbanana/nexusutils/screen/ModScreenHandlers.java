package me.imbanana.nexusutils.screen;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.screen.copperhopper.CopperHopperScreenHandler;
import me.imbanana.nexusutils.screen.hopperfilter.HopperFilterScreenHandler;
import me.imbanana.nexusutils.screen.itemdisplay.ItemDisplayScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.HopperScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static final ScreenHandlerType<ItemDisplayScreenHandler> ITEM_DISPLAY_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(NexusUtils.MOD_ID, "item_display"), new ExtendedScreenHandlerType<>(ItemDisplayScreenHandler::new));

    public static final ScreenHandlerType<CopperHopperScreenHandler> COPPER_HOPPER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(NexusUtils.MOD_ID, "copper_hopper"), new ExtendedScreenHandlerType<>(CopperHopperScreenHandler::new));

    public static final ScreenHandlerType<HopperFilterScreenHandler> HOPPER_FILTER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(NexusUtils.MOD_ID, "hopper_filter"), new ExtendedScreenHandlerType<>(HopperFilterScreenHandler::new));

    public static void registerScreenHandlers() {
        NexusUtils.LOGGER.info("Registering Screen Handlers for " + NexusUtils.MOD_ID);
    }
}
