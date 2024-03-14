package me.imbanana.nexusutils.mixin.enchantments;

import me.imbanana.nexusutils.util.accessors.ITridentEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TridentEntity.class)
public abstract class EnchantmentTridentEntityMixin implements ITridentEntity {

    @Override
    public ItemStack nexusUtils$getTridentItemStack() {
        TridentEntity tridentEntity = (TridentEntity) (Object) this;
        return tridentEntity.getItemStack().copy();
    }
}
