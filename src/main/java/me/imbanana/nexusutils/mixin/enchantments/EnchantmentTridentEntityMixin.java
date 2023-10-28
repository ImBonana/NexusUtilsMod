package me.imbanana.nexusutils.mixin.enchantments;

import me.imbanana.nexusutils.util.TridentEntityItemStackGetter;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TridentEntity.class)
public abstract class EnchantmentTridentEntityMixin implements TridentEntityItemStackGetter {
    @Shadow protected abstract ItemStack asItemStack();

    @Override
    public ItemStack getTridentItemStack() {
        return this.asItemStack();
    }
}
