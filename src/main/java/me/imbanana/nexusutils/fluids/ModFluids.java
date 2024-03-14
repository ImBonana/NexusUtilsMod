package me.imbanana.nexusutils.fluids;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.fluids.custom.milk.MilkFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFluids {
    public static final Fluid MILK = Registry.register(Registries.FLUID, new Identifier(NexusUtils.MOD_ID, "milk"), new MilkFluid());

    public static void registerFluids() {
        NexusUtils.LOGGER.info("Registering Mod Fluids for " + NexusUtils.MOD_ID);
    }
}
