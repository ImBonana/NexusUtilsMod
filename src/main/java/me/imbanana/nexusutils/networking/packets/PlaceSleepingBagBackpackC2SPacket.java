package me.imbanana.nexusutils.networking.packets;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.custom.SleepingBagBlock;
import me.imbanana.nexusutils.block.enums.SleepingBagPart;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import me.imbanana.nexusutils.screen.backpack.BackpackInventory;
import me.imbanana.nexusutils.screen.backpack.BackpackScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class PlaceSleepingBagBackpackC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            if(player.isOnGround() && !player.isInFluid() && player.getWorld().getBlockState(player.getBlockPos()).isAir()) {
                ItemStack backpackItem = player.getInventory().getStack(BackpackItem.SLOT_ID);
                if(backpackItem == null) return;

                BackpackInventory backpackInventory = new BackpackInventory(backpackItem);

                ItemStack sleepingBagItem = backpackInventory.getSleepingBag();
                if(sleepingBagItem == null || sleepingBagItem.isEmpty()) {
                    player.sendMessage(Text.translatable("message.nexusutils.place_sleeping_bag.no_bag").fillStyle(Style.EMPTY.withColor(Formatting.RED)), true);
                    return;
                };

                if(!(sleepingBagItem.getItem() instanceof BlockItem)) return;

                BlockItem blockItem = (BlockItem) sleepingBagItem.getItem();

                AutomaticItemPlacementContext placementContext = new AutomaticItemPlacementContext(
                        player.getWorld(),
                        player.getBlockPos(),
                        player.getHorizontalFacing(),
                        blockItem.getDefaultStack(),
                        Direction.UP
                );

                BlockState state = blockItem.getBlock().getPlacementState(placementContext);

                if(state != null) {
                    player.getWorld().setBlockState(player.getBlockPos(), blockItem.getBlock().getDefaultState().with(SleepingBagBlock.FACING, player.getHorizontalFacing()).with(SleepingBagBlock.PART, SleepingBagPart.FOOT));
                    blockItem.getBlock().onPlaced(player.getServerWorld(), player.getBlockPos(), state, player, sleepingBagItem);

                    Vec3d newPos = player.getPos().add(0, 0.1875f, 0);
                    player.setPos(newPos.getX(), newPos.getY(), newPos.getZ());

                    if(!player.isCreative()) {
                        ItemStack stack = backpackInventory.getStack(BackpackScreenHandler.SLEEPING_BAG_SLOT_ID);
                        stack.decrement(1);
                        backpackInventory.markDirty();
                    }

                    player.sendMessage(Text.translatable("message.nexusutils.place_sleeping_bag.success").fillStyle(Style.EMPTY.withColor(Formatting.GREEN)), true);
                } else {
                    player.sendMessage(Text.translatable("message.nexusutils.place_sleeping_bag.fail").fillStyle(Style.EMPTY.withColor(Formatting.RED)), true);
                }
            }
        });
    }
}
