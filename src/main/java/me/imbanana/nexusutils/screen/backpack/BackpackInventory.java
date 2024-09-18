package me.imbanana.nexusutils.screen.backpack;

import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.FluidTanksComponent;
import me.imbanana.nexusutils.item.ItemInventory;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import net.minecraft.item.ItemStack;

public class BackpackInventory extends ItemInventory {
    private FluidTank leftTank;
    private FluidTank rightTank;

    public BackpackInventory(ItemStack backpackItem) {
        super(backpackItem, BackpackItem.INVENTORY_SIZE + 5);
    }

    @Override
    protected void init() {
        super.init();

        this.leftTank = new FluidTank(BackpackItem.CAPACITY);
        this.rightTank = new FluidTank(BackpackItem.CAPACITY);

        readTanks();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        writeTanks();
    }

    public ItemStack getSleepingBag() {
        return this.getItems().get(BackpackScreenHandler.SLEEPING_BAG_SLOT_ID);
    }

    public void setSleepingBag(ItemStack itemStack) {
        this.getItems().set(BackpackScreenHandler.SLEEPING_BAG_SLOT_ID, itemStack);
        this.markDirty();
    }

    public FluidTank getLeftTank() {
        return this.leftTank;
    }

    public FluidTank getRightTank() {
        return this.rightTank;
    }

    private void writeTanks() {
        this.item.set(ModComponents.FLUID_TANKS,
                new FluidTanksComponent(
                        FluidTanksComponent.Tank.of(this.leftTank),
                        FluidTanksComponent.Tank.of(this.rightTank)
                )
        );
    }

    private void readTanks() {
        FluidTanksComponent fluidTanks = this.item.getOrDefault(ModComponents.FLUID_TANKS, FluidTanksComponent.createTanks(BackpackItem.CAPACITY));

        this.leftTank = FluidTank.of(fluidTanks.leftTank());
        this.rightTank = FluidTank.of(fluidTanks.rightTank());
    }
}
