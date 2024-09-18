package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.NexusEnchantment;
import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import me.imbanana.nexusutils.tags.ModEnchantmentTags;
import me.imbanana.nexusutils.tags.ModItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;

public class OreExcavationEnchantment extends NexusEnchantment {
    public OreExcavationEnchantment(RegistryKey<Enchantment> key) {
        super(key, (damageLookup, enchantmentLookup, itemLookup, blockLookup) -> Enchantment.builder(
                        Enchantment.definition(
                                itemLookup.getOrThrow(ModItemTags.PICKAXES_ENCHANTABLE),
                                1,
                                2,
                                Enchantment.leveledCost(15, 9),
                                Enchantment.leveledCost(65, 9),
                                8,
                                AttributeModifierSlot.MAINHAND
                        )
                ).exclusiveSet(enchantmentLookup.getOrThrow(ModEnchantmentTags.MULTIMINING_EXCLUSIVE_SET))
                .addEffect(
                    ModEnchantmentEffectComponentTypes.ORE_EXCAVATION
                )
        );
    }

    private static final List<Block> whitelistedBlocks = new ArrayList<>(){{
        add(Blocks.IRON_ORE);
        add(Blocks.DEEPSLATE_IRON_ORE);
        add(Blocks.COAL_ORE);
        add(Blocks.DEEPSLATE_COAL_ORE);
        add(Blocks.COPPER_ORE);
        add(Blocks.DEEPSLATE_COPPER_ORE);
        add(Blocks.DIAMOND_ORE);
        add(Blocks.DEEPSLATE_DIAMOND_ORE);
        add(Blocks.EMERALD_ORE);
        add(Blocks.DEEPSLATE_EMERALD_ORE);
        add(Blocks.GOLD_ORE);
        add(Blocks.DEEPSLATE_GOLD_ORE);
        add(Blocks.LAPIS_ORE);
        add(Blocks.DEEPSLATE_LAPIS_ORE);
        add(Blocks.REDSTONE_ORE);
        add(Blocks.DEEPSLATE_REDSTONE_ORE);
        add(Blocks.NETHER_GOLD_ORE);
        add(Blocks.NETHER_QUARTZ_ORE);
    }};

    public static boolean canBreak(Block block) {
        return whitelistedBlocks.contains(block);
    }
}
