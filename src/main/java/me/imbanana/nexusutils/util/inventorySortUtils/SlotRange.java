package me.imbanana.nexusutils.util.inventorySortUtils;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;

public class SlotRange {
    public int min;
    public int max;

    public SlotRange(int min, int max) {
        this.min = min;
        this.max = max;
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