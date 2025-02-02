package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagProvider extends FabricTagProvider.EnchantmentTagProvider {
    public ModEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Enchantments.ATTRACTION_EXCLUSIVE_SET)
                .add(Enchantments.PUNCH)
                .addOptional(ModEnchantments.ATTRACTION.getRegistryKey());

        getOrCreateTagBuilder(ModTags.Enchantments.AUTO_SMELT_EXCLUSIVE_SET)
                .add(Enchantments.SILK_TOUCH)
                .addOptional(ModEnchantments.AUTO_SMELT.getRegistryKey());

        getOrCreateTagBuilder(ModTags.Enchantments.CHAOS_EXCLUSIVE_SET)
                .addOptional(ModEnchantments.PERISH.getRegistryKey())
                .addOptional(ModEnchantments.CHAOS.getRegistryKey());

        getOrCreateTagBuilder(ModTags.Enchantments.ICE_ASPECT_EXCLUSIVE_SET)
                .add(Enchantments.FIRE_ASPECT)
                .addOptional(ModEnchantments.ICE_ASPECT.getRegistryKey());

        getOrCreateTagBuilder(ModTags.Enchantments.LAVA_WALKER_EXCLUSIVE_SET)
                .add(Enchantments.FROST_WALKER)
                .addOptional(ModEnchantments.LAVA_WALKER.getRegistryKey());

        getOrCreateTagBuilder(ModTags.Enchantments.MULTIMINING_EXCLUSIVE_SET)
                .addOptional(ModEnchantments.BLAST.getRegistryKey())
                .addOptional(ModEnchantments.TIMBER.getRegistryKey())
                .addOptional(ModEnchantments.ORE_EXCAVATION.getRegistryKey());

        getOrCreateTagBuilder(EnchantmentTags.NON_TREASURE)
                .addOptional(ModEnchantments.AEGIS.getRegistryKey())
                .addOptional(ModEnchantments.ATTRACTION.getRegistryKey())
                .addOptional(ModEnchantments.AUTO_SMELT.getRegistryKey())
                .addOptional(ModEnchantments.BLAST.getRegistryKey())
                .addOptional(ModEnchantments.BLIND.getRegistryKey())
                .addOptional(ModEnchantments.CHAOS.getRegistryKey())
                .addOptional(ModEnchantments.DEVOUR.getRegistryKey())
                .addOptional(ModEnchantments.DIMINISH.getRegistryKey())
                .addOptional(ModEnchantments.DISAPPEAR.getRegistryKey())
                .addOptional(ModEnchantments.DOUBLE_STRIKE.getRegistryKey())
                .addOptional(ModEnchantments.ENDER_SLAYER.getRegistryKey())
                .addOptional(ModEnchantments.EXPERIENCE.getRegistryKey())
                .addOptional(ModEnchantments.EXTRA_HEALTH.getRegistryKey())
                .addOptional(ModEnchantments.FAMINE.getRegistryKey())
                .addOptional(ModEnchantments.HEADLIGHT.getRegistryKey())
                .addOptional(ModEnchantments.ICE_ASPECT.getRegistryKey())
                .addOptional(ModEnchantments.IMPACT.getRegistryKey())
                .addOptional(ModEnchantments.JUMP.getRegistryKey())
                .addOptional(ModEnchantments.LAUNCH.getRegistryKey())
                .addOptional(ModEnchantments.LAVA_WALKER.getRegistryKey())
                .addOptional(ModEnchantments.LIFESTEAL.getRegistryKey())
                .addOptional(ModEnchantments.LIGHTNING.getRegistryKey())
                .addOptional(ModEnchantments.NETHER_SLAYER.getRegistryKey())
                .addOptional(ModEnchantments.NIGHT_OWL.getRegistryKey())
                .addOptional(ModEnchantments.ORE_EXCAVATION.getRegistryKey())
                .addOptional(ModEnchantments.PERISH.getRegistryKey())
                .addOptional(ModEnchantments.PHOENIX.getRegistryKey())
                .addOptional(ModEnchantments.PLUMMET.getRegistryKey())
                .addOptional(ModEnchantments.POISON.getRegistryKey())
                .addOptional(ModEnchantments.POSEIDON.getRegistryKey())
                .addOptional(ModEnchantments.PROJECTILE_DEFLECT.getRegistryKey())
                .addOptional(ModEnchantments.REPLANTER.getRegistryKey())
                .addOptional(ModEnchantments.ROCKET_ESCAPE.getRegistryKey())
                .addOptional(ModEnchantments.SHOCKWAVE.getRegistryKey())
                .addOptional(ModEnchantments.TELEPATHY.getRegistryKey())
                .addOptional(ModEnchantments.TIMBER.getRegistryKey())
                .addOptional(ModEnchantments.TWINGE.getRegistryKey())
                .addOptional(ModEnchantments.VOODOO.getRegistryKey());
    }
}
