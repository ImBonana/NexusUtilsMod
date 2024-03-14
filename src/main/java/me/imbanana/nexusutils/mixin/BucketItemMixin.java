package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.util.accessors.IBucketItem;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.FluidModificationItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin extends Item implements FluidModificationItem, IBucketItem {
    @Shadow @Final private Fluid fluid;

    public BucketItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public Fluid nexusutils$getFluid() {
        return this.fluid;
    }
}
