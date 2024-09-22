package me.imbanana.nexusutils.screen.backpack;

import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import me.imbanana.nexusutils.components.custom.FluidTanksComponent;
import me.imbanana.nexusutils.item.ItemInventory;
import net.minecraft.item.ItemStack;

public class BackpackInventory extends ItemInventory {
    public static final int INVENTORY_OFFSET = 5;

    private FluidTank leftTank;
    private FluidTank rightTank;

    private final BackpackTierComponent.Tier tier;

    public BackpackInventory(ItemStack backpackStack, BackpackTierComponent.Tier tier) {
        super(backpackStack, getInventorySize(tier));

        this.tier = tier;
        this.readData();
    }

    public BackpackInventory(ItemStack backpackStack) {
        this(backpackStack, backpackStack.getOrDefault(ModComponents.BACKPACK_TIER, BackpackTierComponent.createDefaultBackpackTier()).tier());
    }

    private void readData() {
        this.leftTank = new FluidTank(this.getCapacity());
        this.rightTank = new FluidTank(this.getCapacity());

        readTanks();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        writeTanks();
    }

    private int getSleepingBagSlotId() {
        return BackpackScreenHandler.UtilSlots.SLEEPING_BAG.getId();
    }

    public ItemStack getSleepingBag() {
        return this.getItems().get(this.getSleepingBagSlotId());
    }

    public void setSleepingBag(ItemStack itemStack) {
        this.getItems().set(this.getSleepingBagSlotId(), itemStack);
        this.markDirty();
    }

    public long getCapacity() {
        return getCapacity(this.tier);
    }

    public FluidTank getLeftTank() {
        return this.leftTank;
    }

    public FluidTank getRightTank() {
        return this.rightTank;
    }

    private void writeTanks() {
        this.stack.set(ModComponents.FLUID_TANKS,
                new FluidTanksComponent(
                        FluidTanksComponent.Tank.of(this.leftTank),
                        FluidTanksComponent.Tank.of(this.rightTank)
                )
        );
    }

    private void readTanks() {
        long capacity = this.getCapacity();
        FluidTanksComponent fluidTanks = this.stack.getOrDefault(ModComponents.FLUID_TANKS, FluidTanksComponent.createTanks(capacity));

        this.leftTank = FluidTank.of(fluidTanks.leftTank(), capacity);
        this.rightTank = FluidTank.of(fluidTanks.rightTank(), capacity);
    }

    public static int getInventorySize(BackpackTierComponent.Tier tier) {
        return (9*2) + (tier.asNumber() * 9) + INVENTORY_OFFSET;
    }

    public static long getCapacity(BackpackTierComponent.Tier tier) {
        return 3000L + (tier.asNumber() * 1000L);
    }
}
