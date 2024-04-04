package me.imbanana.nexusutils.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Unique
    private static final FoodComponent QUARTZ_FOOD_COMPONENT = new FoodComponent.Builder()
            .alwaysEdible()
            .hunger(2)
            .saturationModifier(1)
            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200), 1f)
            .build();

    @Inject(method = "isFood", at = @At("HEAD"), cancellable = true)
    private void injectInit(CallbackInfoReturnable<Boolean> cir) {
        Item item = (Item) (Object) this;
        if(item == Items.QUARTZ) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getFoodComponent", at = @At("HEAD"), cancellable = true)
    private void injectGetFoodComponent(CallbackInfoReturnable<FoodComponent> cir) {
        Item item = (Item) (Object) this;
        if(item == Items.QUARTZ) {
            cir.setReturnValue(QUARTZ_FOOD_COMPONENT);
        }
    }
}
