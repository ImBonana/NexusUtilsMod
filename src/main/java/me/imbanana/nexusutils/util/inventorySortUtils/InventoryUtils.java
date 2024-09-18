package me.imbanana.nexusutils.util.inventorySortUtils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

import java.util.function.BiFunction;

public class InventoryUtils {
    public static void transferEntireInventory(
            Inventory from,
            Inventory to,
            SlotRange fromRange,
            SlotRange toRange,
            BiFunction<ItemStack, ItemStack, Boolean> predicate,
            PlayerEntity player) {
        transferEntireInventory(from, to, fromRange, toRange, predicate, null, null, player);
    }

    public static void transferEntireInventory(
            Inventory from,
            Inventory to,
            SlotRange fromRange,
            SlotRange toRange,
            ScreenHandler fromScreenHandler,
            ScreenHandler toScreenHandler,
            PlayerEntity player) {
        transferEntireInventory(from,
                to,
                fromRange,
                toRange,
                (fromStack, toStack) -> true,
                fromScreenHandler,
                toScreenHandler,
                player);
    }

    public static void transferEntireInventory(
            Inventory from,
            Inventory to,
            SlotRange fromRange,
            SlotRange toRange,
            BiFunction<ItemStack, ItemStack, Boolean> predicate,
            ScreenHandler fromScreenHandler,
            ScreenHandler toScreenHandler,
            PlayerEntity player) {
        for (int toIdx = toRange.getMin(); toIdx < toRange.getMax(); toIdx++) {
            for (int fromIdx = fromRange.getMin(); fromIdx < fromRange.getMax(); fromIdx++) {
                ItemStack fromStack = from.getStack(fromIdx).copy();
                ItemStack toStack = to.getStack(toIdx).copy();

                if (fromStack.isEmpty()) {
                    continue;
                }

                if (!predicate.apply(fromStack, toStack)) {
                    continue;
                }

                if (!canTakeItemFromSlot(fromScreenHandler, fromIdx, player)) {
                    continue;
                }

                if (!canPlaceItemInSlot(toScreenHandler, toIdx, fromStack)) {
                    continue;
                }

                if (areItemStacksMergeable(toStack, fromStack)) {
                    int space = toStack.getMaxCount() - toStack.getCount();
                    int amount = Math.min(space, fromStack.getCount());
                    if (amount > 0) {
                        toStack.increment(amount);
                        fromStack.decrement(amount);

                        to.setStack(toIdx, toStack);
                        from.setStack(fromIdx, fromStack.isEmpty() ? ItemStack.EMPTY : fromStack);
                    }

                    from.markDirty();
                    to.markDirty();
                } else if (toStack.isEmpty() && !fromStack.isEmpty()) {
                    to.setStack(toIdx, fromStack);
                    from.setStack(fromIdx, ItemStack.EMPTY);

                    from.markDirty();
                    to.markDirty();
                }
            }
        }
    }

    public static boolean canTakeItemFromSlot(
            ScreenHandler screenHandler, int idx, PlayerEntity player) {
        if (screenHandler == null) {
            return true;
        }
        try {
            return screenHandler.getSlot(idx).canTakeItems(player);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public static boolean canPlaceItemInSlot(
            ScreenHandler screenHandler, int idx, ItemStack itemStack) {
        if (screenHandler == null) {
            return true;
        }
        try {
            return screenHandler.getSlot(idx).canInsert(itemStack);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public static Inventory getContainerInventory(PlayerEntity player) {
        ScreenHandler currentScreenHandler = player.currentScreenHandler;
        if (currentScreenHandler == null) {
            return null;
        }

        try {
            return currentScreenHandler.getSlot(0).inventory;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static boolean areItemStacksMergeable(ItemStack a, ItemStack b) {
        return !a.isEmpty() && !b.isEmpty() && ItemStack.areItemsAndComponentsEqual(a, b);
    }
}
