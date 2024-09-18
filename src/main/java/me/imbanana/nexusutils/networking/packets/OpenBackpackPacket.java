package me.imbanana.nexusutils.networking.packets;

import io.wispforest.owo.network.ServerAccess;
import me.imbanana.nexusutils.screen.backpack.BackpackScreenHandlerFactory;
import net.minecraft.item.ItemStack;

public record OpenBackpackPacket() {
    public static void receiveServer(OpenBackpackPacket message, ServerAccess access) {
        access.runtime().execute(() -> {
            ItemStack backpackStack = access.player().getInventory().nexusUtils$getBackpackItemStack();
            if(backpackStack.isEmpty()) return;

            access.player().openHandledScreen(new BackpackScreenHandlerFactory(backpackStack));
        });
    }
}
