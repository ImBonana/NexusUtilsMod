package me.imbanana.nexusutils.screen.backpack;

import me.imbanana.nexusutils.components.custom.FluidTanksComponent;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;

public class FluidTank extends SingleVariantStorage<FluidVariant> {
    private final long capacity;

    public FluidTank(long capacity) {
        this(capacity, FluidVariant.blank(), 0);
    }

    public FluidTank(long capacity, FluidVariant variant, long amount) {
        this.capacity = capacity;
        this.variant = variant;
        this.amount = amount;
    }

    public static FluidTank of(FluidTanksComponent.Tank tank) {
        return new FluidTank(tank.capacity(), tank.fluidVariant(), tank.amount());
    }

    public static FluidTank of(FluidTanksComponent.Tank tank, long capacity) {
        return new FluidTank(capacity, tank.fluidVariant(), tank.amount());
    }

    @Override
    public FluidVariant getBlankVariant() {
        return FluidVariant.blank();
    }

    @Override
    public long getCapacity(FluidVariant variant) {
        return this.capacity;
    }
}
