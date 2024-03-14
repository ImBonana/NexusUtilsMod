package me.imbanana.nexusutils.util.inventorySortUtils;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;

public record SlotRange(int min, int max) {

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public static SlotRange fullRange(Inventory inventory) {
        return new SlotRange(0, inventory.size());
    }

    public static SlotRange playerMainRange() {
        return new SlotRange(PlayerInventory.getHotbarSize(), PlayerInventory.MAIN_SIZE);
    }

    public static SlotRange horseMainRange(Inventory inventory) {
        return new SlotRange(2, inventory.size());
    }
}