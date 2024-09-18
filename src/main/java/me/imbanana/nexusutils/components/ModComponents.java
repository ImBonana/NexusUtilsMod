package me.imbanana.nexusutils.components;

import com.mojang.serialization.Codec;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.components.custom.FluidTanksComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModComponents {
    public static final ComponentType<FluidTanksComponent> FLUID_TANKS = register("fluid_tank", FluidTanksComponent.CODEC);

    private static <T> ComponentType<T> register(String id, Codec<T> codec) {
        return Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                NexusUtils.idOf(id),
                ComponentType.<T>builder().codec(codec).build()
        );
    }

    public static void registerModComponents() {
        NexusUtils.LOGGER.info("Registering Mod Components for " + NexusUtils.MOD_ID);
    }
}
