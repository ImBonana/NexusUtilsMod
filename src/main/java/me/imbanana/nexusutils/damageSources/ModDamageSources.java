package me.imbanana.nexusutils.damageSources;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.*;

public class ModDamageSources {
    public static final RegistryKey<DamageType> BLEED = register("bleed");

    private static RegistryKey<DamageType> register(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, NexusUtils.idOf(name));
    }
}
