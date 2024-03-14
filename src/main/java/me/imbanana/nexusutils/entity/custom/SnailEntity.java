package me.imbanana.nexusutils.entity.custom;

import me.imbanana.nexusutils.entity.ModEntities;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.tags.ModEntitySpawnTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class SnailEntity extends AnimalEntity {
    public SnailEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new AnimalMateGoal(this, 1.15d));
        this.goalSelector.add(1, new TemptGoal(this, 1.15d, Ingredient.ofItems(Items.KELP), false));

        this.goalSelector.add(2, new FollowParentGoal(this, 1.15d));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.15d));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 4f));
        this.goalSelector.add(3, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createSnailAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 3)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.KELP);
    }

    public static boolean canSpawn(EntityType<? extends AnimalEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(ModEntitySpawnTags.SNAIL_SPAWN_BLOCKS) && SnailEntity.isLightLevelValidForNaturalSpawn(world, pos);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(player.getStackInHand(hand).isEmpty()) {
            player.setStackInHand(hand, new ItemStack(ModItems.SNAIL_ITEM, 1));
            this.remove(RemovalReason.DISCARDED);
            return ActionResult.SUCCESS;
        }
        return super.interactMob(player, hand);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.SNAIL.create(world);
    }
}
