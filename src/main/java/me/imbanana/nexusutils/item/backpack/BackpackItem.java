package me.imbanana.nexusutils.item.backpack;

import dev.emi.trinkets.api.TrinketItem;
import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import me.imbanana.nexusutils.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class BackpackItem extends TrinketItem {
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
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if(user.getInventory().nexusUtils$isBackapckEquipped()) {
            user.setStackInHand(hand, user.getInventory().nexusUtils$getBackpackItemStack());
        }

        user.getInventory().nexusUtils$setBackpackItemStack(stack.copy());
        stack.decrement(1);

        return super.use(world, user, hand);
    }

    public static ItemStack ofTier(BackpackTierComponent.Tier tier) {
        ItemStack stack = ModItems.BACKPACK.getDefaultStack().copy();
        stack.set(ModComponents.BACKPACK_TIER, new BackpackTierComponent(tier));
        return stack;
    }
}
