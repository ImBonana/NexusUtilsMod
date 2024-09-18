package me.imbanana.nexusutils.effect;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.effect.custom.BleedStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEffects {

    public static final RegistryEntry<StatusEffect> BLEED = registerStatusEffect("bleed", new BleedStatusEffect());

    public static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, NexusUtils.idOf(name), statusEffect);
    }

    public static void registerEffects() {
        NexusUtils.LOGGER.info("Registering Status Effects for " + NexusUtils.MOD_ID);
    }
}
