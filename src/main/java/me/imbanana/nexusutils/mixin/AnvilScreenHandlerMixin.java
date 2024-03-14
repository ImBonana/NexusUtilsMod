package me.imbanana.nexusutils.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.*;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {


    @Shadow
    @Nullable
    private static String sanitize(String name) {
        return null;
    }

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Redirect(method = "setNewItemName", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/AnvilScreenHandler;sanitize(Ljava/lang/String;)Ljava/lang/String;"))
    public String sanitizeRedirect(String name) {
        String s = sanitize(name);
        if(s == null) return null;
        return s.replace("\\&", "§");
    }

    @Redirect(method = "setNewItemName", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;isBlank(Ljava/lang/String;)Z"))
    public boolean isBlankRedirect(String name) {
        return Util.isBlank(sanitize(name));
    }

    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;set(I)V"))
    public void setCost(Property instance, int i) {
        instance.set(Math.min(i, 39));
    }
}
