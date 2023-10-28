package me.imbanana.nexusutils.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ModTestItem extends Item {
    public ModTestItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
//            MinerUtils.breakAllBlocks(world, user.getBlockPos(), Blocks.GLASS, 15);
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
//        NexusUtils.LOGGER.info("Using");
//        MinerUtils.breakAllBlocks(context.getWorld(), context.getBlockPos(), context.getWorld().getBlockState(context.getBlockPos()).getBlock(), context.getPlayer(), 30, true);
        return ActionResult.PASS;
    }
}
