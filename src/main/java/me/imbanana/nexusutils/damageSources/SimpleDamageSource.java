package me.imbanana.nexusutils.damageSources;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SimpleDamageSource {
    String name;
    RegistryKey<DamageType> registryKey;

    public SimpleDamageSource(String name, RegistryKey<DamageType> registryKey) {
        this.name = name;
        this.registryKey = registryKey;
    }

    public RegistryKey<DamageType> getRegistryKey() {
        return registryKey;
    }

    public String getName() {
        return name;
    }

    public DamageSource create(World world, @Nullable Entity source, @Nullable Entity attacker) {
        return world.getDamageSources().create(this.getRegistryKey(), source, attacker);
    }

    public DamageSource create(World world, @Nullable Entity source) {
        return world.getDamageSources().create(this.getRegistryKey(), source);
    }

    public DamageSource create(World world) {
        return world.getDamageSources().create(this.getRegistryKey());
    }
}
