package me.imbanana.nexusutils.screen.hopperfilter;

import me.imbanana.nexusutils.block.entity.ImplementedInventory;
import me.imbanana.nexusutils.item.custom.HopperFilterItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public class HopperFilterInventory implements ImplementedInventory {
    private final ItemStack hopperFilter;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(HopperFilterItem.INVENTORY_SIZE, ItemStack.EMPTY);

    public HopperFilterInventory(ItemStack hopperFilter) {
        this.hopperFilter = hopperFilter;

        NbtCompound itemNbt = this.hopperFilter.getOrCreateNbt();
        Inventories.readNbt(itemNbt, this.inventory);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public void markDirty() {
        NbtCompound nbt = this.hopperFilter.getOrCreateNbt();
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}
