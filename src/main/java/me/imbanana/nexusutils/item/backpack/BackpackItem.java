package me.imbanana.nexusutils.item.backpack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BackpackItem extends Item {
    public static final int INVENTORY_SIZE = 9*3;
    public static final long CAPACITY = 4000;

    public BackpackItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canBeNested() {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if(!user.getInventory().nexusUtils$isBackapckEquipped()) {
            ItemStack newStack = stack.copy();
            user.getInventory().nexusUtils$setBackpackItemStack(newStack);
            user.nexusutils$onEquipBackpack(newStack, ItemStack.EMPTY);

            stack.setCount(0);
        }

        return TypedActionResult.success(stack);
    }
}
