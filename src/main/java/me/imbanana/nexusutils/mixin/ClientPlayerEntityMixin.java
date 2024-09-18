package me.imbanana.nexusutils.mixin;

import com.mojang.authlib.GameProfile;
import me.imbanana.nexusutils.entity.ModEntityAttributes;
import me.imbanana.nexusutils.networking.ModNetwork;
import me.imbanana.nexusutils.networking.packets.AirJumpPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    @Shadow public Input input;


    @Unique
    private int jumpLeft;
    @Unique
    private boolean jumpedLastTick = false;

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void injectTickMovement(CallbackInfo ci) {
        if(this.isOnGround() || this.isClimbing()) {
            this.nexusutils$resetJumps();
        } else if(this.input.jumping && !this.getAbilities().flying && this.nexusutils$canJump()) {
            this.nexusutils$jump();
        }

        jumpedLastTick = this.input.jumping;
    }

    @Unique
    private void nexusutils$jump() {
        this.jump();
        if(this.nexusutils$getMaximumJumps() != jumpLeft)
            ModNetwork.NETWORK_CHANNEL.clientHandle().send(new AirJumpPacket(this.getId()));
        this.jumpLeft--;
    }

    @Unique
    private void nexusutils$resetJumps() {
        this.jumpLeft = nexusutils$getMaximumJumps();
    }

    @Unique
    private int nexusutils$getMaximumJumps() {
        return (int) this.getAttributeValue(ModEntityAttributes.GENERIC_JUMP_COUNT);
    }

    @Unique
    private boolean nexusutils$wearingUsableElytra() {
        ItemStack chestItemStack = this.getEquippedStack(EquipmentSlot.CHEST);
        return chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isUsable(chestItemStack);
    }

    @Unique
    private boolean nexusutils$canJump() {
        boolean canJumpBasedOnState = !nexusutils$wearingUsableElytra() && !isFallFlying() && !this.hasVehicle() && !this.isTouchingWater() && !this.hasStatusEffect(StatusEffects.LEVITATION);
        return canJumpBasedOnState && jumpLeft > 0 && !jumpedLastTick;
    }
}
