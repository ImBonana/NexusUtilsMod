package me.imbanana.nexusutils.networking.packets.screens;

import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

public record ItemInventoryOpeningData(ItemStack stack) {
    public static final PacketCodec<RegistryByteBuf, ItemInventoryOpeningData> PACKET_CODEC = ItemStack.PACKET_CODEC.xmap(ItemInventoryOpeningData::new, ItemInventoryOpeningData::stack);
}
