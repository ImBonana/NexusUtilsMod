package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import me.imbanana.nexusutils.components.custom.FluidTanksComponent;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import me.imbanana.nexusutils.item.custom.BackpackUpgradeItem;
import me.imbanana.nexusutils.screen.backpack.BackpackInventory;
import me.imbanana.nexusutils.screen.backpack.FluidTank;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    @Shadow
    @Nullable
    private static String sanitize(String name) {
        return null;
    }

    @Shadow @Final private Property levelCost;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Redirect(method = "setNewItemName", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/AnvilScreenHandler;sanitize(Ljava/lang/String;)Ljava/lang/String;"))
    public String sanitizeRedirect(String name) {
        String s = sanitize(name);
        if(s == null) return null;
        return s.replace("\\&", "§");
    }

    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;set(I)V"))
    public void setCost(Property instance, int i) {
        instance.set(Math.min(i, 39));
    }

    @Inject(method = "updateResult", at = @At(value = "HEAD"), cancellable = true)
    public void injectUpdateResult(CallbackInfo ci) {
        ItemStack input = this.input.getStack(0);
        ItemStack inputAddition = this.input.getStack(1);

        if(input.isEmpty()) return;
        if(!(input.getItem() instanceof BackpackItem)) return;

        if(inputAddition.isEmpty()) return;
        if(!(inputAddition.getItem() instanceof BackpackUpgradeItem backpackUpgradeItem)) return;

        BackpackTierComponent.Tier currentTier = input.getOrDefault(ModComponents.BACKPACK_TIER, BackpackTierComponent.createDefaultBackpackTier()).tier();
        if (currentTier.asNumber() >= backpackUpgradeItem.getTier().asNumber()) return;

        ItemStack outputStack = input.copy();
        outputStack.set(ModComponents.BACKPACK_TIER, new BackpackTierComponent(backpackUpgradeItem.getTier()));

        long capacity = BackpackInventory.getCapacity(backpackUpgradeItem.getTier());
        FluidTanksComponent fluidTanksComponent = outputStack.getOrDefault(ModComponents.FLUID_TANKS, FluidTanksComponent.createTanks(capacity));
        FluidTank leftTank = FluidTank.of(fluidTanksComponent.leftTank(), capacity);
        FluidTank rightTank = FluidTank.of(fluidTanksComponent.rightTank(), capacity);

        outputStack.set(ModComponents.FLUID_TANKS, new FluidTanksComponent(
                FluidTanksComponent.Tank.of(leftTank),
                FluidTanksComponent.Tank.of(rightTank)
        ));

        output.setStack(0, outputStack);
        this.levelCost.set(1);
        this.sendContentUpdates();
        ci.cancel();
    }

    @Redirect(method = "onTakeOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;setStack(ILnet/minecraft/item/ItemStack;)V", ordinal = 3))
    private void redirectOnTakeOutput(Inventory instance, int i, ItemStack ignore) {
        ItemStack stack = this.input.getStack(1);

        if(!(stack.getItem() instanceof BackpackUpgradeItem)) instance.setStack(i, ignore);

        stack.decrement(1);
        instance.setStack(i, stack);
    }
}
