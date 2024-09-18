package me.imbanana.nexusutils.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.imbanana.nexusutils.block.custom.SleepingBagBlock;
import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import me.imbanana.nexusutils.util.ModEnchantmentHelper;
import me.imbanana.nexusutils.util.SitHandler;
import me.imbanana.nexusutils.util.accessors.ILivingEntity;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.function.Consumer;

@Mixin(value = LivingEntity.class, priority = 800)
public abstract class LivingEntityMixin extends Entity implements ILivingEntity {
    @Shadow @Nullable protected PlayerEntity attackingPlayer;

    @Shadow public abstract long getLootTableSeed();

    @Shadow public abstract RegistryKey<LootTable> getLootTable();

    @Shadow public abstract boolean isExperienceDroppingDisabled();

    @Shadow public abstract int getXpToDrop(ServerWorld world, @Nullable Entity attacker);

    @Shadow protected abstract void dropInventory();

    @Shadow protected abstract void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer);

    @Shadow protected abstract boolean shouldDropLoot();

    @Shadow protected abstract boolean shouldAlwaysDropXp();

    @Shadow public abstract boolean shouldDropXp();

    @Shadow protected int playerHitTimer;

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract boolean clearStatusEffects();

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract ItemStack getStackInHand(Hand hand);

    @Shadow protected abstract void setPositionInBed(BlockPos pos);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "onDismounted", at = @At(value = "TAIL"))
    protected void InjectOnDismount(Entity vehicle, CallbackInfo ci) {
        if(!(vehicle instanceof DisplayEntity.TextDisplayEntity)) return;
        SitHandler.removeSeat(vehicle.getId());
        this.requestTeleport(vehicle.getBlockX(), vehicle.getBlockY() + 1, vehicle.getBlockZ());
    }

    @Inject(
            method = "sleep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;",
                    shift = At.Shift.BEFORE
            )
    )
    private void injectBeforeSleep(BlockPos pos, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        BlockState blockState = livingEntity.getWorld().getBlockState(pos);
        if (blockState.getBlock() instanceof SleepingBagBlock) {
            livingEntity.getWorld().setBlockState(pos, blockState.with(SleepingBagBlock.OCCUPIED, true), Block.NOTIFY_ALL);
        }
    }

    @Redirect(method = "sleep", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setPositionInBed(Lnet/minecraft/util/math/BlockPos;)V"))
    private void test(LivingEntity instance, BlockPos pos) {
        BlockState blockState = this.getWorld().getBlockState(pos);

        if(blockState.getBlock() instanceof SleepingBagBlock) {
            this.setPositionInSleepingBag(pos);
            return;
        }

        this.setPositionInBed(pos);
    }

    @Redirect(method = "getSleepingDirection", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BedBlock;getDirection(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/Direction;"))
    private Direction redirectGetSleepingDirection_GetDirection(BlockView world, BlockPos pos) {
        Direction direction = SleepingBagBlock.getDirection(this.getWorld(), pos);
        return direction == null ? BedBlock.getDirection(world, pos) : direction;
    }

    @Inject(method = "isSleepingInBed", at = @At(value = "HEAD"), cancellable = true)
    private void injectIsSleepingInBed(CallbackInfoReturnable<Boolean> info) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        info.setReturnValue(livingEntity.getSleepingPosition()
            .map(pos ->
                livingEntity.getWorld().getBlockState(pos).getBlock() instanceof BedBlock
                || livingEntity.getWorld().getBlockState(pos).getBlock() instanceof SleepingBagBlock
            )
            .orElse(false));
    }

    @Inject(method = "method_18404", at = @At(value = "TAIL"))
    private void injectWakeUp(BlockPos pos, CallbackInfo ci, @Local BlockState blockState) {
        if(blockState.getBlock() instanceof SleepingBagBlock) {
            Direction direction = blockState.get(SleepingBagBlock.FACING);
            this.getWorld().setBlockState(pos, blockState.with(SleepingBagBlock.OCCUPIED, false), Block.NOTIFY_ALL);
            Vec3d vec3d = SleepingBagBlock.findWakeUpPosition(this.getType(), this.getWorld(), pos, direction, this.getYaw()).orElseGet(() -> {
                BlockPos blockPos2 = pos.up();
                return new Vec3d((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.1, (double)blockPos2.getZ() + 0.5);
            });
            Vec3d vec3d2 = Vec3d.ofBottomCenter(pos).subtract(vec3d).normalize();
            float f = (float) MathHelper.wrapDegrees(MathHelper.atan2(vec3d2.z, vec3d2.x) * 57.2957763671875 - 90.0);
            this.setPosition(vec3d.x, vec3d.y, vec3d.z);
            this.setYaw(f);
            this.setPitch(0.0f);
        }
    }

    @Inject(
            method = "handleFallDamage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;computeFallDamage(FF)I", shift = At.Shift.AFTER)
    )
    private void InjectHandleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
        ItemStack boots = this.getEquippedStack(EquipmentSlot.FEET);

        if (this.getWorld() instanceof ServerWorld serverWorld) {
            ModEnchantmentHelper.applyEntityEffects(serverWorld, (LivingEntity) (Object) this, this.getPos(), boots, EquipmentSlot.FEET, ModEnchantmentEffectComponentTypes.ON_FALL);
        }
    }

    @Inject(method = "tryUseTotem", at = @At(value = "TAIL"), cancellable = true)
    private void InjectTryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> info) {
        ItemStack helmet = this.getEquippedStack(EquipmentSlot.HEAD);

        ItemStack itemStack = null;
        for (Hand hand : Hand.values()) {
            ItemStack itemStack2 = this.getStackInHand(hand);
            if (!itemStack2.isOf(Items.TOTEM_OF_UNDYING)) continue;
            itemStack = itemStack2.copy();
            itemStack2.decrement(1);
            break;
        }

        if(itemStack == null && helmet != null) {

            if(EnchantmentHelper.hasAnyEnchantmentsWith(helmet, ModEnchantmentEffectComponentTypes.PHOENIX)) {
                if(new Random().nextInt(1, 11) == 1) {
                    this.setHealth(1.0f);
                    this.clearStatusEffects();
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
                    this.getWorld().sendEntityStatus(this, EntityStatuses.USE_TOTEM_OF_UNDYING);

                    info.setReturnValue(true);
                }
            }
        }
    }

    @Inject(method = "drop", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;shouldDropLoot()Z", shift = At.Shift.BEFORE), cancellable = true)
    private void InjectDrop(ServerWorld world, DamageSource source, CallbackInfo info) {
        boolean bl = this.playerHitTimer > 0;

        Entity entity = source.getAttacker();

        if(entity instanceof PlayerEntity player) {

            ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);

            boolean hasTelepathy = false;

            if(source.getSource() instanceof TridentEntity tridentEntity) {
                ItemStack tridentStack = tridentEntity.nexusUtils$getTridentItemStack();

                if(tridentStack != null) hasTelepathy = EnchantmentHelper.hasAnyEnchantmentsWith(tridentStack, ModEnchantmentEffectComponentTypes.TELEPARHY);
            } else if(itemStack != null) {
                hasTelepathy = EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, ModEnchantmentEffectComponentTypes.TELEPARHY);
            }

            if (hasTelepathy) {
                if (this.shouldDropLoot() && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                    getDropLoot(source, bl, (item) -> player.getInventory().offerOrDrop(item));
                    this.dropEquipment(world, source, bl);
                }

                this.dropInventory();
                if (this.getWorld() instanceof ServerWorld && !this.isExperienceDroppingDisabled() && (this.shouldAlwaysDropXp() || this.playerHitTimer > 0 && this.shouldDropXp() && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT))) {
                    player.addExperience(this.getXpToDrop(world, source.getAttacker()));
                }

                info.cancel();
            }
        }
    }

    @Override
    public void nexusutils$onEquipBackpack(ItemStack stack, ItemStack previousStack) {
        if ((stack.isEmpty() && previousStack.isEmpty()) || ItemStack.areItemsAndComponentsEqual(stack, previousStack) || this.firstUpdate) {
            return;
        }

        if (!this.getWorld().isClient() && !this.isSpectator()) {
            if (!this.isSilent()) {
                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, this.getSoundCategory(), 1.0f, 1.0f);
            }
        }
    }

    @Unique
    private void getDropLoot(DamageSource damageSource, boolean causedByPlayer, Consumer<ItemStack> loot) {
        RegistryKey<LootTable> lootTableRegistryKey = this.getLootTable();
        MinecraftServer server = this.getWorld().getServer();
        if(server == null) return;
        LootTable lootTable = server.getReloadableRegistries().getLootTable(lootTableRegistryKey);
        LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld)this.getWorld())
                .add(LootContextParameters.THIS_ENTITY, this)
                .add(LootContextParameters.ORIGIN, this.getPos())
                .add(LootContextParameters.DAMAGE_SOURCE, damageSource)
                .addOptional(LootContextParameters.ATTACKING_ENTITY, damageSource.getAttacker())
                .addOptional(LootContextParameters.DIRECT_ATTACKING_ENTITY, damageSource.getSource());

        if (causedByPlayer && this.attackingPlayer != null) {
            builder = builder.add(LootContextParameters.LAST_DAMAGE_PLAYER, this.attackingPlayer).luck(this.attackingPlayer.getLuck());
        }
        LootContextParameterSet lootContextParameterSet = builder.build(LootContextTypes.ENTITY);
        lootTable.generateLoot(lootContextParameterSet, this.getLootTableSeed(), loot);
    }

    @Unique
    private void setPositionInSleepingBag(BlockPos pos) {
        this.setPosition((double)pos.getX() + 0.5, (double)pos.getY() + 0.325, (double)pos.getZ() + 0.5);
    }
}
