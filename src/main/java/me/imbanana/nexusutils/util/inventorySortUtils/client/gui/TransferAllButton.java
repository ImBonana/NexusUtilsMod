package me.imbanana.nexusutils.util.inventorySortUtils.client.gui;

import me.imbanana.nexusutils.mixin.HandledScreenAccessor;
import me.imbanana.nexusutils.networking.ModNetwork;
import me.imbanana.nexusutils.networking.packets.inventoryUtils.TransferAllInventoryPacket;
import me.imbanana.nexusutils.util.Position2;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;

public class TransferAllButton extends InventoryManagementButton {
    public TransferAllButton(
            HandledScreen<?> parent,
            Inventory inventory,
            Slot referenceSlot,
            Position2 offset,
            boolean fromPlayerInventory) {
        super(
                parent,
                inventory,
                referenceSlot,
                offset,
                new Position2(fromPlayerInventory ? 4 : 3, 0),
                fromPlayerInventory,
                (button) -> ModNetwork.NETWORK_CHANNEL.clientHandle().send(new TransferAllInventoryPacket(fromPlayerInventory)),
                getTooltip(fromPlayerInventory));
    }

    public TransferAllButton(
            HandledScreenAccessor parent,
            Inventory inventory,
            Slot referenceSlot,
            Position2 offset,
            boolean fromPlayerInventory) {
        super(
                parent,
                inventory,
                referenceSlot,
                offset,
                new Position2(fromPlayerInventory ? 4 : 3, 0),
                fromPlayerInventory,
                (button) -> ModNetwork.NETWORK_CHANNEL.clientHandle().send(new TransferAllInventoryPacket(fromPlayerInventory)),
                getTooltip(fromPlayerInventory));
    }

    private static Text getTooltip(boolean fromPlayerInventory) {
        String key = fromPlayerInventory
                ? "nexusutils.button.transfer_place"
                : "nexusutils.button.transfer_take";
        return Text.translatable(key);
    }
}