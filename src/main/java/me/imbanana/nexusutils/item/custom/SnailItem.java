package me.imbanana.nexusutils.item.custom;

import me.imbanana.nexusutils.entity.ModEntities;
import me.imbanana.nexusutils.entity.custom.SnailEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.event.GameEvent;

import java.util.Objects;

public class SnailItem extends Item implements Equipment {
    public SnailItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient) {
            ItemStack itemStack = context.getStack();

            BlockPos blockPos = context.getBlockPos();
            BlockState state = context.getWorld().getBlockState(blockPos);
            Direction direction = context.getSide();
            BlockPos blockPos2 = state.getCollisionShape(context.getWorld(), blockPos).isEmpty() ? blockPos : blockPos.offset(direction);

            if(ModEntities.SNAIL.spawnFromItemStack((ServerWorld) context.getWorld(), itemStack, context.getPlayer(), blockPos2, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP) != null) {
                itemStack.decrement(1);
                context.getWorld().emitGameEvent((Entity)context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }
}
