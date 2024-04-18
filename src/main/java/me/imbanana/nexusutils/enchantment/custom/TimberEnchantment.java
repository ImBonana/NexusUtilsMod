package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TimberEnchantment extends Enchantment implements TradableEnchantment {
    public TimberEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem;
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
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    private final static List<Block> whitelistedBlocks = new ArrayList<>(){{
        add(Blocks.ACACIA_LOG);
        add(Blocks.STRIPPED_ACACIA_LOG);
        add(Blocks.BIRCH_LOG);
        add(Blocks.STRIPPED_BIRCH_LOG);
        add(Blocks.CHERRY_LOG);
        add(Blocks.STRIPPED_CHERRY_LOG);
        add(Blocks.DARK_OAK_LOG);
        add(Blocks.STRIPPED_DARK_OAK_LOG);
        add(Blocks.JUNGLE_LOG);
        add(Blocks.STRIPPED_JUNGLE_LOG);
        add(Blocks.MANGROVE_LOG);
        add(Blocks.STRIPPED_MANGROVE_LOG);
        add(Blocks.OAK_LOG);
        add(Blocks.DARK_OAK_LOG);
        add(Blocks.SPRUCE_LOG);
        add(Blocks.STRIPPED_SPRUCE_LOG);
        add(Blocks.MANGROVE_ROOTS);
        add(Blocks.WARPED_STEM);
        add(Blocks.STRIPPED_WARPED_STEM);
        add(Blocks.CRIMSON_STEM);
        add(Blocks.STRIPPED_CRIMSON_STEM);
    }};

    public static boolean canBreak(Block block) {
        return whitelistedBlocks.contains(block);
    }

    @Override
    public int getMaxPrice() {
        return 55;
    }

    @Override
    public int getMinPrice() {
        return 40;
    }

    @Override
    public int getMaxLevelToGet() {
        return this.getMaxLevel();
    }
}
