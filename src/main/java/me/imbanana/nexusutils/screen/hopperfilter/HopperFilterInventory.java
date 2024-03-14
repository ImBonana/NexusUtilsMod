package me.imbanana.nexusutils.screen.hopperfilter;

import me.imbanana.nexusutils.block.entity.ImplementedInventory;
import me.imbanana.nexusutils.item.ItemInventory;
import me.imbanana.nexusutils.item.custom.HopperFilterItem;
import me.imbanana.nexusutils.util.ISimpleInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public class HopperFilterInventory extends ItemInventory {

    public HopperFilterInventory(ItemStack hopperFilter) {
        super(hopperFilter, HopperFilterItem.INVENTORY_SIZE);
    }
}
