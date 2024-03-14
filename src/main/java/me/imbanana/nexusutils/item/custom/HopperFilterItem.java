package me.imbanana.nexusutils.item.custom;

import me.imbanana.nexusutils.screen.hopperfilter.HopperFilterScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HopperFilterItem extends Item {
    public static final int INVENTORY_SIZE = 18;

    public HopperFilterItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack filter = user.getStackInHand(hand);

        user.openHandledScreen(new HopperFilterScreenHandlerFactory(filter));

        return TypedActionResult.pass(filter);
    }
}
