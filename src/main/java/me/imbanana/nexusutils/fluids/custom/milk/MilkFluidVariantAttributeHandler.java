package me.imbanana.nexusutils.fluids.custom.milk;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
import net.minecraft.text.Text;

public class MilkFluidVariantAttributeHandler implements FluidVariantAttributeHandler {
    @Override
    public Text getName(FluidVariant fluidVariant) {
        return Text.translatable("fluid.nexusutils.milk");
    }
}
