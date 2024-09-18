package me.imbanana.nexusutils.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.imbanana.nexusutils.item.custom.TerroristDogItem;
import me.imbanana.nexusutils.util.ITerroristable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity implements Angerable, ITerroristable {
    @Unique
    private static final TrackedData<Boolean> HAS_BOMB_BELT = DataTracker.registerData(WolfEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    private final String bombBeltId = "BombBelt";

    protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void injectInitDataTracker(CallbackInfo ci, @Local(argsOnly = true) DataTracker.Builder builder) {
        builder.add(HAS_BOMB_BELT, false);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readNbt(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains(bombBeltId)) {
            this.nexusUtils$setBombBelt(nbt.getBoolean(bombBeltId));
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean(bombBeltId, this.nexusUtils$hasBombBelt());
    }

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/WolfEntity;setSitting(Z)V", ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    private void injectIntreractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);

        if(itemStack.getItem() instanceof TerroristDogItem) {
            ActionResult actionResult = itemStack.useOnEntity(player, this, hand);
            if(actionResult.isAccepted()) cir.setReturnValue(actionResult);
        }
    }

    @Override
    public boolean nexusUtils$hasBombBelt() {
        return this.dataTracker.get(HAS_BOMB_BELT);
    }

    @Override
    public void nexusUtils$setBombBelt(boolean value) {
        this.dataTracker.set(HAS_BOMB_BELT, value);
    }


    @Override
    public void nexusUtils$goBoom() {
        if(this.getWorld().isClient) return;
        this.dead = true;
        this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 3, World.ExplosionSourceType.MOB);
        this.kill();
    }
}
