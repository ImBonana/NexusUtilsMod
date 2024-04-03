package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.networking.ModPackets;
import me.imbanana.nexusutils.util.accessors.IPlayerInventory;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract PlayerInventory getInventory();

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

    @Unique
    private void tryToSyncBackpack() {
        ItemStack backpack = ((IPlayerInventory) this.getInventory()).nexusUtils$getBackpackItemStack();

        if(!ItemStack.areEqual(syncedBackpack.get(0), backpack)) {
            syncBackpack();
        }
    }

    @Unique
    private void syncBackpack() {
        ItemStack backpack = ((IPlayerInventory) this.getInventory()).nexusUtils$getBackpackItemStack();

        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeInt(this.getId());
        packet.writeItemStack(backpack);

        PlayerLookup.around((ServerWorld) this.getWorld(), this.getPos(), 64).forEach(serverPlayerEntity -> {
            if(serverPlayerEntity.getId() != this.getId()) {
                ServerPlayNetworking.send(serverPlayerEntity, ModPackets.SYNC_BACKPACK, packet);
            }
        });
    }
}
