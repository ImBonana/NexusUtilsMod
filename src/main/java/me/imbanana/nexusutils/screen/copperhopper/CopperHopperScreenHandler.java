package me.imbanana.nexusutils.screen.copperhopper;

import me.imbanana.nexusutils.block.entity.CopperHopperBlockEntity;
import me.imbanana.nexusutils.networking.ModNetwork;
import me.imbanana.nexusutils.networking.packets.UpdateCopperHopperFilterModePacket;
import me.imbanana.nexusutils.networking.packets.screens.BlockEntityScreenOpeningData;
import me.imbanana.nexusutils.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class CopperHopperScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final CopperHopperBlockEntity blockEntity;

    public CopperHopperScreenHandler(int syncId, PlayerInventory inventory, BlockEntityScreenOpeningData data) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(data.pos()), new ArrayPropertyDelegate(1));
    }

    public CopperHopperScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate) {
        super(ModScreenHandlers.COPPER_HOPPER_SCREEN_HANDLER, syncId);
        checkSize((Inventory) blockEntity, 6);
        this.inventory = (Inventory) blockEntity;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = (CopperHopperBlockEntity) blockEntity;

        this.addSlot(new FilterSlot(inventory, 0, 16, 20));

        for (int j = 0; j < 5; ++j) {
            this.addSlot(new Slot(inventory, j+1, 44 + j * 18, 20));
        }

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(arrayPropertyDelegate);

    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isWhitelist() {
        return this.propertyDelegate.get(0) == 1;
    }
    public void toggleWhitelist() {
        this.propertyDelegate.set(0, isWhitelist() ? 0 : 1);
        ModNetwork.NETWORK_CHANNEL.clientHandle().send(new UpdateCopperHopperFilterModePacket(this.blockEntity.getPos(), this.propertyDelegate.get(0) == 1));
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
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, i * 18 + 51));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 109));
        }
    }

    private static class FilterSlot extends Slot {

        public FilterSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxItemCount(ItemStack stack) {
            return 1;
        }

        @Override
        public int getMaxItemCount() {
            return 1;
        }
    }
}