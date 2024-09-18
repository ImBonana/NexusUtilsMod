package me.imbanana.nexusutils.util.inventorySortUtils.client.gui;

import me.imbanana.nexusutils.mixin.HandledScreenAccessor;
import me.imbanana.nexusutils.networking.ModNetwork;
import me.imbanana.nexusutils.networking.packets.inventoryUtils.AutoStackInventoryPacket;
import me.imbanana.nexusutils.util.Position2;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;

public class AutoStackButton extends InventoryManagementButton {
    public AutoStackButton(
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
                new Position2(fromPlayerInventory ? 2 : 1, 0),
                fromPlayerInventory,
                (button) -> ModNetwork.NETWORK_CHANNEL.clientHandle().send(new AutoStackInventoryPacket(fromPlayerInventory)),
                getTooltip(fromPlayerInventory));
    }

    public AutoStackButton(
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
                new Position2(fromPlayerInventory ? 2 : 1, 0),
                fromPlayerInventory,
                (button) -> ModNetwork.NETWORK_CHANNEL.clientHandle().send(new AutoStackInventoryPacket(fromPlayerInventory)),
                getTooltip(fromPlayerInventory));
    }

    private static Text getTooltip(boolean fromPlayerInventory) {
        String key = fromPlayerInventory
                ? "nexusutils.button.autostack_into"
                : "nexusutils.button.autostack_from";
        return Text.translatable(key);
    }
}