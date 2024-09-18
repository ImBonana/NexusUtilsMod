package me.imbanana.nexusutils.enchantment.lootConditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.NumberRange;
import net.minecraft.util.StringIdentifiable;

public record EntityStatusLootCondition(NumberRange.DoubleRange range, StatusType statusType, LootContext.EntityTarget entity) implements LootCondition {
    public static final MapCodec<EntityStatusLootCondition> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
                NumberRange.DoubleRange.CODEC.fieldOf("range").forGetter(EntityStatusLootCondition::range),
                StatusType.CODEC.fieldOf("statusType").forGetter(EntityStatusLootCondition::statusType),
                LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(EntityStatusLootCondition::entity)
            )
            .apply(instance, EntityStatusLootCondition::new)
    );

    @Override
    public LootConditionType getType() {
        return ModLootConditionTypes.ENTITY_STATUS;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(this.entity.getParameter());
        if(this.statusType == StatusType.HEALTH) {
            if(entity instanceof LivingEntity livingEntity) {
                return this.range.test(livingEntity.getHealth());
            }
        } else if (this.statusType == StatusType.FOOD) {
            if(entity instanceof PlayerEntity player) {
                return this.range.test(player.getHungerManager().getFoodLevel());
            }
        }

        return true;
    }

    public static LootCondition.Builder builder(LootContext.EntityTarget entity, StatusType statusType, NumberRange.DoubleRange range) {
        return () -> new EntityStatusLootCondition(range, statusType, entity);
    }

    public enum StatusType implements StringIdentifiable {
        HEALTH("health"),
        FOOD("food");

        public static final StringIdentifiable.BasicCodec<StatusType> CODEC = StringIdentifiable.createCodec(StatusType::values);
        private final String type;

        StatusType(final String type) {
            this.type = type;
        }

        @Override
        public String asString() {
            return this.type;
        }
    }
}
