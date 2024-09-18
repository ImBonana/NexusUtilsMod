package me.imbanana.nexusutils.tags;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModEnchantmentTags {
    public static final TagKey<Enchantment> ATTRACTION_EXCLUSIVE_SET = register("exclusive/attraction");
    public static final TagKey<Enchantment> AUTO_SMELT_EXCLUSIVE_SET = register("exclusive/auto_smelt");
    public static final TagKey<Enchantment> MULTIMINING_EXCLUSIVE_SET = register("exclusive/multimining");
    public static final TagKey<Enchantment> CHAOS_EXCLUSIVE_SET = register("exclusive/chaos");
    public static final TagKey<Enchantment> ICE_ASPECT_EXCLUSIVE_SET = register("exclusive/ice_aspect");
    public static final TagKey<Enchantment> LAVA_WALKER_EXCLUSIVE_SET = register("exclusive/lava_walker");

    private static TagKey<Enchantment> register(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, NexusUtils.idOf(id));
    }
}
