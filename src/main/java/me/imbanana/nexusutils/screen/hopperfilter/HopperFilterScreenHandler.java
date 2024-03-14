package me.imbanana.nexusutils.screen.hopperfilter;

import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.screen.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class HopperFilterScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public HopperFilterScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, buf.readItemStack());
    }

    public HopperFilterScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack itemStack) {
        super(ModScreenHandlers.HOPPER_FILTER_SCREEN_HANDLER, syncId);

        this.inventory = new HopperFilterInventory(itemStack);
        inventory.onOpen(playerInventory.player);

        for(int i = 0; i < 2; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new HopperFilterSlot(inventory, j + i * 9, 8 + j * 18, i * 18 + 18));
            }
        }

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }


    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, i * 18 + 66));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 124));
        }
    }

    private static class HopperFilterSlot extends Slot {

        public HopperFilterSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxItemCount() {
            return 1;
        }

        @Override
        public int getMaxItemCount(ItemStack stack) {
            return 1;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.getItem() != ModItems.HOPPER_FILTER;
        }
    }
}