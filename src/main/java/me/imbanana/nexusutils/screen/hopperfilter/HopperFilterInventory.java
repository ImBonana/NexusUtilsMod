package me.imbanana.nexusutils.screen.hopperfilter;

import me.imbanana.nexusutils.block.entity.ImplementedInventory;
import me.imbanana.nexusutils.item.custom.HopperFilterItem;
import me.imbanana.nexusutils.util.ISimpleInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public class HopperFilterInventory implements ISimpleInventory {
    private final ItemStack hopperFilter;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(HopperFilterItem.INVENTORY_SIZE, ItemStack.EMPTY);

    private boolean canUse = true;

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
        return canUse;
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if(hopperFilter.isEmpty()) {
            this.canUse = false;
            return;
        }
        ISimpleInventory.super.onOpen(player);
    }

    @Override
    public ItemStack getStack(int slot) {
        if(hopperFilter.isEmpty()) {
            this.canUse = false;
            return ItemStack.EMPTY;
        }
        return ISimpleInventory.super.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int count) {
        if(hopperFilter.isEmpty()) {
            this.canUse = false;
            return ItemStack.EMPTY;
        }
        return ISimpleInventory.super.removeStack(slot, count);
    }

    @Override
    public ItemStack removeStack(int slot) {
        if(hopperFilter.isEmpty()) {
            this.canUse = false;
            return ItemStack.EMPTY;
        }
        return ISimpleInventory.super.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if(hopperFilter.isEmpty()) {
            this.canUse = false;
            return;
        }
        ISimpleInventory.super.setStack(slot, stack);
    }

    @Override
    public void clear() {
        if(hopperFilter.isEmpty()) {
            this.canUse = false;
            return;
        }
        ISimpleInventory.super.clear();
    }
}
