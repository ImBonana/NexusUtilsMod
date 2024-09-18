package me.imbanana.nexusutils.mixin;

import com.mojang.datafixers.util.Pair;
import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import me.imbanana.nexusutils.enchantment.custom.TimberEnchantment;
import me.imbanana.nexusutils.util.BlockBreaker;
import me.imbanana.nexusutils.util.BlockFinder;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {

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

        if (EnchantmentHelper.hasAnyEnchantmentsWith(heldStack, ModEnchantmentEffectComponentTypes.BLAST)) {
            if(!this.nexusutils$isMining) {
                List<BlockPos> posList = BlockFinder.findPositions(world, player, 1, 0);

                if(serverBreakBlocks(posList, pos, heldStack)) cir.setReturnValue(true);
            }

            if(this.nexusutils$isMining) cir.setReturnValue(true);
        }

        Pair<Unit, Integer> timberEnchantment = EnchantmentHelper.getEffectListAndLevel(heldStack, ModEnchantmentEffectComponentTypes.TIMBER);

        if(timberEnchantment != null && TimberEnchantment.canBreak(blockState.getBlock())) {
            if(!this.nexusutils$isMining) {
                List<BlockPos> posList = BlockFinder.getVeinBlocks(blockState.getBlock(), pos, world, 10 + (5 * timberEnchantment.getSecond()));

                if(serverBreakBlocks(posList, pos, heldStack)) cir.setReturnValue(true);
            }

            if(this.nexusutils$isMining) cir.setReturnValue(true);
        }

        Pair<Unit, Integer> oreExcavation = EnchantmentHelper.getEffectListAndLevel(heldStack, ModEnchantmentEffectComponentTypes.ORE_EXCAVATION);

        if(oreExcavation != null) {
            if(!this.nexusutils$isMining) {
                List<BlockPos> posList = BlockFinder.getVeinBlocks(blockState.getBlock(), pos, world, 10 + (5 * oreExcavation.getSecond()));

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
