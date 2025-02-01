package me.imbanana.nexusutils.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class TerroristDogRemoteItem extends Item {
    public TerroristDogRemoteItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        Box myBox = new Box(user.getBlockPos()).expand(100);
        List<WolfEntity> wolfTerrorits = world.getEntitiesByClass(WolfEntity.class, myBox, wolfEntity -> wolfEntity.isAlive() && user.getUuid().equals(wolfEntity.getOwnerUuid()) && wolfEntity.nexusUtils$hasBombBelt());

        for(WolfEntity wolfEntity : wolfTerrorits) wolfEntity.nexusUtils$goBoom();

        if(!world.isClient) {
            itemStack.damage(1, user, hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        }

        user.playSoundToPlayer(SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 1f, 1f);

        return ActionResult.SUCCESS;
    }
}
