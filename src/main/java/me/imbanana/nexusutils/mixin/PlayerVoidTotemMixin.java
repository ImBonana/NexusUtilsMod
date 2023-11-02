package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerVoidTotemMixin {
    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Unique
    private BlockPos lastStandSafeBlock;

    @Inject(method = "tickMovement", at = @At("TAIL"))
    protected void InjectPlayerMoveEvent(CallbackInfo info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        BlockState blockState = player.getWorld().getBlockState(player.getBlockPos().down(1));
        if(!blockState.isAir() && !blockState.isLiquid()) {
            lastStandSafeBlock = player.getBlockPos();
        }
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    protected void InjectPlayerDeath(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if((player.getHealth() - amount) <= 0f) {
            if(canActiveVoidTotem()) {
                player.fallDistance = 0;
                player.teleport(lastStandSafeBlock.getX(), lastStandSafeBlock.getY(), lastStandSafeBlock.getZ());
                player.fallDistance = 0;

                getVoidTotemFromHand().decrement(1);

                player.setHealth(2f);
                player.clearStatusEffects();
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));

                if(!player.getWorld().isClient()) {
                    player.getWorld().addParticle(ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY() + 1, player.getZ(), 0, 0, 0);
                    player.getWorld().sendEntityStatus(player, EntityStatuses.USE_TOTEM_OF_UNDYING);
                }

                info.setReturnValue(true);
            }
        }
    }

    @Unique
    ItemStack getVoidTotemFromHand() {
        PlayerEntity player = (PlayerEntity) (Object) this;

        for(Hand hand : Hand.values()) {
            ItemStack itemStack = player.getStackInHand(hand);
            if(itemStack == null) continue;
            if(!itemStack.isOf(ModItems.VOID_TOTEM)) continue;
            return itemStack;
        }

        return null;
    }

    @Unique
    boolean canActiveVoidTotem() {
        PlayerEntity player = (PlayerEntity) (Object) this;

        return lastStandSafeBlock != null && getVoidTotemFromHand() != null
                && (player.getMainHandStack().isOf(ModItems.VOID_TOTEM) || (!player.getMainHandStack().isOf(Items.TOTEM_OF_UNDYING) && player.getOffHandStack().isOf(ModItems.VOID_TOTEM)))
                && ((player.getWorld().getRegistryKey().getValue().equals(DimensionTypes.OVERWORLD_ID) && player.getPos().getY() < -64)
                || (player.getWorld().getRegistryKey().getValue().equals(DimensionTypes.THE_NETHER_ID) && player.getPos().getY() < 0)
                || (player.getWorld().getRegistryKey().getValue().equals(DimensionTypes.THE_END_ID) && player.getPos().getY() < 0));
    }
}
