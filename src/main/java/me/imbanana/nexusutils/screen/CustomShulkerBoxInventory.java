package me.imbanana.nexusutils.screen;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

public class CustomShulkerBoxInventory implements NamedScreenHandlerFactory, Inventory {
    private final DefaultedList<ItemStack> inventory;
    private final Text displayName;
    private final ItemStack itemStack;
    private boolean canUse = true;

    public CustomShulkerBoxInventory(DefaultedList<ItemStack> inventory, Text displayName, ItemStack itemStack) {
        this.inventory = inventory;
        this.displayName = displayName;
        this.itemStack = itemStack;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ShulkerBoxScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public Text getDisplayName() {
        return this.displayName;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        if(itemStack.isEmpty()) {
            this.canUse = false;
            return ItemStack.EMPTY;
        }
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if(itemStack.isEmpty()) {
            this.canUse = false;
            return ItemStack.EMPTY;
        }
        ItemStack result = Inventories.splitStack(this.inventory, slot, amount);
        if (!result.isEmpty()) {
            markDirty();
        }

        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        if(itemStack.isEmpty()) {
            this.canUse = false;
            return ItemStack.EMPTY;
        }
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if(itemStack.isEmpty()) {
            this.canUse = false;
            return;
        }
        this.inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
    }

    @Override
    public void markDirty() {
        NexusUtils.LOGGER.info(this.itemStack.toString());
        NbtCompound nbt = this.itemStack.getOrCreateNbt();
        if (nbt == null) return;
        NbtCompound blockEntityTag = nbt.getCompound("BlockEntityTag");
        if (blockEntityTag == null) return;

        NbtCompound writeNbtList = Inventories.writeNbt(blockEntityTag, this.inventory, true);
        nbt.put("BlockEntityTag", writeNbtList);
        this.itemStack.setNbt(nbt);
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if(itemStack.isEmpty()) {
            this.canUse = false;
            return;
        }
        NbtCompound nbt = this.itemStack.getOrCreateNbt();
        nbt.putBoolean("disabled", true);
        Inventory.super.onOpen(player);
    }

    @Override
    public void onClose(PlayerEntity player) {
        NbtCompound nbt = this.itemStack.getOrCreateNbt();
        nbt.putBoolean("disabled", true);
        Inventory.super.onClose(player);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return this.canUse;
    }

    @Override
    public void clear() {
        if(itemStack.isEmpty()) {
            this.canUse = false;
            return;
        }
        this.inventory.clear();
    }
}
