package me.imbanana.nexusutils.damageSources;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;

public class ModDamageSources {
    public static final SimpleDamageSource BLEED = register("bleed");

    static SimpleDamageSource register(String name) {
        return new SimpleDamageSource(name, RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(NexusUtils.MOD_ID, name)));
    }
}
