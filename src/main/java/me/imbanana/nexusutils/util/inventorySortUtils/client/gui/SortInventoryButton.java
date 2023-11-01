package me.imbanana.nexusutils.util.inventorySortUtils.client.gui;

import me.imbanana.nexusutils.mixin.HandledScreenAccessor;
import me.imbanana.nexusutils.networking.ModPackets;
import me.imbanana.nexusutils.util.Position2;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;

public class SortInventoryButton extends InventoryManagementButton {
    public SortInventoryButton(
            HandledScreen<?> parent,
            Inventory inventory,
            Slot referenceSlot,
            Position2 offset,
            boolean isPlayerInventory) {
        super(
                parent,
                inventory,
                referenceSlot,
                offset,
                new Position2(0, 0),
                isPlayerInventory,
                (button) -> {
                    ClientPlayNetworking.send(ModPackets.SORT_INVENTORY, PacketByteBufs.create().writeBoolean(isPlayerInventory));
                },
                getTooltip(isPlayerInventory));
    }

    public SortInventoryButton(
            HandledScreenAccessor parent,
            Inventory inventory,
            Slot referenceSlot,
            Position2 offset,
            boolean isPlayerInventory) {
        super(
                parent,
                inventory,
                referenceSlot,
                offset,
                new Position2(0, 0),
                isPlayerInventory,
                (button) -> {
                    ClientPlayNetworking.send(ModPackets.SORT_INVENTORY, PacketByteBufs.create().writeBoolean(isPlayerInventory));
                },
                getTooltip(isPlayerInventory));
    }

    private static Text getTooltip(boolean isPlayerInventory) {
        String key = isPlayerInventory
                ? "nexusutils.button.sort_player"
                : "nexusutils.button.sort_container";
        return Text.translatable(key);
    }
}