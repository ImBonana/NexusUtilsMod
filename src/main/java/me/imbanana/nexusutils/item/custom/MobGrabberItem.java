package me.imbanana.nexusutils.item.custom;

import me.imbanana.nexusutils.NexusUtils;
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
    public static final int COOLDOWN = 2;

    public MobGrabberItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity instanceof PlayerEntity) return ActionResult.PASS;
        if(user.getItemCooldownManager().isCoolingDown(this)) return ActionResult.PASS;
        List<Entity> toRemove = new ArrayList<>();

        if(user.hasPassengers()) toRemove.addAll(user.getPassengerList());

        if(entity.startRiding(user, true)) {
            user.getItemCooldownManager().set(user.getStackInHand(hand).getItem(), COOLDOWN);
            removePassengers(user, toRemove);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.pass(user.getStackInHand(hand));

        if(user.hasPassengers()) {
            removePassengers(user);
            user.getItemCooldownManager().set(user.getStackInHand(hand).getItem(), COOLDOWN);
            return TypedActionResult.success(user.getStackInHand(hand), true);
        }

        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    private void removePassengers(PlayerEntity player) {
        List<Entity> toRemove = new ArrayList<>(player.getPassengerList());

        player.removeAllPassengers();

        for(Entity entity : toRemove)
            entity.setVelocity(player.getRotationVector().multiply(1));
    }

    private void removePassengers(PlayerEntity player, List<Entity> toRemove) {
        for(Entity entity : toRemove) {
            entity.stopRiding();
            entity.setVelocity(player.getRotationVector().multiply(1));
        }
    }
}
