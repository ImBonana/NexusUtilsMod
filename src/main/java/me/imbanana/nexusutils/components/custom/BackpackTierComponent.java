package me.imbanana.nexusutils.components.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringIdentifiable;

import java.util.Arrays;

public record BackpackTierComponent(Tier tier) {
    public static final Codec<BackpackTierComponent> CODEC = RecordCodecBuilder.create(builder ->
        builder.group(
                Tier.CODEC.fieldOf("tier").forGetter(BackpackTierComponent::tier)
        ).apply(builder, BackpackTierComponent::new)
    );

    public static BackpackTierComponent createDefaultBackpackTier() {
        return new BackpackTierComponent(Tier.TIER_0);
    }

    public enum Tier implements StringIdentifiable {
        TIER_0("tier_0", 0, 0xC6C6C6),
        TIER_1("tier_1", 1, 0xffffff),
        TIER_2("tier_2", 2, 0xDEB12D),
        TIER_3("tier_3", 3, 0x2CBAA8),
        TIER_4("tier_4", 4, 0x443A3B),;

        public static final StringIdentifiable.EnumCodec<Tier> CODEC = StringIdentifiable.createCodec(Tier::values);

        private final String id;
        private final int tier;
        private final int color;

        Tier(String id, int tier, int color) {
            this.id = id;
            this.tier = tier;
            this.color = color;
        }

        @Override
        public String asString() {
            return this.id;
        }

        public int asNumber() {
            return tier;
        }

        public int getColor() {
            return color;
        }

        public static Tier fromNumber(int v) {
            return Arrays.stream(Tier.values()).filter(tier1 -> tier1.tier == v).findFirst().orElse(null);
        }
    }
}
