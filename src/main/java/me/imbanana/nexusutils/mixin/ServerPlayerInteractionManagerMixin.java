package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.enchantment.custom.OreExcavationEnchantment;
import me.imbanana.nexusutils.enchantment.custom.TimberEnchantment;
import me.imbanana.nexusutils.util.BlockBreaker;
import me.imbanana.nexusutils.util.BlockFinder;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

    @Final @Shadow protected ServerPlayerEntity player;

    @Shadow protected ServerWorld world;

    @Unique private boolean nexusutils$isMining = false;

    @Inject(
            method = "tryBreakBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/block/BlockState;"
            ),
            cancellable = true
    )
    private void tryBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        ItemStack heldStack = player.getMainHandStack();
        BlockState blockState = world.getBlockState(pos);

        int blastEnchantmentTarget = EnchantmentHelper.getLevel(ModEnchantments.BLAST, heldStack);

        if (blastEnchantmentTarget > 0) {
            if(!this.nexusutils$isMining) {
                List<BlockPos> posList = BlockFinder.findPositions(world, player, 1, 0);

                if(serverBreakBlocks(posList, pos, heldStack)) cir.setReturnValue(true);
            }

            if(this.nexusutils$isMining) cir.setReturnValue(true);
        }

        int timberEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.TIMBER, heldStack);

        if(timberEnchantmentLevel > 0 && TimberEnchantment.canBreak(blockState.getBlock())) {
            if(!this.nexusutils$isMining) {
                List<BlockPos> posList = BlockFinder.getVeinBlocks(blockState.getBlock(), pos, world, 10 + (5 * timberEnchantmentLevel));

                if(serverBreakBlocks(posList, pos, heldStack)) cir.setReturnValue(true);
            }

            if(this.nexusutils$isMining) cir.setReturnValue(true);
        }

        int oreExcavationLevel = EnchantmentHelper.getLevel(ModEnchantments.ORE_EXCAVATION, heldStack);

        if(oreExcavationLevel > 0 && OreExcavationEnchantment.canBreak(blockState.getBlock())) {
            if(!this.nexusutils$isMining) {
                List<BlockPos> posList = BlockFinder.getVeinBlocks(blockState.getBlock(), pos, world, 10 + (5 * oreExcavationLevel));

                if(serverBreakBlocks(posList, pos, heldStack)) cir.setReturnValue(true);
            }

            if(this.nexusutils$isMining) cir.setReturnValue(true);
        }
    }

    @Unique
        private boolean serverBreakBlocks(List<BlockPos> posList, BlockPos pos, ItemStack heldStack) {
        this.nexusutils$isMining = true;

        BlockBreaker.breakBlocks(world, pos, posList, player, heldStack);

        this.nexusutils$isMining = false;
        return true;
    }
}
