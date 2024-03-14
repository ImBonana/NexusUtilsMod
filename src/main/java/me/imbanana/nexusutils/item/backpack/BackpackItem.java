package me.imbanana.nexusutils.item.backpack;

import me.imbanana.nexusutils.util.accessors.ILivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BackpackItem extends Item {
    public static final int SLOT_ID = 41;
    public static final int X_POS = 77;
    public static final int Y_POS = 44;
//    public static final int CREATIVE_X_POS = 127;
//    public static final int CREATIVE_Y_POS = 20;

    public static final int CREATIVE_X_POS = -2000;
    public static final int CREATIVE_Y_POS = -2000;

    public static final int INVENTORY_SIZE = 9*3;

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

        ItemStack previousStack = user.getInventory().getStack(SLOT_ID);

        if(previousStack.isEmpty()) {
            ItemStack newStack = stack.copy();
            ((ILivingEntity) user).nexusutils$onEquipBackpack(newStack, previousStack);
            user.getInventory().setStack(SLOT_ID, newStack.copy());

            stack.setCount(0);
        }

        return TypedActionResult.success(stack);
    }
}
