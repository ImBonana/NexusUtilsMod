package me.imbanana.nexusutils.damageSources;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public record SimpleDamageSource(String name, RegistryKey<DamageType> registryKey) {

    public DamageSource create(World world, @Nullable Entity source, @Nullable Entity attacker) {
        return world.getDamageSources().create(this.registryKey(), source, attacker);
    }

    public DamageSource create(World world, @Nullable Entity source) {
        return world.getDamageSources().create(this.registryKey(), source);
    }

    public DamageSource create(World world) {
        return world.getDamageSources().create(this.registryKey());
    }
}
