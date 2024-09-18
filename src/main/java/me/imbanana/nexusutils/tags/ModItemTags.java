package me.imbanana.nexusutils.tags;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModItemTags {
    public static final TagKey<Item> SLEEPING_BAGS = register("sleeping_bags");
    public static final TagKey<Item> RANGED_PROJECTILE_ENCHANTABLE = register("enchantable/ranged_projectile");
    public static final TagKey<Item> RANGED_WEAPON_ENCHANTABLE = register("enchantable/ranged_weapon");
    public static final TagKey<Item> AXES_ENCHANTABLE = register("enchantable/axes");
    public static final TagKey<Item> PICKAXES_ENCHANTABLE = register("enchantable/pickaxes");
    public static final TagKey<Item> EXPERIENCE_ENCHANTABLE = register("enchantable/experience");
    public static final TagKey<Item> ICE_ASPECT_ENCHANTABLE = register("enchantable/ice_aspect");
    public static final TagKey<Item> TELEPATHY_ENCHANTABLE = register("enchantable/telepathy");

    private static TagKey<Item> register(String id) {
        return TagKey.of(RegistryKeys.ITEM, NexusUtils.idOf(id));
    }
}
