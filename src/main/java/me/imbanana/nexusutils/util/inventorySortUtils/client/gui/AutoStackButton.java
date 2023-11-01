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
                (button) -> {
                    ClientPlayNetworking.send(ModPackets.AUTO_STACK_INVENTORY, PacketByteBufs.create().writeBoolean(fromPlayerInventory));
                },
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
                (button) -> {
                    ClientPlayNetworking.send(ModPackets.AUTO_STACK_INVENTORY, PacketByteBufs.create().writeBoolean(fromPlayerInventory));
                },
                getTooltip(fromPlayerInventory));
    }

    private static Text getTooltip(boolean fromPlayerInventory) {
        String key = fromPlayerInventory
                ? "nexusutils.button.autostack_into"
                : "nexusutils.button.autostack_from";
        return Text.translatable(key);
    }
}