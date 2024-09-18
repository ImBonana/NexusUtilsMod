package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.util.accessors.ITridentEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity implements ITridentEntity {

    protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack nexusUtils$getTridentItemStack() {
        TridentEntity tridentEntity = (TridentEntity) (Object) this;
        return tridentEntity.getItemStack().copy();
    }
}
