package me.imbanana.nexusutils.item.custom;

import me.imbanana.nexusutils.screen.ModScreenHandlers;
import me.imbanana.nexusutils.screen.hopperfilter.HopperFilterExtendedScreenHandlerType;
import me.imbanana.nexusutils.screen.hopperfilter.HopperFilterScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class HopperFilterItem extends Item {
    public static final int INVENTORY_SIZE = 18;

    public HopperFilterItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack filter = user.getStackInHand(hand);
//
//        NbtCompound nbtCompound = filter.getOrCreateNbt();
//        DefaultedList<ItemStack> inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
//
//        Inventories.readNbt(nbtCompound, inventory);

        user.openHandledScreen(new HopperFilterExtendedScreenHandlerType(filter));

        return TypedActionResult.pass(filter);
    }
}
