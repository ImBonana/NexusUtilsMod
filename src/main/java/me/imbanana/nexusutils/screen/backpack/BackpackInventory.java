package me.imbanana.nexusutils.screen.backpack;

import me.imbanana.nexusutils.item.ItemInventory;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class BackpackInventory extends ItemInventory {
    private SingleVariantStorage<FluidVariant> leftTank;
    private SingleVariantStorage<FluidVariant> rightTank;


    private final String LEFT_TANK = "LeftTank";
    private final String RIGHT_TANK = "RightTank";
    private final String LEFT_TANK_AMOUNT = "LeftTankAmount";
    private final String RIGHT_TANK_AMOUNT = "RightTankAmount";

    public BackpackInventory(ItemStack backpackItem) {
        super(backpackItem, BackpackItem.INVENTORY_SIZE + 5);
    }

    @Override
    protected void init() {
        super.init();

        this.leftTank = createFluidStorage();
        this.rightTank = createFluidStorage();

        NbtCompound backpackNbt = this.item.getOrCreateNbt();

        readTanks(backpackNbt);
    }

    @Override
    public void markDirty() {
        super.markDirty();
        NbtCompound backpackNbt = this.item.getOrCreateNbt();

        writeTanks(backpackNbt);
    }

    public ItemStack getSleepingBag() {
        return this.getItems().get(BackpackScreenHandler.SLEEPING_BAG_SLOT_ID);
    }

    public void setSleepingBag(ItemStack itemStack) {
        this.getItems().set(BackpackScreenHandler.SLEEPING_BAG_SLOT_ID, itemStack);
        this.markDirty();
    }

    public SingleVariantStorage<FluidVariant> getLeftTank() {
        return this.leftTank;
    }

    public SingleVariantStorage<FluidVariant> getRightTank() {
        return this.rightTank;
    }

    private void writeTanks(NbtCompound nbtCompound) {
        nbtCompound.put(LEFT_TANK, this.leftTank.variant.toNbt());
        nbtCompound.put(RIGHT_TANK, this.rightTank.variant.toNbt());
        nbtCompound.putLong(LEFT_TANK_AMOUNT, this.leftTank.amount);
        nbtCompound.putLong(RIGHT_TANK_AMOUNT, this.rightTank.amount);
    }

    private void readTanks(NbtCompound nbtCompound) {
        this.leftTank.variant = FluidVariant.fromNbt(nbtCompound.getCompound(LEFT_TANK));
        this.rightTank.variant = FluidVariant.fromNbt(nbtCompound.getCompound(RIGHT_TANK));

        this.leftTank.amount = nbtCompound.getLong(LEFT_TANK_AMOUNT);
        this.rightTank.amount = nbtCompound.getLong(RIGHT_TANK_AMOUNT);
    }

    private SingleVariantStorage<FluidVariant> createFluidStorage() {
        return new SingleVariantStorage<>() {
            @Override
            protected FluidVariant getBlankVariant() {
                return FluidVariant.blank();
            }

            @Override
            protected long getCapacity(FluidVariant variant) {
                return 4000;
            }
        };
    }
}
