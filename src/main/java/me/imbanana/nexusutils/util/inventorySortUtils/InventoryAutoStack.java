package me.imbanana.nexusutils.util.inventorySortUtils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;

public class InventoryAutoStack {
    public static void autoStack(PlayerEntity player, boolean fromPlayerInventory) {
        Inventory containerInventory = getContainerInventory(player);
        if (containerInventory == null) {
            return;
        }

        Inventory playerInventory = player.getInventory();

        if (fromPlayerInventory) {
            autoStackInventories(playerInventory, containerInventory, player);
        } else {
            autoStackInventories(containerInventory, playerInventory, player);
        }
    }

    public static Inventory getContainerInventory(PlayerEntity player) {
        ScreenHandler currentScreenHandler = player.currentScreenHandler;
        if (currentScreenHandler == null) {
            return null;
        }

        try {
            return currentScreenHandler.getSlot(0).inventory;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private static void autoStackInventories(Inventory from, Inventory to, PlayerEntity player) {
        autoStackInventories(from, to, SlotRange.fullRange(from), SlotRange.fullRange(to), player);
    }

    private static void autoStackInventories(Inventory from, Inventory to, SlotRange fromRange, SlotRange toRange, PlayerEntity player) {
        InventoryUtils.transferEntireInventory(from,
                to,
                fromRange,
                toRange,
                (fromStack, toStack) -> !toStack.isEmpty(),
                player);
    }
}
