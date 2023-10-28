package me.imbanana.nexusutils.tags;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public final class ModEntityTypeTags {
    public static final TagKey<EntityType<?>> NO_BLEEDING_APPLY_MOBS = register("no_bleeding_apply_mobs");

    private static TagKey<EntityType<?>> register(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(NexusUtils.MOD_ID, id));
    }
}
