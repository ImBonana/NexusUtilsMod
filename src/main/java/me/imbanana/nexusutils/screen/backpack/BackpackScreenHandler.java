package me.imbanana.nexusutils.screen.backpack;

import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import me.imbanana.nexusutils.fluids.ModFluids;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.item.custom.SleepingBagItem;
import me.imbanana.nexusutils.networking.packets.screens.ItemInventoryOpeningData;
import me.imbanana.nexusutils.screen.ModScreenHandlers;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BackpackScreenHandler extends ScreenHandler {
    private final BackpackInventory inventory;
    private final BackpackTierComponent.Tier tier;

    public BackpackScreenHandler(int syncId, PlayerInventory inventory, ItemInventoryOpeningData data) {
        this(syncId, inventory, data.stack());
    }

    public BackpackScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack itemStack) {
        super(ModScreenHandlers.BACKPACK_SCREEN_HANDLER, syncId);

        this.tier = itemStack.getOrDefault(ModComponents.BACKPACK_TIER, BackpackTierComponent.createDefaultBackpackTier()).tier();
        this.inventory = new BackpackInventory(itemStack);
        this.inventory.onOpen(playerInventory.player);

        int slotSize = 18;
        int yOffset = (this.tier.asNumber() - 1) * slotSize;
        boolean isTierZero = this.tier == BackpackTierComponent.Tier.TIER_0;

        this.addSlot(new BackpackBucketInputSlot(this.inventory, UtilSlots.LEFT_TANK_INPUT.getId(), 6, (isTierZero ? 11 : 22 + yOffset)));
        this.addSlot(new BackpackBucketOutputSlot(this.inventory, UtilSlots.LEFT_TANK_OUTPUT.getId(), 6, (isTierZero ? 39 : 50 + yOffset)));

        this.addSlot(new BackpackBucketInputSlot(this.inventory, UtilSlots.RIGHT_TANK_INPUT.getId(), 234, (isTierZero ? 11 : 22 + yOffset)));
        this.addSlot(new BackpackBucketOutputSlot(this.inventory, UtilSlots.RIGHT_TANK_OUTPUT.getId(), 234, (isTierZero ? 39 : 50 + yOffset)));

        this.addSlot(new BackpackSleepingBagSlot(this.inventory, UtilSlots.SLEEPING_BAG.getId(), 25, 73 + (yOffset)));

        for (int i = 0; i < this.inventory.size() / 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(this.inventory, (i * 9) + j + BackpackInventory.INVENTORY_OFFSET, (j * 18) + 48, (i * 18 + 18)){
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return !stack.isOf(ModItems.BACKPACK);
                    }
                });
            }
        }

        addPlayerInventory(playerInventory, 66 + (yOffset + slotSize));
        addPlayerHotbar(playerInventory, 124 + (yOffset + slotSize));
    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if(originalStack.getItem() instanceof SleepingBagItem) {
                if (!this.insertItem(originalStack, UtilSlots.SLEEPING_BAG.getId(), UtilSlots.SLEEPING_BAG.getId() + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if(originalStack.getItem() instanceof BucketItem bucketItem) {
                Fluid fluid =  bucketItem.nexusutils$getFluid();
                if(fluid != Fluids.EMPTY) {
                    if(this.getLeftTank().isResourceBlank() || (this.getLeftTank().variant.getFluid() == fluid && this.getLeftTank().getAmount() < this.getLeftTank().getCapacity())) {
                        if (!this.insertItem(originalStack, UtilSlots.LEFT_TANK_INPUT.getId(), UtilSlots.LEFT_TANK_INPUT.getId() + 1, true)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (this.getRightTank().isResourceBlank() || this.getRightTank().variant.getFluid() == fluid) {
                        if (!this.insertItem(originalStack, UtilSlots.RIGHT_TANK_INPUT.getId(), UtilSlots.RIGHT_TANK_INPUT.getId() + 1, true)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }

            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size() - BackpackInventory.INVENTORY_OFFSET, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, BackpackInventory.INVENTORY_OFFSET, this.inventory.size(), false)) {
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

    public FluidTank getLeftTank() {
        return this.inventory.getLeftTank();
    }

    public FluidTank getRightTank() {
        return this.inventory.getRightTank();
    }

    private void addPlayerInventory(PlayerInventory playerInventory, int yOffset) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 48 + l * 18, i * 18 + yOffset));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory, int yOffset) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 48 + i * 18, yOffset));
        }
    }

    public BackpackInventory getInventory() {
        return this.inventory;
    }

    private void updateFluidSlots() {
        tryToTransferFluid(this.getSlot(UtilSlots.LEFT_TANK_INPUT.getId()), this.getSlot(UtilSlots.LEFT_TANK_OUTPUT.getId()), this.getLeftTank());
        tryToTransferFluid(this.getSlot(UtilSlots.RIGHT_TANK_INPUT.getId()), this.getSlot(UtilSlots.RIGHT_TANK_OUTPUT.getId()), this.getRightTank());

        if(this.getLeftTank().amount <= 0) {
            this.getLeftTank().variant = FluidVariant.of(Fluids.EMPTY);
        }

        if(this.getRightTank().amount <= 0) {
            this.getRightTank().variant = FluidVariant.of(Fluids.EMPTY);
        }
    }

    private void tryToTransferFluid(Slot inputSlot, Slot outputSlot, FluidTank fluidTank) {
        if(inputSlot.hasStack() && inputSlot.canInsert(inputSlot.getStack())) {
            if(inputSlot.getStack().getItem() instanceof BucketItem bucketItem) {
                Fluid fluid = bucketItem.nexusutils$getFluid();

                if(fluid == Fluids.EMPTY && !outputSlot.hasStack() && !fluidTank.isResourceBlank() && fluidTank.getAmount() >= 1000) {
                    inputSlot.getStack().decrement(1);

                    fluidTank.amount -= 1000;

                    ItemStack outputStack = fluidTank.variant.getFluid().getBucketItem().getDefaultStack();
                    outputSlot.setStack(outputStack);
                } else {
                    transferFluid(inputSlot, outputSlot, fluidTank, fluid);
                }
            } else if (inputSlot.getStack().getItem() instanceof MilkBucketItem) {
                transferFluid(inputSlot, outputSlot, fluidTank, ModFluids.MILK);
            }
        }
    }

    private void transferFluid(Slot inputSlot, Slot outputSlot, FluidTank fluidTank, Fluid fluid) {
        FluidVariant fluidVariant = FluidVariant.of(fluid);

        ItemStack outputItem = new ItemStack(Items.BUCKET);

        long fluidAmount = 1000;

        if(
                fluid != Fluids.EMPTY
                && fluidTank.getAmount() <= (fluidTank.getCapacity() - fluidAmount)
                && (fluidVariant.equals(fluidTank.variant) || fluidTank.variant.isBlank())
                && outputItem.getCount() < outputSlot.getMaxItemCount(outputItem)
                && (!outputSlot.hasStack() || (outputSlot.hasStack() && ItemStack.areItemsAndComponentsEqual(outputSlot.getStack(), outputItem)))
        ) {
            inputSlot.getStack().decrement(1);
            if (fluidTank.variant.isBlank()) {
                fluidTank.variant = fluidVariant;
                fluidTank.amount = fluidAmount;
            } else {
                fluidTank.amount += fluidAmount;
            }

            if(outputSlot.hasStack()) {
                outputSlot.getStack().increment(1);
            } else {
                outputSlot.setStack(outputItem.copy());
            }
        }
    }

    public BackpackTierComponent.Tier getTier() {
        return this.tier;
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

    public enum UtilSlots {
        LEFT_TANK_INPUT(0),
        LEFT_TANK_OUTPUT(1),
        RIGHT_TANK_INPUT(2),
        RIGHT_TANK_OUTPUT(3),
        SLEEPING_BAG(4);

        private final int index;

        UtilSlots(int index) {
            this.index = index;
        }

        public int getId() {
            return this.index;
        }
    }
}
