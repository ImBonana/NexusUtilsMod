package me.imbanana.nexusutils.item.custom;

import me.imbanana.nexusutils.util.ITerroristable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class TerroristDogItem extends Item {
    public TerroristDogItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity instanceof Tameable tameable) {
            if(!user.getUuid().equals(tameable.getOwnerUuid())) return ActionResult.FAIL;
        }

        if(entity instanceof ITerroristable terrorist) {
            if(terrorist.nexusUtils$hasBombBelt()) return ActionResult.FAIL;

            terrorist.nexusUtils$setBombBelt(true);

            user.swingHand(hand, !user.getWorld().isClient);

            return ActionResult.CONSUME_PARTIAL;
        }

        return ActionResult.PASS;
    }
}
