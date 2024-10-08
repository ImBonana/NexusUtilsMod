package me.imbanana.nexusutils.item;

import me.imbanana.nexusutils.util.ISimpleInventory;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class ItemInventory implements ISimpleInventory {
    private final DefaultedList<ItemStack> inventory;

    protected final ItemStack stack;

    protected boolean canUse = true;

    public ItemInventory(ItemStack stack, int inventorySize) {
        this.inventory = DefaultedList.ofSize(inventorySize, ItemStack.EMPTY);
        this.stack = stack;

       this.init();
    }

    protected void init() {
        ContainerComponent containerComponent = this.stack.get(DataComponentTypes.CONTAINER);
        if(containerComponent == null) return;
        containerComponent.copyTo(this.inventory);
    }

    @Override
    public void markDirty() {
        this.stack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(this.inventory));
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return canUse;
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if(this.stack.isEmpty()) {
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
        if(this.stack.isEmpty()) {
            this.canUse = false;
            return ItemStack.EMPTY;
        }
        return ISimpleInventory.super.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int count) {
        if(this.stack.isEmpty()) {
            this.canUse = false;
            return ItemStack.EMPTY;
        }
        return ISimpleInventory.super.removeStack(slot, count);
    }

    @Override
    public ItemStack removeStack(int slot) {
        if(this.stack.isEmpty()) {
            this.canUse = false;
            return ItemStack.EMPTY;
        }
        return ISimpleInventory.super.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if(this.stack.isEmpty()) {
            this.canUse = false;
            return;
        }
        ISimpleInventory.super.setStack(slot, stack);
    }

    @Override
    public void clear() {
        if(this.stack.isEmpty()) {
            this.canUse = false;
            return;
        }
        ISimpleInventory.super.clear();
    }
}
