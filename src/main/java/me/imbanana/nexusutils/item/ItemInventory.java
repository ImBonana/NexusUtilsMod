package me.imbanana.nexusutils.item;

import me.imbanana.nexusutils.item.custom.HopperFilterItem;
import me.imbanana.nexusutils.util.ISimpleInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;

public class ItemInventory implements ISimpleInventory {
    private final DefaultedList<ItemStack> inventory;

    protected final ItemStack item;

    protected boolean canUse = true;

    public ItemInventory(ItemStack item, int inventorySize) {
        this.inventory = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
        this.item = item;

       this.init();
    }

    protected void init() {
        NbtCompound itemNbt = this.item.getOrCreateNbt();
        Inventories.readNbt(itemNbt, this.inventory);
    }

    @Override
    public void markDirty() {
        NbtCompound nbt = this.item.getOrCreateNbt();
        Inventories.writeNbt(nbt, this.inventory);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return canUse;
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if(this.item.isEmpty()) {
            this.canUse = false;
            return;
        }
        ISimpleInventory.super.onOpen(player);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public ItemStack getStack(int slot) {
        if(this.item.isEmpty()) {
            this.canUse = false;
            return ItemStack.EMPTY;
        }
        return ISimpleInventory.super.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int count) {
        if(this.item.isEmpty()) {
            this.canUse = false;
            return ItemStack.EMPTY;
        }
        return ISimpleInventory.super.removeStack(slot, count);
    }

    @Override
    public ItemStack removeStack(int slot) {
        if(this.item.isEmpty()) {
            this.canUse = false;
            return ItemStack.EMPTY;
        }
        return ISimpleInventory.super.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if(this.item.isEmpty()) {
            this.canUse = false;
            return;
        }
        ISimpleInventory.super.setStack(slot, stack);
    }

    @Override
    public void clear() {
        if(this.item.isEmpty()) {
            this.canUse = false;
            return;
        }
        ISimpleInventory.super.clear();
    }
}
