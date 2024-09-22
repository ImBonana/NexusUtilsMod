package me.imbanana.nexusutils.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemEnchantmentsComponent.class)
public abstract class ItemEnchantmentsComponentMixin implements TooltipAppender {

    @Inject(method = "appendTooltip", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V", shift = At.Shift.AFTER))
    private void injectAppendTooltip_partOne(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type, CallbackInfo ci, @Local RegistryEntry<Enchantment> registryEntry) {
        if(registryEntry.getKey().isPresent()) {
            tooltip.accept(Text.literal("◦ ").append(Text.translatable(Util.createTranslationKey("enchantment", registryEntry.getKey().get().getValue()) + ".desc")).formatted(Formatting.DARK_GRAY));
        }
    }
}
