package me.imbanana.nexusutils.networking;

import io.wispforest.owo.network.OwoNetChannel;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.networking.packets.*;
import me.imbanana.nexusutils.networking.packets.inventoryUtils.AutoStackInventoryPacket;
import me.imbanana.nexusutils.networking.packets.inventoryUtils.SortInventoryPacket;
import me.imbanana.nexusutils.networking.packets.inventoryUtils.TransferAllInventoryPacket;
import me.imbanana.nexusutils.networking.packets.mail.SendMailPacket;
import me.imbanana.nexusutils.networking.packets.mail.SendMailResultPacket;
import me.imbanana.nexusutils.networking.packets.mail.UpdateMailBoxesPacket;

public class ModNetwork {
    public static final OwoNetChannel NETWORK_CHANNEL = OwoNetChannel.create(NexusUtils.idOf("main"));

    public static void registerNetwork() {
        NETWORK_CHANNEL.registerServerbound(OpenBackpackPacket.class, OpenBackpackPacket::receiveServer);
        NETWORK_CHANNEL.registerServerbound(SitPacket.class, SitPacket::receiveServer);
        NETWORK_CHANNEL.registerServerbound(AutoStackInventoryPacket.class, AutoStackInventoryPacket::receiveServer);
        NETWORK_CHANNEL.registerServerbound(SortInventoryPacket.class, SortInventoryPacket::receiveServer);
        NETWORK_CHANNEL.registerServerbound(TransferAllInventoryPacket.class, TransferAllInventoryPacket::receiveServer);
        NETWORK_CHANNEL.registerServerbound(UpdateCopperHopperFilterModePacket.class, UpdateCopperHopperFilterModePacket::receiveServer);
        NETWORK_CHANNEL.registerServerbound(PlaceSleepingBagBackpackPacket.class, PlaceSleepingBagBackpackPacket::receiveServer);
        NETWORK_CHANNEL.registerServerbound(UpdateMailBoxesPacket.class, UpdateMailBoxesPacket::receiveServer);
        NETWORK_CHANNEL.registerServerbound(SendMailPacket.class, SendMailPacket::receiveServer);
        NETWORK_CHANNEL.registerServerbound(AirJumpPacket.class, AirJumpPacket::receiveServer);

        NETWORK_CHANNEL.registerClientbound(SendMailResultPacket.class, SendMailResultPacket::receiveCient);
        NETWORK_CHANNEL.registerClientbound(UpdateMailBoxesPacket.class, UpdateMailBoxesPacket::receiveClient);
        NETWORK_CHANNEL.registerClientbound(SyncBackpackPacket.class, SyncBackpackPacket::receiveClient);
        NETWORK_CHANNEL.registerClientbound(AirJumpPacket.class, AirJumpPacket::receiveClient);
    }
}
