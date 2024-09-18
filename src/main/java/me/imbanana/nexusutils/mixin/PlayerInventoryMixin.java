package me.imbanana.nexusutils.mixin;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.util.accessors.IPlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Nameable;
import org.spongepowered.asm.mixin.*;

import java.util.Optional;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements Inventory, Nameable, IPlayerInventory {
    @Shadow @Final public PlayerEntity player;

    @Override
    public ItemStack nexusUtils$getBackpackItemStack() {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(this.player);
        return component.filter(trinketComponent ->
                trinketComponent.isEquipped(ModItems.BACKPACK)
        )
        .map(trinketComponent ->
                trinketComponent.getEquipped(ModItems.BACKPACK).getFirst().getRight()
        )
        .orElse(ItemStack.EMPTY);
    }

    @Override
    public void nexusUtils$setBackpackItemStack(ItemStack itemStack) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(this.player);
        component.ifPresent(trinketComponent ->
                trinketComponent.getInventory().get("chest").get("backpack").setStack(0, itemStack));
    }

    @Override
    public boolean nexusUtils$isBackapckEquipped() {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(this.player);
        return component.isPresent() && component.get().isEquipped(ModItems.BACKPACK);
    }
}
