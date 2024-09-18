package me.imbanana.nexusutils.networking.packets.mail;

import io.wispforest.owo.network.ClientAccess;
import me.imbanana.nexusutils.screen.postbox.PostBoxScreen;
import net.minecraft.text.Text;

public record SendMailResultPacket(boolean success, Text text) {
    public static void receiveCient(SendMailResultPacket message, ClientAccess access) {
        access.runtime().execute(() -> {
            if(access.runtime().currentScreen instanceof PostBoxScreen postBoxScreen) {
                postBoxScreen.setSendStatus(message.success(), message.text());
                if(message.success()) postBoxScreen.clearMessage();
            }
        });
    }
}
