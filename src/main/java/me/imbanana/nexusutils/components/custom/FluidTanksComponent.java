package me.imbanana.nexusutils.components.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.imbanana.nexusutils.screen.backpack.FluidTank;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

public record FluidTanksComponent(Tank leftTank, Tank rightTank) {
    public static final Codec<FluidTanksComponent> CODEC = RecordCodecBuilder.create(builder ->
        builder.group(
                Tank.CODEC.fieldOf("leftTank").forGetter(FluidTanksComponent::leftTank),
                Tank.CODEC.fieldOf("rightTank").forGetter(FluidTanksComponent::rightTank)
        ).apply(builder, FluidTanksComponent::new)
    );

    public static FluidTanksComponent createTanks(long capacity) {
        return new FluidTanksComponent(Tank.createEmpty(capacity), Tank.createEmpty(capacity));
    }

    public record Tank(FluidVariant fluidVariant, long amount, long capacity) {
        public static final Codec<Tank> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    FluidVariant.CODEC.fieldOf("fluid").forGetter(Tank::fluidVariant),
                    Codec.LONG.fieldOf("amount").forGetter(Tank::amount),
                    Codec.LONG.fieldOf("capacity").forGetter(Tank::capacity)
            ).apply(builder, Tank::new)
        );

        public static Tank createEmpty(long capacity) {
            return new Tank(FluidVariant.blank(), 0, capacity);
        }

        public static Tank of(FluidTank fluidTank) {
            return new Tank(fluidTank.variant, fluidTank.amount, fluidTank.getCapacity());
        }
    }
}
