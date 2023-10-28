package me.imbanana.nexusutils.item.custom;

import net.minecraft.enchantment.KnockbackEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.*;

public class MobGrabberItem extends Item {
    public MobGrabberItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(user.hasPassengers()) removePassengers(user);
        if(entity.startRiding(user)) {
            user.getItemCooldownManager().set(user.getStackInHand(hand).getItem(), 2);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.hasPassengers()) {
            removePassengers(user);
            user.getItemCooldownManager().set(user.getStackInHand(hand).getItem(), 2);
            return TypedActionResult.success(user.getStackInHand(hand), true);
        }

        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    void removePassengers(PlayerEntity player) {
        List<Entity> toRemove = new ArrayList<>();

        if(player.hasPassengers()) {
            for(Entity entity : player.getPassengerList())
                toRemove.add(entity);

            player.removeAllPassengers();

            for(Entity entity : toRemove)
                entity.setVelocity(player.getRotationVector().multiply(1));
        }
    }
}
