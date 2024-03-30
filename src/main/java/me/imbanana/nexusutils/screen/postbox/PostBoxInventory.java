package me.imbanana.nexusutils.screen.postbox;

import me.imbanana.nexusutils.util.ISimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class PostBoxInventory implements ISimpleInventory {
    private final DefaultedList<ItemStack> inventory;

    public PostBoxInventory() {
        this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
}
