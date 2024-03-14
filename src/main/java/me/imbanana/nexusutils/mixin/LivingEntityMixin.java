package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.block.custom.SleepingBagBlock;
import me.imbanana.nexusutils.util.accessors.ILivingEntity;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(value = LivingEntity.class, priority = 800)
public abstract class LivingEntityMixin extends Entity implements ILivingEntity {

    @Shadow public abstract Optional<BlockPos> getSleepingPosition();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
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

        BlockState blockState;
        if ((blockState = livingEntity.getWorld().getBlockState(pos)).getBlock() instanceof SleepingBagBlock) {
            livingEntity.getWorld().setBlockState(pos, blockState.with(SleepingBagBlock.OCCUPIED, true), Block.NOTIFY_ALL);
        }
    }

    @Inject(method = "getSleepingDirection", at = @At(value = "RETURN"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectGetSleepingDirection(CallbackInfoReturnable<Direction> info) {
        BlockPos blockPos = this.getSleepingPosition().orElse(null);
        if(blockPos == null) return;

        Direction direction = SleepingBagBlock.getDirection(this.getWorld(), blockPos);
        if(direction == null) return;

        info.setReturnValue(direction);
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

    /**
     * @author Im_Banana
     * @reason why not?
     */
    @Overwrite
    public void wakeUp() {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        livingEntity.getSleepingPosition().filter(this.getWorld()::isChunkLoaded).ifPresent(pos -> {
            BlockState blockState = this.getWorld().getBlockState(pos);
            if (blockState.getBlock() instanceof BedBlock) {
                Direction direction = blockState.get(BedBlock.FACING);
                this.getWorld().setBlockState(pos, blockState.with(BedBlock.OCCUPIED, false), Block.NOTIFY_ALL);
                Vec3d vec3d = BedBlock.findWakeUpPosition(this.getType(), this.getWorld(), pos, direction, this.getYaw()).orElseGet(() -> {
                    BlockPos blockPos2 = pos.up();
                    return new Vec3d((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.1, (double)blockPos2.getZ() + 0.5);
                });
                Vec3d vec3d2 = Vec3d.ofBottomCenter(pos).subtract(vec3d).normalize();
                float f = (float) MathHelper.wrapDegrees(MathHelper.atan2(vec3d2.z, vec3d2.x) * 57.2957763671875 - 90.0);
                this.setPosition(vec3d.x, vec3d.y, vec3d.z);
                this.setYaw(f);
                this.setPitch(0.0f);
            } else if(blockState.getBlock() instanceof SleepingBagBlock) {
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
        });
        Vec3d vec3d = this.getPos();
        this.setPose(EntityPose.STANDING);
        this.setPosition(vec3d.x, vec3d.y, vec3d.z);
        livingEntity.clearSleepingPosition();
    }

    @Override
    public void nexusutils$onEquipBackpack(ItemStack stack, ItemStack previousStack) {
        if ((stack.isEmpty() && previousStack.isEmpty()) || ItemStack.canCombine(stack, previousStack) || this.firstUpdate) {
            return;
        }

        if (!this.getWorld().isClient() && !this.isSpectator()) {
            if (!this.isSilent()) {
                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, this.getSoundCategory(), 1.0f, 1.0f);
            }
        }
    }
}
