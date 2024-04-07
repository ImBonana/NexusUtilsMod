package me.imbanana.nexusutils.mixin;

import net.minecraft.block.*;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public abstract class BoneMealMixin {
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    protected void InjectBoneMealUse(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);

        if (canUse(world, blockPos)) {
            if (!world.isClient) {
                BlockPos newPos = getHighestBlockFromType(world, blockPos);

                if(!world.getBlockState(newPos).isAir()) return;
                world.setBlockState(newPos, blockState.getBlock().getDefaultState(), Block.NOTIFY_LISTENERS);

                if(world.getBlockState(blockPos).getBlock() instanceof CactusBlock cactusBlock) {
                    if(!cactusBlock.canPlaceAt(blockState, world, newPos)) {
                        world.breakBlock(newPos, true);
                    }
                }

                context.getStack().decrement(1);
                context.getPlayer().emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
                world.syncWorldEvent(WorldEvents.BONE_MEAL_USED, blockPos, 0);
            }
            info.setReturnValue(ActionResult.success(world.isClient));
        }
    }

    @Unique
    private boolean canUse(World world, BlockPos blockPos) {
        Block block = world.getBlockState(blockPos).getBlock();
        if(block.getDefaultState().isAir()) return false;

        return block == Blocks.BAMBOO || block == Blocks.SUGAR_CANE || block == Blocks.CACTUS;
    }

    @Unique
    private BlockPos getHighestBlockFromType(World world, BlockPos pos) {
        for (int i = 0; i < (world.getTopY() - pos.getY()); i++) {
            if(world.getBlockState(pos.up(i)).getBlock() != world.getBlockState(pos).getBlock())
                return pos.up(i);
        }

        return pos;
    }
}
