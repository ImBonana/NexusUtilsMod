package me.imbanana.nexusutils.util.accessors;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;

public interface IBucketItem {
    default Fluid nexusutils$getFluid() {
        return Fluids.EMPTY;
    }
}
