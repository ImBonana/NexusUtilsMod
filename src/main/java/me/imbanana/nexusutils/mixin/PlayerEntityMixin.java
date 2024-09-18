package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.entity.ModEntityAttributes;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.networking.ModNetwork;
import me.imbanana.nexusutils.networking.packets.SyncBackpackPacket;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract PlayerInventory getInventory();

    @Unique
    private BlockPos lastStandSafeBlock;

    @Unique
    private final DefaultedList<ItemStack> syncedBackpack = DefaultedList.ofSize(1, ItemStack.EMPTY);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void injectTick(CallbackInfo ci) {
        if(!this.getWorld().isClient) {
            this.tryToSyncBackpack();
        }
    }

    @Inject(method = "createPlayerAttributes", at = @At("RETURN"), cancellable = true)
    private static void injectCreatePlayerAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.setReturnValue(
                cir.getReturnValue()
                .add(ModEntityAttributes.GENERIC_JUMP_COUNT, 1)
        );
    }

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
                player.teleport(lastStandSafeBlock.getX(), lastStandSafeBlock.getY(), lastStandSafeBlock.getZ(), false);
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
    private void tryToSyncBackpack() {
        ItemStack backpack = this.getInventory().nexusUtils$getBackpackItemStack();

        if(!ItemStack.areEqual(syncedBackpack.getFirst(), backpack)) {
            syncBackpack();
        }
    }

    @Unique
    private void syncBackpack() {
        ItemStack backpack = this.getInventory().nexusUtils$getBackpackItemStack();

        PlayerLookup.around((ServerWorld) this.getWorld(), this.getPos(), 64).forEach(serverPlayerEntity -> {
            if(serverPlayerEntity.getId() != this.getId()) {
                ModNetwork.NETWORK_CHANNEL.serverHandle(serverPlayerEntity).send(new SyncBackpackPacket(this.getId(), backpack));
            }
        });
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
