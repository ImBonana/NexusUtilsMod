package me.imbanana.nexusutils.effect;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.effect.custom.BleedStatusEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static StatusEffect MORE_HEALTH = registerStatusEffect("more_health",
            new ModSimpleStatusEffect(StatusEffectCategory.BENEFICIAL, 0xff0000)
                    .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, "44EA7A26-9E76-4C6D-B43F-C1E6DBA731E9",
                            1, EntityAttributeModifier.Operation.ADDITION));

    public static StatusEffect BLEED = registerStatusEffect("bleed", new BleedStatusEffect(StatusEffectCategory.HARMFUL, 10027008));

    public static StatusEffect registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(NexusUtils.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
        NexusUtils.LOGGER.info("Registering Status Effects for " + NexusUtils.MOD_ID);
    }
}
