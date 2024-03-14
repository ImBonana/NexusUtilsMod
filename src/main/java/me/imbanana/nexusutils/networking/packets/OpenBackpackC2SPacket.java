package me.imbanana.nexusutils.networking.packets;

import me.imbanana.nexusutils.item.backpack.BackpackItem;
import me.imbanana.nexusutils.screen.backpack.BackpackScreenHandler;
import me.imbanana.nexusutils.screen.backpack.BackpackScreenHandlerFactory;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class OpenBackpackC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        server.execute(() -> {
            ItemStack backpackStack = player.getInventory().getStack(BackpackItem.SLOT_ID);
            if(backpackStack.isEmpty()) return;

            // Open the backpack menu
//            player.sendMessage(Text.literal("Opening Backpack"));
            player.openHandledScreen(new BackpackScreenHandlerFactory(backpackStack));
        });
    }
}
