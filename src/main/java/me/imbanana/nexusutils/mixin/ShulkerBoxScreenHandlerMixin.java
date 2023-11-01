package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.util.IExtendedShulkerBoxScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShulkerBoxScreenHandler.class)
public abstract class ShulkerBoxScreenHandlerMixin implements IExtendedShulkerBoxScreenHandler {
    @Shadow @Final private Inventory inventory;

    public Inventory getInventory() {
        return this.inventory;
    }
}
