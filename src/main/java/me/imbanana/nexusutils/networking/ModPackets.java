package me.imbanana.nexusutils.networking;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.networking.packets.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier SIT_ID = new Identifier(NexusUtils.MOD_ID, "sit");
    public static final Identifier BLOCK_PARTICLE = new Identifier(NexusUtils.MOD_ID, "block_particle");
    public static final Identifier SORT_INVENTORY = new Identifier(NexusUtils.MOD_ID, "sort_inventory");
    public static final Identifier AUTO_STACK_INVENTORY = new Identifier(NexusUtils.MOD_ID, "auto_stack_inventory");
    public static final Identifier TRANSFER_ALL_INVENTORY = new Identifier(NexusUtils.MOD_ID, "transfer_all_inventory");
    public static final Identifier UPDATE_COPPER_HOPPER_FILTER_MODE = new Identifier(NexusUtils.MOD_ID, "update_copper_hopper_filter_mode");
    public static final Identifier OPEN_BACKPACK = new Identifier(NexusUtils.MOD_ID, "open_backpack");
    public static final Identifier PLACE_SLEEPING_BAG_BACKPACK = new Identifier(NexusUtils.MOD_ID, "place_sleeping_bag_backpack");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SIT_ID, SitC2SPacket::receive);

        ServerPlayNetworking.registerGlobalReceiver(SORT_INVENTORY, InventoryUtilsC2SPacket::receiveSort);
        ServerPlayNetworking.registerGlobalReceiver(AUTO_STACK_INVENTORY, InventoryUtilsC2SPacket::receiveAutoStack);
        ServerPlayNetworking.registerGlobalReceiver(TRANSFER_ALL_INVENTORY, InventoryUtilsC2SPacket::receiveTransferAll);

        ServerPlayNetworking.registerGlobalReceiver(UPDATE_COPPER_HOPPER_FILTER_MODE, UpdateCopperHopperFilterModeC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(OPEN_BACKPACK, OpenBackpackC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PLACE_SLEEPING_BAG_BACKPACK, PlaceSleepingBagBackpackC2SPacket::receive);
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(BLOCK_PARTICLE, BlockParticleS2CPacket::receive);
    }
}
