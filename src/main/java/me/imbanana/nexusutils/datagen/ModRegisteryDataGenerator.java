package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRegisteryDataGenerator extends FabricDynamicRegistryProvider {
    public ModRegisteryDataGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.TRIM_MATERIAL));

        registerEnchantment(entries, registries, ModEnchantments.AEGIS);
        registerEnchantment(entries, registries, ModEnchantments.ATTRACTION);
        registerEnchantment(entries, registries, ModEnchantments.AUTO_SMELT);
        registerEnchantment(entries, registries, ModEnchantments.BLAST);
        registerEnchantment(entries, registries, ModEnchantments.BLIND);
        registerEnchantment(entries, registries, ModEnchantments.CHAOS);
        registerEnchantment(entries, registries, ModEnchantments.DEVOUR);
        registerEnchantment(entries, registries, ModEnchantments.DIMINISH);
        registerEnchantment(entries, registries, ModEnchantments.DISAPPEAR);
        registerEnchantment(entries, registries, ModEnchantments.DOUBLE_STRIKE);
        registerEnchantment(entries, registries, ModEnchantments.ENDER_SLAYER);
        registerEnchantment(entries, registries, ModEnchantments.EXPERIENCE);
        registerEnchantment(entries, registries, ModEnchantments.EXTRA_HEALTH);
        registerEnchantment(entries, registries, ModEnchantments.FAMINE);
        registerEnchantment(entries, registries, ModEnchantments.HEADLIGHT);
        registerEnchantment(entries, registries, ModEnchantments.ICE_ASPECT);
        registerEnchantment(entries, registries, ModEnchantments.IMPACT);
        registerEnchantment(entries, registries, ModEnchantments.JUMP);
        registerEnchantment(entries, registries, ModEnchantments.LAUNCH);
        registerEnchantment(entries, registries, ModEnchantments.LAVA_WALKER);
        registerEnchantment(entries, registries, ModEnchantments.LIFESTEAL);
        registerEnchantment(entries, registries, ModEnchantments.LIGHTNING);
        registerEnchantment(entries, registries, ModEnchantments.NETHER_SLAYER);
        registerEnchantment(entries, registries, ModEnchantments.NIGHT_OWL);
        registerEnchantment(entries, registries, ModEnchantments.ORE_EXCAVATION);
        registerEnchantment(entries, registries, ModEnchantments.PERISH);
        registerEnchantment(entries, registries, ModEnchantments.PHOENIX);
        registerEnchantment(entries, registries, ModEnchantments.PLUMMET);
        registerEnchantment(entries, registries, ModEnchantments.POISON);
        registerEnchantment(entries, registries, ModEnchantments.POSEIDON);
        registerEnchantment(entries, registries, ModEnchantments.PROJECTILE_DEFLECT);
        registerEnchantment(entries, registries, ModEnchantments.REPLANTER);
        registerEnchantment(entries, registries, ModEnchantments.ROCKET_ESCAPE);
        registerEnchantment(entries, registries, ModEnchantments.SHOCKWAVE);
        registerEnchantment(entries, registries, ModEnchantments.TELEPATHY);
        registerEnchantment(entries, registries, ModEnchantments.TIMBER);
        registerEnchantment(entries, registries, ModEnchantments.TWINGE);
        registerEnchantment(entries, registries, ModEnchantments.VOODOO);
    }

    @Override
    public String getName() {
        return "nexusutils";
    }

    private void registerEnchantment(Entries entries, RegistryWrapper.WrapperLookup registries, NexusEnchantment enchantment, ResourceCondition... resourceConditions) {
        entries.add(enchantment.getRegistryKey(), enchantment.getBuilder(registries).build(enchantment.getRegistryKey().getValue()), resourceConditions);
    }
}
