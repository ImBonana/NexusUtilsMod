package me.imbanana.nexusutils.mixin;

import com.google.common.collect.ImmutableList;
import me.imbanana.nexusutils.util.accessors.IPlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements Inventory, Nameable, IPlayerInventory {
    @Mutable
    @Shadow @Final private List<DefaultedList<ItemStack>> combinedInventory;
    @Unique
    private final DefaultedList<ItemStack> backpack = DefaultedList.ofSize(1, ItemStack.EMPTY);

    @Override
    public ItemStack nexusUtils$getBackpackItemStack() {
        return backpack.get(0);
    }

    @Override
    public void nexusUtils$setBackpackItemStack(ItemStack itemStack) {
        backpack.set(0, itemStack);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void Inject$Init(PlayerEntity player, CallbackInfo info) {
        this.combinedInventory = new ArrayList<>(combinedInventory);
        this.combinedInventory.add(backpack);
        this.combinedInventory = ImmutableList.copyOf(this.combinedInventory);
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void injectWriteNbt(NbtList nbtList, CallbackInfoReturnable<NbtList> info) {
        if (!this.backpack.get(0).isEmpty())  {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)(160));
            this.backpack.get(0).writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }

    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    private void injectReadNbt(NbtList nbtList, CallbackInfo info) {
        this.backpack.clear();

        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound compoundTag = nbtList.getCompound(i);
            int slot = compoundTag.getByte("Slot") & 255;
            ItemStack itemStack = ItemStack.fromNbt(compoundTag);
            if (!itemStack.isEmpty()) {
                if (slot >= 160 && slot < this.backpack.size() + 160) {
                    this.backpack.set(slot - 160, itemStack);
                }
            }
        }
    }

    @Inject(method = "size", at = @At("HEAD"), cancellable = true)
    public void injectSize(CallbackInfoReturnable<Integer> info) {
        int size = 0;
        for (DefaultedList<ItemStack> list : combinedInventory) {
            size += list.size();
        }
        info.setReturnValue(size);
    }

    @Inject(method = "isEmpty", at = @At("HEAD"), cancellable = true)
    private void injectIsEmpty(CallbackInfoReturnable<Boolean> info) {
        if(!backpack.isEmpty()) info.setReturnValue(false);
    }
}
