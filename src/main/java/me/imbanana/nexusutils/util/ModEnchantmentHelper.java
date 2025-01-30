package me.imbanana.nexusutils.util;

import com.mojang.datafixers.util.Pair;
import me.imbanana.nexusutils.enchantment.lootContextTypes.ModLootContextTypes;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.effect.EnchantmentEffectEntry;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Optional;

public class ModEnchantmentHelper {
    public static <T extends List<EnchantmentEffectEntry<EnchantmentEntityEffect>>> void applyEntityEffects(ServerWorld serverWorld, LivingEntity entity, Vec3d pos, ItemStack itemStack, EquipmentSlot slot, ComponentType<T> componentType) {
        Pair<T, Integer> pair = EnchantmentHelper.getHighestLevelEffect(itemStack, componentType);
        if (pair != null) {
            int level = pair.getSecond();

            T effects = pair.getFirst();
            for (EnchantmentEffectEntry<EnchantmentEntityEffect> effect : effects) {
                LootContext context = createEnchantedEntityWithItemLootContext(serverWorld, level, entity, itemStack, pos);
                if (effect.test(context)) {
                    EnchantmentEffectContext enchantmentEffectContext = new EnchantmentEffectContext(itemStack, slot, entity);
                    effect.effect().apply(serverWorld, level, enchantmentEffectContext, entity, pos);
                }
            }
        }
    }

    private static LootContext createEnchantedEntityWithItemLootContext(ServerWorld world, int level, Entity entity, ItemStack tool, Vec3d pos) {
        LootWorldContext lootContextParameterSet = new LootWorldContext.Builder(world)
                .add(LootContextParameters.THIS_ENTITY, entity)
                .add(LootContextParameters.ENCHANTMENT_LEVEL, level)
                .add(LootContextParameters.ORIGIN, pos)
                .add(LootContextParameters.TOOL, tool)
                .build(ModLootContextTypes.ENCHANTED_ENTITY_ITEM);
        return new LootContext.Builder(lootContextParameterSet).build(Optional.empty());
    }
}
