package me.imbanana.nexusutils.networking.packets.mail;

import io.wispforest.owo.network.ClientAccess;
import io.wispforest.owo.network.ServerAccess;
import me.imbanana.nexusutils.networking.ModNetwork;
import me.imbanana.nexusutils.screen.postbox.PostBoxScreen;
import me.imbanana.nexusutils.util.MailBox;
import me.imbanana.nexusutils.util.MailDeliveryService;

import java.util.ArrayList;
import java.util.List;

public record UpdateMailBoxesPacket(List<MailBox> mailBoxes) {
    public static final UpdateMailBoxesPacket EMPTY = new UpdateMailBoxesPacket(new ArrayList<>());

    public static void receiveServer(UpdateMailBoxesPacket message, ServerAccess access) {
        MailDeliveryService mailDeliveryService = access.player().getServerWorld().nexusUtils$getMailDeliveryService();
        List<MailBox> mailBoxes = mailDeliveryService.getAllMailBoxes();

        ModNetwork.NETWORK_CHANNEL.serverHandle(access.player()).send(new UpdateMailBoxesPacket(mailBoxes));
    }

    public static void receiveClient(UpdateMailBoxesPacket message, ClientAccess access) {
        PostBoxScreen.setMailBoxesCache(message.mailBoxes());
        PostBoxScreen.markMailBoxesDirty();
    }
}
