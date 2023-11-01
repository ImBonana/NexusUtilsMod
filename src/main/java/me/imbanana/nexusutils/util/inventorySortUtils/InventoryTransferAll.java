package me.imbanana.nexusutils.util.inventorySortUtils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.HorseScreenHandler;

public class InventoryTransferAll {
    public static void transferAll(PlayerEntity player, boolean fromPlayerInventory) {
        Inventory containerInventory = InventoryUtils.getContainerInventory(player);
        if (containerInventory == null) {
            return;
        }

        Inventory playerInventory = player.getInventory();

        SlotRange playerSlotRange = SlotRange.playerMainRange();
        SlotRange containerSlotRange = SlotRange.fullRange(containerInventory);

        if (player.currentScreenHandler instanceof HorseScreenHandler) {
            containerSlotRange = SlotRange.horseMainRange(containerInventory);
        }

        if (fromPlayerInventory) {
            InventoryUtils.transferEntireInventory(playerInventory,
                    containerInventory,
                    playerSlotRange,
                    containerSlotRange,
                    player.playerScreenHandler,
                    player.currentScreenHandler,
                    player);
        } else {
            InventoryUtils.transferEntireInventory(containerInventory,
                    playerInventory,
                    containerSlotRange,
                    playerSlotRange,
                    player.currentScreenHandler,
                    player.playerScreenHandler,
                    player);
        }
    }
}
