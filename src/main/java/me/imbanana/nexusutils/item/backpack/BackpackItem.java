package me.imbanana.nexusutils.item.backpack;

import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class BackpackItem extends Item {
    public BackpackItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canBeNested() {
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        BackpackTierComponent.Tier tier = stack.getOrDefault(ModComponents.BACKPACK_TIER, BackpackTierComponent.createDefaultBackpackTier()).tier();
        Text levelText = Text.stringifiedTranslatable(this.getTranslationKey() + ".desc.tier_" + tier.asNumber()).fillStyle(Style.EMPTY.withColor(tier.getColor()));
        tooltip.add(Text.translatable(this.getTranslationKey() + ".desc", levelText));
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
