package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.util.accessors.IShulkerBoxScreenHandler;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ShulkerBoxScreenHandler.class)
public abstract class ShulkerBoxScreenHandlerMixin extends ScreenHandler implements IShulkerBoxScreenHandler {
    @Shadow @Final private Inventory inventory;

    protected ShulkerBoxScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    public Inventory nexusUtils$getInventory() {
        return this.inventory;
    }
}
