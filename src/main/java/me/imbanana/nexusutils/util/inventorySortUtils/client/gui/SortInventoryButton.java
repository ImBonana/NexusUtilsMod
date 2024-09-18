package me.imbanana.nexusutils.util.inventorySortUtils.client.gui;

import me.imbanana.nexusutils.mixin.HandledScreenAccessor;
import me.imbanana.nexusutils.networking.ModNetwork;
import me.imbanana.nexusutils.networking.packets.inventoryUtils.SortInventoryPacket;
import me.imbanana.nexusutils.util.Position2;
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
                (button) -> ModNetwork.NETWORK_CHANNEL.clientHandle().send(new SortInventoryPacket(isPlayerInventory)),
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
                (button) -> ModNetwork.NETWORK_CHANNEL.clientHandle().send(new SortInventoryPacket(isPlayerInventory)),
                getTooltip(isPlayerInventory));
    }

    private static Text getTooltip(boolean isPlayerInventory) {
        String key = isPlayerInventory
                ? "nexusutils.button.sort_player"
                : "nexusutils.button.sort_container";
        return Text.translatable(key);
    }
}