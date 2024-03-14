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
                (button) -> ClientPlayNetworking.send(ModPackets.TRANSFER_ALL_INVENTORY, PacketByteBufs.create().writeBoolean(fromPlayerInventory)),
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
                (button) -> ClientPlayNetworking.send(ModPackets.TRANSFER_ALL_INVENTORY, PacketByteBufs.create().writeBoolean(fromPlayerInventory)),
                getTooltip(fromPlayerInventory));
    }

    private static Text getTooltip(boolean fromPlayerInventory) {
        String key = fromPlayerInventory
                ? "nexusutils.button.transfer_place"
                : "nexusutils.button.transfer_take";
        return Text.translatable(key);
    }
}