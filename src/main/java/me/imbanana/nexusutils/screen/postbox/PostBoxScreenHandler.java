package me.imbanana.nexusutils.screen.postbox;

import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.networking.ModNetwork;
import me.imbanana.nexusutils.networking.packets.mail.UpdateMailBoxesPacket;
import me.imbanana.nexusutils.screen.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class PostBoxScreenHandler extends ScreenHandler {

    private final ScreenHandlerContext context;
    private final PostBoxInventory inventory;

    public PostBoxScreenHandler(int syncId, PlayerInventory playerInventory, Object data) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public PostBoxScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.POST_BOX_SCREEN_HANDLER, syncId);

        this.inventory = new PostBoxInventory();
        this.context = context;

        inventory.onOpen(playerInventory.player);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new Slot(this.inventory, (i * 3) + j, (j * 18) + 242, (i * 18) + 18));
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

    public PostBoxInventory getInventory() {
        return inventory;
    }

    public void updateMailBoxes() {
        ModNetwork.NETWORK_CHANNEL.clientHandle().send(UpdateMailBoxesPacket.EMPTY);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, blockPos) -> this.dropInventory(player, this.inventory));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return PostBoxScreenHandler.canUse(this.context, player, ModBlocks.POST_BOX);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 137 + l * 18, i * 18 + 87));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 137 + i * 18, 145));
        }
    }
}
