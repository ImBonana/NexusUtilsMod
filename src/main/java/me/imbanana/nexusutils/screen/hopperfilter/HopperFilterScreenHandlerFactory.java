package me.imbanana.nexusutils.screen.hopperfilter;

import me.imbanana.nexusutils.networking.packets.screens.ItemInventoryOpeningData;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class HopperFilterScreenHandlerFactory implements ExtendedScreenHandlerFactory<ItemInventoryOpeningData> {

    private final ItemStack stack;

    public HopperFilterScreenHandlerFactory(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public ItemInventoryOpeningData getScreenOpeningData(ServerPlayerEntity player) {
        return new ItemInventoryOpeningData(stack);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.nexusutils.hopper_filter");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new HopperFilterScreenHandler(syncId, playerInventory, this.stack);
    }
}
