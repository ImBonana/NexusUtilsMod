package me.imbanana.nexusutils.enchantment.lootConditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.StringIdentifiable;

public record DayTimeLootCondition(DayTime dayTime) implements LootCondition {
    public static final MapCodec<DayTimeLootCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    DayTime.CODEC.fieldOf("dayTime").forGetter(DayTimeLootCondition::dayTime)
            )
            .apply(instance, DayTimeLootCondition::new)
    );

    @Override
    public LootConditionType getType() {
        return ModLootConditionTypes.DAY_TIME;
    }

    @Override
    public boolean test(LootContext lootContext) {
        if(dayTime == DayTime.DAYLIGHT && lootContext.getWorld().isDay()) return true;

        return dayTime == DayTime.NIGHT && lootContext.getWorld().isNight();
    }

    public static LootCondition.Builder builder(DayTime dayTime) {
        return () -> new DayTimeLootCondition(dayTime);
    }

    public enum DayTime implements StringIdentifiable {
        NIGHT("night"),
        DAYLIGHT("daylight");

        public static final StringIdentifiable.BasicCodec<DayTime> CODEC = StringIdentifiable.createCodec(DayTime::values);
        private final String type;

        DayTime(final String type) {
            this.type = type;
        }

        @Override
        public String asString() {
            return this.type;
        }
    }
}
