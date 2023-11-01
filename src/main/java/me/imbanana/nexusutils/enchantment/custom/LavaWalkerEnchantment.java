package me.imbanana.nexusutils.enchantment.custom;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.block.custom.FrozenLavaBlock;
import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FrostedIceBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class LavaWalkerEnchantment extends Enchantment implements TradableEnchantment {
    public LavaWalkerEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
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

    public static void freezeLava(LivingEntity entity, World world, BlockPos blockPos, int level) {
        if (!entity.isOnGround()) {
            return;
        }
        BlockState blockState = ModBlocks.FROZEN_LAVA.getDefaultState();
        int i = Math.min(16, 2 + level);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add(-i, -1, -i), blockPos.add(i, -1, i))) {
            BlockState blockState3;
            if (!blockPos2.isWithinDistance(entity.getPos(), (double)i)) continue;
            mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
            BlockState blockState2 = world.getBlockState(mutable);
            if (!blockState2.isAir() || (blockState3 = world.getBlockState(blockPos2)) != FrozenLavaBlock.getMeltedState() || !blockState.canPlaceAt(world, blockPos2) || !world.canPlace(blockState, blockPos2, ShapeContext.absent())) continue;
            world.setBlockState(blockPos2, blockState);
            world.scheduleBlockTick(blockPos2, ModBlocks.FROZEN_LAVA, MathHelper.nextInt(entity.getRandom(), 60, 120));
        }
    }

    @Override
    public int getMaxPrice() {
        return 55;
    }

    @Override
    public int getMinPrice() {
        return 35;
    }

    @Override
    public int getMaxLevelToGet() {
        return this.getMaxLevel();
    }
}
