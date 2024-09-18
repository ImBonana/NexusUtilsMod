package me.imbanana.nexusutils.enchantment.lootConditions;

import com.mojang.serialization.MapCodec;
import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModLootConditionTypes {
    public static final LootConditionType ENTITY_STATUS = register("entity_status", EntityStatusLootCondition.CODEC);
    public static final LootConditionType DAY_TIME = register("day_time", DayTimeLootCondition.CODEC);
    public static final LootConditionType ENTITY_FALL_DISTANCE = register("entity_fall_distance", EntityFallDistanceLootCondition.CODEC);
    public static final LootConditionType ITEM_COOLDOWN = register("item_cooldown", ItemCooldownLootCondition.CODEC);

    private static LootConditionType register(String id, MapCodec<? extends LootCondition> codec) {
        return Registry.register(Registries.LOOT_CONDITION_TYPE, NexusUtils.idOf(id), new LootConditionType(codec));
    }

    public static void init() {
        NexusUtils.LOGGER.info("Registering Loot Condition Types " + NexusUtils.MOD_ID);
    }
}
