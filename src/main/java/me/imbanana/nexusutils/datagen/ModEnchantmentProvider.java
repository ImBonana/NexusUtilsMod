package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentProvider extends FabricDynamicRegistryProvider {
    public ModEnchantmentProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        register(entries, registries, ModEnchantments.AEGIS);
        register(entries, registries, ModEnchantments.ATTRACTION);
        register(entries, registries, ModEnchantments.AUTO_SMELT);
        register(entries, registries, ModEnchantments.BLAST);
        register(entries, registries, ModEnchantments.BLIND);
        register(entries, registries, ModEnchantments.CHAOS);
        register(entries, registries, ModEnchantments.DEVOUR);
        register(entries, registries, ModEnchantments.DIMINISH);
        register(entries, registries, ModEnchantments.DISAPPEAR);
        register(entries, registries, ModEnchantments.DOUBLE_STRIKE);
        register(entries, registries, ModEnchantments.ENDER_SLAYER);
        register(entries, registries, ModEnchantments.EXPERIENCE);
        register(entries, registries, ModEnchantments.EXTRA_HEALTH);
        register(entries, registries, ModEnchantments.FAMINE);
        register(entries, registries, ModEnchantments.HEADLIGHT);
        register(entries, registries, ModEnchantments.ICE_ASPECT);
        register(entries, registries, ModEnchantments.IMPACT);
        register(entries, registries, ModEnchantments.JUMP);
        register(entries, registries, ModEnchantments.LAUNCH);
        register(entries, registries, ModEnchantments.LAVA_WALKER);
        register(entries, registries, ModEnchantments.LIFESTEAL);
        register(entries, registries, ModEnchantments.LIGHTNING);
        register(entries, registries, ModEnchantments.NETHER_SLAYER);
        register(entries, registries, ModEnchantments.NIGHT_OWL);
        register(entries, registries, ModEnchantments.ORE_EXCAVATION);
        register(entries, registries, ModEnchantments.PERISH);
        register(entries, registries, ModEnchantments.PHOENIX);
        register(entries, registries, ModEnchantments.PLUMMET);
        register(entries, registries, ModEnchantments.POISON);
        register(entries, registries, ModEnchantments.POSEIDON);
        register(entries, registries, ModEnchantments.PROJECTILE_DEFLECT);
        register(entries, registries, ModEnchantments.REPLANTER);
        register(entries, registries, ModEnchantments.ROCKET_ESCAPE);
        register(entries, registries, ModEnchantments.SHOCKWAVE);
        register(entries, registries, ModEnchantments.TELEPATHY);
        register(entries, registries, ModEnchantments.TIMBER);
        register(entries, registries, ModEnchantments.TWINGE);
        register(entries, registries, ModEnchantments.VOODOO);
    }

    @Override
    public String getName() {
        return "nexusutils";
    }

    private void register(Entries entries, RegistryWrapper.WrapperLookup registries, NexusEnchantment enchantment, ResourceCondition... resourceConditions) {
        entries.add(enchantment.getRegistryKey(), enchantment.getBuilder(registries).build(enchantment.getRegistryKey().getValue()), resourceConditions);
    }
}
