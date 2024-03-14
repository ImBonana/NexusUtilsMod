package me.imbanana.nexusutils.screen.hopperfilter;

import me.imbanana.nexusutils.item.ItemInventory;
import me.imbanana.nexusutils.item.custom.HopperFilterItem;
import net.minecraft.item.ItemStack;

public class HopperFilterInventory extends ItemInventory {

    public HopperFilterInventory(ItemStack hopperFilter) {
        super(hopperFilter, HopperFilterItem.INVENTORY_SIZE);
    }
}
