package me.imbanana.nexusutils.entity;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEntityAttributes {
    public static final RegistryEntry<EntityAttribute> GENERIC_JUMP_COUNT = register(
            "generic.jump_count", new ClampedEntityAttribute("attribute.name.generic.jump_count", 0.0, 0.0, 100.0).setTracked(true)
    );

    private static RegistryEntry<EntityAttribute> register(String id, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, NexusUtils.idOf(id), attribute);
    }

    public static void registerAttributes() {
        NexusUtils.LOGGER.info("Registering entity attributes for " + NexusUtils.MOD_ID);
    }
}
