package me.imbanana.nexusutils.enchantment.effects;

import com.mojang.serialization.MapCodec;
import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEnchantmentEffects {
    public static final MapCodec<StealFoodEnchantmentEffect> STEAL_FOOD = register("steal_food", StealFoodEnchantmentEffect.CODEC);
    public static final MapCodec<StealHealthEnchantmentEffect> STEAL_HEALTH = register("steal_health", StealHealthEnchantmentEffect.CODEC);
    public static final MapCodec<LaunchEntityForwardEnchantmentEffect> LAUNCH_FORWARD = register("launch_forward", LaunchEntityForwardEnchantmentEffect.CODEC);
    public static final MapCodec<LaunchEntityIntoAirEnchantment> LAUNCH_INTO_AIR = register("launch_into_air", LaunchEntityIntoAirEnchantment.CODEC);
    public static final MapCodec<LaunchEntityToEntityEnchantmentEffect> LAUNCH_TO_ENTITY = register("launch_to_entity", LaunchEntityToEntityEnchantmentEffect.CODEC);
    public static final MapCodec<DamageMobsFallBasedEnchantmentEffect> DAMAGE_MOBS_FALL = register("damage_mobs_fall", DamageMobsFallBasedEnchantmentEffect.CODEC);
    public static final MapCodec<ItemCooldownEnchantmentEffect> ITEM_COOLDOWN = register("item_cooldown", ItemCooldownEnchantmentEffect.CODEC);
    public static final MapCodec<SwingHandEnchantmentEffect> SWING_HAND = register("swing_hand", SwingHandEnchantmentEffect.CODEC);

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, NexusUtils.idOf(id), codec);
    }

    public static void init() {
        NexusUtils.LOGGER.info("Registering Enchantment Effects for " + NexusUtils.MOD_ID);
    }
}
