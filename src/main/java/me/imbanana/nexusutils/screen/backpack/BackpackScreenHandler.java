package me.imbanana.nexusutils.screen.backpack;

import me.imbanana.nexusutils.fluids.ModFluids;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import me.imbanana.nexusutils.item.custom.SleepingBagItem;
import me.imbanana.nexusutils.screen.ModScreenHandlers;
import me.imbanana.nexusutils.util.accessors.IBucketItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BackpackScreenHandler extends ScreenHandler {
    private final BackpackInventory inventory;

    private final int LEFT_TANK_INPUT_SLOT_ID = BackpackItem.INVENTORY_SIZE;
    private final int LEFT_TANK_OUTPUT_SLOT_ID = BackpackItem.INVENTORY_SIZE + 1;

    private final int RIGHT_TANK_INPUT_SLOT_ID = BackpackItem.INVENTORY_SIZE + 2;
    private final int RIGHT_TANK_OUTPUT_SLOT_ID = BackpackItem.INVENTORY_SIZE + 3;

    public static final int SLEEPING_BAG_SLOT_ID = BackpackItem.INVENTORY_SIZE + 4;

    public BackpackScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, buf.readItemStack());
    }

    public BackpackScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack itemStack) {
        super(ModScreenHandlers.BACKPACK_SCREEN_SCREEN_HANDLER, syncId);

        this.inventory = new BackpackInventory(itemStack);
        this.inventory.onOpen(playerInventory.player);

        for (int i = 0; i < BackpackItem.INVENTORY_SIZE / 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(this.inventory, (i * 9) + j, (j * 18) + 48, (i * 18 + 18)){
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return !stack.isOf(ModItems.BACKPACK);
                    }
                });
            }
        }

        this.addSlot(new BackpackBucketInputSlot(this.inventory, LEFT_TANK_INPUT_SLOT_ID, 6, 22));
        this.addSlot(new BackpackBucketOutputSlot(this.inventory, LEFT_TANK_OUTPUT_SLOT_ID, 6, 50));

        this.addSlot(new BackpackBucketInputSlot(this.inventory, RIGHT_TANK_INPUT_SLOT_ID, 234, 22));
        this.addSlot(new BackpackBucketOutputSlot(this.inventory, RIGHT_TANK_OUTPUT_SLOT_ID, 234, 50));

        this.addSlot(new BackpackSleepingBagSlot(this.inventory, SLEEPING_BAG_SLOT_ID, 25, 73));

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

    public SingleVariantStorage<FluidVariant> getLeftTank() {
        return this.inventory.getLeftTank();
    }

    public SingleVariantStorage<FluidVariant> getRightTank() {
        return this.inventory.getRightTank();
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 48 + l * 18, i * 18 + 84));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 48 + i * 18, 142));
        }
    }

    public BackpackInventory getInventory() {
        return this.inventory;
    }

    private void updateFluidSlots() {
        tryToTransferFluid(this.getSlot(LEFT_TANK_INPUT_SLOT_ID), this.getSlot(LEFT_TANK_OUTPUT_SLOT_ID), this.getLeftTank());
        tryToTransferFluid(this.getSlot(RIGHT_TANK_INPUT_SLOT_ID), this.getSlot(RIGHT_TANK_OUTPUT_SLOT_ID), this.getRightTank());

        if(this.getLeftTank().amount <= 0) {
            this.getLeftTank().variant = FluidVariant.of(Fluids.EMPTY);
        }

        if(this.getRightTank().amount <= 0) {
            this.getRightTank().variant = FluidVariant.of(Fluids.EMPTY);
        }
    }

    private void tryToTransferFluid(Slot inputSlot, Slot outputSlot, SingleVariantStorage<FluidVariant> fluidStorage) {
        if(inputSlot.hasStack() && inputSlot.canInsert(inputSlot.getStack())) {
            if(inputSlot.getStack().getItem() instanceof BucketItem) {
                IBucketItem bucketItem = (IBucketItem) inputSlot.getStack().getItem();
                Fluid fluid = bucketItem.nexusutils$getFluid();

                if(fluid == Fluids.EMPTY && !outputSlot.hasStack() && !fluidStorage.isResourceBlank() && fluidStorage.getAmount() >= 1000) {
                    inputSlot.getStack().decrement(1);

                    fluidStorage.amount -= 1000;

                    ItemStack outputStack = fluidStorage.variant.getFluid().getBucketItem().getDefaultStack();
                    outputSlot.setStack(outputStack);
                } else {
                    transferFluid(inputSlot, outputSlot, fluidStorage, fluid);
                }
            } else if (inputSlot.getStack().getItem() instanceof MilkBucketItem) {
                transferFluid(inputSlot, outputSlot, fluidStorage, ModFluids.MILK);
            }
        }
    }

    private void transferFluid(Slot inputSlot, Slot outputSlot, SingleVariantStorage<FluidVariant> fluidStorage, Fluid fluid) {
        FluidVariant fluidVariant = FluidVariant.of(fluid);

        ItemStack outputItem = new ItemStack(Items.BUCKET);

        long fluidAmount = 1000;

        if(
                fluid != Fluids.EMPTY
                && fluidStorage.getAmount() <= (fluidStorage.getCapacity() - fluidAmount)
                && (fluidVariant.equals(fluidStorage.variant) || fluidStorage.variant.isBlank())
                && outputItem.getCount() < outputSlot.getMaxItemCount(outputItem)
                && (!outputSlot.hasStack() || (outputSlot.hasStack() && ItemStack.canCombine(outputSlot.getStack(), outputItem)))
        ) {

            inputSlot.getStack().decrement(1);
            if (fluidStorage.variant.isBlank()) {
                fluidStorage.variant = fluidVariant;
                fluidStorage.amount = fluidAmount;
            } else {
                fluidStorage.amount += fluidAmount;
            }

            if(outputSlot.hasStack()) {
                outputSlot.getStack().increment(1);
            } else {
                outputSlot.setStack(outputItem.copy());
            }
        }
    }

    public class BackpackBucketInputSlot extends Slot {

        public BackpackBucketInputSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxItemCount() {
            return 1;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return (stack.getItem() instanceof BucketItem && !(stack.getItem() instanceof EntityBucketItem)) || stack.getItem() instanceof MilkBucketItem;
        }

        @Override
        public void markDirty() {
            super.markDirty();
            BackpackScreenHandler.this.updateFluidSlots();
        }
    }

    public class BackpackBucketOutputSlot extends Slot {

        public BackpackBucketOutputSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }

        @Override
        public void markDirty() {
            super.markDirty();
            BackpackScreenHandler.this.updateFluidSlots();
        }
    }

    public static class BackpackSleepingBagSlot extends Slot {

        public BackpackSleepingBagSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.getItem() instanceof SleepingBagItem;
        }
    }
}
