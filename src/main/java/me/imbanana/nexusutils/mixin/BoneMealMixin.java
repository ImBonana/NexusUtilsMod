package me.imbanana.nexusutils.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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

        if (canUse(world, blockPos)) {
            if (!world.isClient) {
                world.setBlockState(getHighestBlockFromType(world, blockPos), world.getBlockState(blockPos).getBlock().getDefaultState());
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
