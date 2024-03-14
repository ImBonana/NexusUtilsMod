package me.imbanana.nexusutils.screen.backpack;

import com.mojang.datafixers.util.Pair;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.item.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class BackpackPlayerInventorySlot extends Slot {
    public static final Identifier EMPTY_BACKPACK_SLOT_TEXTURE = new Identifier(NexusUtils.MOD_ID, "item/empty_backpack_slot");


    private final PlayerEntity owner;

    public BackpackPlayerInventorySlot(Inventory inventory, int index, int x, int y, PlayerEntity owner) {
        super(inventory, index, x, y);
        this.owner = owner;
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }


    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isOf(ModItems.BACKPACK);
    }

    @Override
    public void setStack(ItemStack stack, ItemStack previousStack) {
        this.owner.nexusutils$onEquipBackpack(stack, previousStack);
        super.setStack(stack, previousStack);
    }

    @Environment(EnvType.CLIENT)
    @Nullable
    @Override
    public Pair<Identifier, Identifier> getBackgroundSprite() {
        return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EMPTY_BACKPACK_SLOT_TEXTURE);
    }
}
