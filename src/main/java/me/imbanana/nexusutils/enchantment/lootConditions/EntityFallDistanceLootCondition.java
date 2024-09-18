package me.imbanana.nexusutils.enchantment.lootConditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.NumberRange;

public record EntityFallDistanceLootCondition(NumberRange.DoubleRange distance, LootContext.EntityTarget entity) implements LootCondition {
    public static final MapCodec<EntityFallDistanceLootCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    NumberRange.DoubleRange.CODEC.fieldOf("distance").forGetter(EntityFallDistanceLootCondition::distance),
                    LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(EntityFallDistanceLootCondition::entity)
            )
            .apply(instance, EntityFallDistanceLootCondition::new)
    );

    @Override
    public LootConditionType getType() {
        return ModLootConditionTypes.ENTITY_FALL_DISTANCE;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(this.entity.getParameter());
        if(entity == null) return false;
        return this.distance.test(entity.fallDistance);
    }

    public static LootCondition.Builder builder(LootContext.EntityTarget entity, NumberRange.DoubleRange distance) {
        return () -> new EntityFallDistanceLootCondition(distance, entity);
    }
}
