package me.imbanana.nexusutils.item.custom;

import me.imbanana.nexusutils.screen.custom.CustomCraftingTableContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CraftingOnAStickItem extends Item {
    public CraftingOnAStickItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient())
            user.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                    (i, playerInventory, playerEntity) ->
                            new CustomCraftingTableContainer(i, playerInventory, ScreenHandlerContext.create(world, user.getBlockPos())),
                    Text.of("Crafting")
            ));
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

}
