package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

import java.util.ArrayList;
import java.util.List;

public class OreExcavationEnchantment extends Enchantment implements TradableEnchantment {
    public OreExcavationEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof PickaxeItem;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != ModEnchantments.BLAST;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public int getMaxPrice() {
        return 64;
    }

    @Override
    public int getMinPrice() {
        return 45;
    }

    @Override
    public int getMaxLevelToGet() {
        return this.getMaxLevel();
    }

    private final static List<Block> whitelistedBlocks = new ArrayList<>(){{
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
