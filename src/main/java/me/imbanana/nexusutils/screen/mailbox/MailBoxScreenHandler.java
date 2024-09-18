package me.imbanana.nexusutils.screen.mailbox;

import me.imbanana.nexusutils.networking.packets.screens.BlockEntityScreenOpeningData;
import me.imbanana.nexusutils.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class MailBoxScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public MailBoxScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntityScreenOpeningData data) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(data.pos()));
    }

    public MailBoxScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity) {
        super(ModScreenHandlers.MAIL_BOX_SCREEN_HANDLER, syncId);

        checkSize((Inventory) blockEntity, 9);
        this.inventory = (Inventory) blockEntity;
        inventory.onOpen(playerInventory.player);

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, (i * 18) + 8, 18) {
                @Override
                public boolean canInsert(ItemStack stack) {
                    return false;
                }
            });
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
    public void onClosed(PlayerEntity player) {
        this.inventory.onClose(player);
        super.onClosed(player);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, i * 18 + 51));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 109));
        }
    }
}
