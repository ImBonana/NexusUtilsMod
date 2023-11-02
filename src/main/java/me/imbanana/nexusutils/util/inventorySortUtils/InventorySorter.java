package me.imbanana.nexusutils.util.inventorySortUtils;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.util.IExtendedShulkerBoxScreenHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.*;
import java.util.function.Predicate;

public class InventorySorter {
    private InventorySorter() { } // ignore

    public static void sortCurrentlyOpenInventory(ServerPlayerEntity player) {
        try {
            ScreenHandler screenHandler = player.currentScreenHandler;
            if(screenHandler == null){
                NexusUtils.LOGGER.error("Sorting failed, because screenHandler is null.");
                return;
            }
            if (screenHandler instanceof GenericContainerScreenHandler genericContainerScreenHandler) {
                if (canPlayerUse(player, screenHandler)) return;

                Inventory containerInventory = genericContainerScreenHandler.getInventory();
                InventorySorter.sortInventory(containerInventory);
                containerInventory.markDirty();
            } else if (screenHandler instanceof IExtendedShulkerBoxScreenHandler shulkerBoxScreenHandler) {
                if (canPlayerUse(player, screenHandler)) return;

                Inventory containerInventory = shulkerBoxScreenHandler.getInventory();
                InventorySorter.sortInventory(containerInventory);
                containerInventory.markDirty();

            } else {
                String currentScreenHandlerReturnType = screenHandler.getClass().getName();
                NexusUtils.LOGGER.warn("player.currentScreenHandler returned {}, which does not work with Simple Sorting.", currentScreenHandlerReturnType);
            }
        } catch (Exception e) {
            NexusUtils.LOGGER.error("Sorting failed, because of this exception: {}", e.getMessage());
        }
    }

    public static void sortPlayerInventory(ServerPlayerEntity playerEntity) {
        PlayerInventory playerInventory = playerEntity.getInventory();
        Inventory inventory = new SimpleInventory(3*9);
        for (int i = 0; i < inventory.size(); i++) {
            inventory.setStack(i, playerInventory.getStack(i + 9));
        }

        sortInventory(inventory);

        for (int i = 0; i < inventory.size(); i++) {
            playerInventory.setStack(i + 9, inventory.getStack(i));
        }
    }

    private static boolean canPlayerUse(ServerPlayerEntity player, ScreenHandler screenHandler) {
        if (!screenHandler.canUse(player)) {
            NexusUtils.LOGGER.info("Failed to sort, because player cannot use the container anymore.");
            return true;
        }
        return false;
    }

    public static void sortInventory(Inventory inventory) {
        Map<Integer, ItemStack> inventoryMap = convertInventoryToMap(inventory);
        List<Map.Entry<Integer, ItemStack>> entriesWithValueSorted = inventoryMap.entrySet().stream().sorted((entry, entry2) -> {
            int itemId = Item.getRawId(entry.getValue().getItem());
            int itemId2 = Item.getRawId(entry2.getValue().getItem());

            return Integer.compare(itemId, itemId2);
        }).toList();

        Map<Integer, ItemStack> sortedInventoryMap = sortInventory(entriesWithValueSorted);
        inventory.clear();
        int slot = 0;
        for (Map.Entry<Integer, ItemStack> entry : sortedInventoryMap.entrySet()) {
            inventory.setStack(slot, entry.getValue());
            slot++;
        }
    }

    private static Map<Integer, ItemStack> convertInventoryToMap(Inventory inventory) {
        Map<Integer, ItemStack> inventoryMap = new HashMap<>(inventory.size());
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            inventoryMap.put(i, stack);
        }
        return inventoryMap;
    }

    private static Map<Integer, ItemStack> sortInventory(List<Map.Entry<Integer, ItemStack>> entriesWithValueSorted) {
        Map<Integer, ItemStack> sortedInventoryMap = new HashMap<>();
        int nextSpot = 1;
        ArrayList<Integer> entryKeysMarkedForSkipping = new ArrayList<>();
        for (var entry : entriesWithValueSorted) {
            if (entryKeysMarkedForSkipping.contains(entry.getKey())) continue;
            ItemStack stack = entry.getValue();
            if (itemStacksOfTheSameItemExists(entriesWithValueSorted, stack.getItem()) && stack.getItem().getMaxCount() != 1) {
                List<Map.Entry<Integer, ItemStack>> stacksOfTheSameItem = entriesWithValueSorted
                        .stream()
                        .filter(entryValueHasTheSameItemId(stack))
                        .toList();

                Collection<ItemStack> combinedStacks = combineStacksOfTheSameItem(stacksOfTheSameItem);
                stacksOfTheSameItem.forEach(x -> entryKeysMarkedForSkipping.add(x.getKey()));

                for (ItemStack combinedStack : combinedStacks) {
                    sortedInventoryMap.put(nextSpot, combinedStack);
                    nextSpot++;
                }
            } else {
                sortedInventoryMap.putIfAbsent(nextSpot, entry.getValue());
            }
            nextSpot++;
        }
        return sortedInventoryMap;
    }

    private static boolean itemStacksOfTheSameItemExists(List<Map.Entry<Integer, ItemStack>> entriesWithValueSorted, Item item) {
        int count = entriesWithValueSorted.stream().filter(entry -> entry.getValue().getItem().equals(item)).toList().size();
        return count > 1;
    }

    private static Predicate<Map.Entry<Integer, ItemStack>> entryValueHasTheSameItemId(ItemStack stack) {
        return anyEntry -> Item.getRawId(anyEntry.getValue().getItem()) == Item.getRawId(stack.getItem());
    }

    private static Collection<ItemStack> combineStacksOfTheSameItem(List<Map.Entry<Integer, ItemStack>> stacksOfTheSameItem) {
        Item item = stacksOfTheSameItem.get(1).getValue().getItem();
        ItemStack nonFullStack = new ItemStack(item, 0);
        Collection<ItemStack> combinedStacks = new ArrayList<>();
        for (Map.Entry<Integer, ItemStack> entry : stacksOfTheSameItem) {
            if(isRenamedItem(entry)){
                combinedStacks.add(entry.getValue());
                continue;
            }
            int entryItemAmount = entry.getValue().getCount();
            int newAmount = nonFullStack.getCount() + entryItemAmount;
            if (newAmount >= item.getMaxCount()) {
                ItemStack fullStack = new ItemStack(item, item.getMaxCount());
                combinedStacks.add(fullStack);
                newAmount -= item.getMaxCount();
            }
            nonFullStack.setCount(newAmount);
        }
        if (nonFullStack.getCount() > 0) combinedStacks.add(nonFullStack);
        return combinedStacks;
    }

    private static boolean isRenamedItem(Map.Entry<Integer, ItemStack> entry) {
        String defaultName = entry.getValue().getItem().getDefaultStack().getName().getString();
        String entryName = entry.getValue().getName().getString();
        return !entryName.equals(defaultName);
    }
}
