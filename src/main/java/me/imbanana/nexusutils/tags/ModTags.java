package me.imbanana.nexusutils.tags;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> SLEEPING_BAGS = register("sleeping_bags");
        public static final TagKey<Item> RANGED_PROJECTILE_ENCHANTABLE = register("enchantable/ranged_projectile");
        public static final TagKey<Item> RANGED_WEAPON_ENCHANTABLE = register("enchantable/ranged_weapon");
        public static final TagKey<Item> AXES_ENCHANTABLE = register("enchantable/axes");
        public static final TagKey<Item> HOES_ENCHANTABLE = register("enchantable/hoes");
        public static final TagKey<Item> PICKAXES_ENCHANTABLE = register("enchantable/pickaxes");
        public static final TagKey<Item> EXPERIENCE_ENCHANTABLE = register("enchantable/experience");
        public static final TagKey<Item> ICE_ASPECT_ENCHANTABLE = register("enchantable/ice_aspect");
        public static final TagKey<Item> TELEPATHY_ENCHANTABLE = register("enchantable/telepathy");

        private static TagKey<Item> register(String id) {
            return TagKey.of(RegistryKeys.ITEM, NexusUtils.idOf(id));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> SNAIL_SPAWNABLE = register("snail_spawnable");
        public static final TagKey<Block> SLEEPING_BAGS = register("sleeping_bags");

        private static TagKey<Block> register(String id) {
            return TagKey.of(RegistryKeys.BLOCK, NexusUtils.idOf(id));
        }
    }

    public static final class EntityTypes {
        public static final TagKey<EntityType<?>> NO_BLEEDING = register("no_bleeding");

        private static TagKey<EntityType<?>> register(String id) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, NexusUtils.idOf(id));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> SNAIL_SPAWN = register("snail_spawnable");

        private static TagKey<Biome> register(String id) {
            return TagKey.of(RegistryKeys.BIOME, NexusUtils.idOf(id));
        }
    }

    public static class Enchantments {
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
}
