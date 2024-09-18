package me.imbanana.nexusutils.enchantment.componentTypes;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.enchantment.lootContextTypes.ModLootContextTypes;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;

import java.util.List;
import java.util.function.UnaryOperator;

public class ModEnchantmentEffectComponentTypes {

    public static final ComponentType<List<EnchantmentEffectEntry<EnchantmentEntityEffect>>> ON_FALL = register("on_fall", builder -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentEntityEffect.CODEC, LootContextTypes.ENCHANTED_ENTITY).listOf()));
    public static final ComponentType<List<EnchantmentEffectEntry<EnchantmentEntityEffect>>> ON_RIGHT_CLICK = register("on_right_click", builder -> builder.codec(EnchantmentEffectEntry.createCodec(EnchantmentEntityEffect.CODEC, ModLootContextTypes.ENCHANTED_ENTITY_ITEM).listOf()));
    public static final ComponentType<Unit> AUTO_SMELT = register("auto_smelt", builder -> builder.codec(Unit.CODEC));
    public static final ComponentType<Unit> BLAST = register("blast", builder -> builder.codec(Unit.CODEC));
    public static final ComponentType<Unit> ORE_EXCAVATION = register("ore_excavation", builder -> builder.codec(Unit.CODEC));
    public static final ComponentType<Unit> PHOENIX = register("phoenix", builder -> builder.codec(Unit.CODEC));
    public static final ComponentType<Unit> REPLANTER = register("replanter", builder -> builder.codec(Unit.CODEC));
    public static final ComponentType<Unit> TELEPARHY = register("telepathy", builder -> builder.codec(Unit.CODEC));
    public static final ComponentType<Unit> TIMBER = register("timber", builder -> builder.codec(Unit.CODEC));

    private static <T> ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> unaryOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, NexusUtils.idOf(name), unaryOperator.apply(ComponentType.builder()).build());
    }

    public static void init() {
        NexusUtils.LOGGER.info("Registering Enchantments Effect Component Types for " + NexusUtils.MOD_ID);
    }
}
