package me.imbanana.nexusutils.networking.packets;

import me.imbanana.nexusutils.item.custom.PackageItem;
import me.imbanana.nexusutils.networking.ModPackets;
import me.imbanana.nexusutils.screen.postbox.PostBoxScreen;
import me.imbanana.nexusutils.screen.postbox.PostBoxScreenHandler;
import me.imbanana.nexusutils.util.MailBox;
import me.imbanana.nexusutils.util.MailDeliveryService;
import me.imbanana.nexusutils.util.accessors.IServerWorld;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SendMailPacket {
    public static void receiveC2S(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        String message = buf.readString();
        MailBox mailBox = MailBox.fromPacket(buf);

        server.execute(() -> {
            if(player.currentScreenHandler instanceof PostBoxScreenHandler postBoxScreenHandler) {
                PacketByteBuf packet = PacketByteBufs.create();

                MailDeliveryService mailDeliveryService = ((IServerWorld) player.getServerWorld()).nexusUtils$getMailDeliveryService();
                ItemStack packageItem = PackageItem.packScreen(postBoxScreenHandler, message, player);

                MailDeliveryService.MailSendResult result = mailDeliveryService.sendMail(mailBox, packageItem);

                switch (result) {
                    case SUCCESS: {
                        postBoxScreenHandler.getInventory().clear();

                        packet.writeBoolean(true);
                        packet.writeText(Text.translatable("screen.nexusutils.postbox.success").setStyle(Style.EMPTY.withColor(Formatting.GREEN)));
                        break;
                    }

                    case FULL: {
                        packet.writeBoolean(false);
                        packet.writeText(Text.translatable("screen.nexusutils.postbox.full").setStyle(Style.EMPTY.withColor(Formatting.RED)));
                        break;
                    }

                    case NOT_FOUND: {
                        packet.writeBoolean(false);
                        packet.writeText(Text.translatable("screen.nexusutils.postbox.not_found").setStyle(Style.EMPTY.withColor(Formatting.RED)));
                        ServerPlayNetworking.send(player, ModPackets.UPDATE_MAIL_BOXES, UpdateMailBoxesPacket.createMailBoxesPacket(mailDeliveryService.getAllMailBoxes()));
                        break;
                    }
                }

                ServerPlayNetworking.send(player, ModPackets.SEND_MAIL, packet);
            }
        });
    }

    public static void receiveS2C(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender response) {
        boolean success = buf.readBoolean();
        Text message = buf.readText();

        client.execute(() -> {
            if(client.currentScreen instanceof PostBoxScreen postBoxScreen) {
                postBoxScreen.setSendStatus(success, message);
                if(success) postBoxScreen.clearMessage();
            }
        });
    }
}
