package me.imbanana.nexusutils.networking.packets;

import me.imbanana.nexusutils.networking.ModPackets;
import me.imbanana.nexusutils.screen.postbox.PostBoxScreen;
import me.imbanana.nexusutils.util.MailBox;
import me.imbanana.nexusutils.util.MailDeliveryService;
import me.imbanana.nexusutils.util.accessors.IServerWorld;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class UpdateMailBoxesPacket {
    public static PacketByteBuf createMailBoxesPacket(List<MailBox> mailboxes) {
        PacketByteBuf packet = PacketByteBufs.create();

        encode(packet, mailboxes);

        return packet;
    }

    public static void receiveC2S(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        MailDeliveryService mailDeliveryService = ((IServerWorld) player.getServerWorld()).nexusUtils$getMailDeliveryService();
        List<MailBox> mailBoxes = mailDeliveryService.getAllMailBoxes();
        PacketByteBuf packet = createMailBoxesPacket(mailBoxes);

        ServerPlayNetworking.send(player, ModPackets.UPDATE_MAIL_BOXES, packet);
    }

    public static void receiveS2C(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender response) {
        List<MailBox> mailBoxes = decode(buf);
        PostBoxScreen.setMailBoxesCache(mailBoxes);
        PostBoxScreen.markMailBoxesDirty();
    }

    private static void encode(PacketByteBuf buf, List<MailBox> mailBoxes) {
        buf.writeInt(mailBoxes.size());
        for(MailBox mailBox : mailBoxes) {
            mailBox.toPacket(buf);
        }
    }

    private static List<MailBox> decode(PacketByteBuf buf) {
        List<MailBox> mailBoxes = new ArrayList<>();
        int size = buf.readInt();
        for (int i = 0; i <size; i++) {
            mailBoxes.add(MailBox.fromPacket(buf));
        }
        return mailBoxes;
    }
}
