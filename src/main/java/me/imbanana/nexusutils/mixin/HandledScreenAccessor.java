package me.imbanana.nexusutils.mixin;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HandledScreen.class)
public interface HandledScreenAccessor {
    @Accessor(value = "x")
    int getX();

    @Accessor(value = "y")
    int getY();

    @Accessor
    int getBackgroundWidth();
}
