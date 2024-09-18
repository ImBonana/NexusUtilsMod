package me.imbanana.nexusutils.networking.packets.mail;

import io.wispforest.owo.network.ServerAccess;
import me.imbanana.nexusutils.item.custom.PackageItem;
import me.imbanana.nexusutils.networking.ModNetwork;
import me.imbanana.nexusutils.screen.postbox.PostBoxScreenHandler;
import me.imbanana.nexusutils.util.MailBox;
import me.imbanana.nexusutils.util.MailDeliveryService;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public record SendMailPacket(MailBox mailBox, String message) {
    public static void receiveServer(SendMailPacket message, ServerAccess access) {
        access.runtime().execute(() -> {
            if(access.player().currentScreenHandler instanceof PostBoxScreenHandler postBoxScreenHandler) {
                MailDeliveryService mailDeliveryService = access.player().getServerWorld().nexusUtils$getMailDeliveryService();
                ItemStack packageItem = PackageItem.packScreen(postBoxScreenHandler, message.message(), access.player());

                MailDeliveryService.MailSendResult result = mailDeliveryService.sendMail(message.mailBox(), packageItem);

                boolean success = false;
                Text text = Text.empty();

                switch (result) {
                    case SUCCESS: {
                        postBoxScreenHandler.getInventory().clear();
                        success = true;
                        text = Text.translatable("screen.nexusutils.postbox.success").setStyle(Style.EMPTY.withColor(Formatting.GREEN));
                        break;
                    }

                    case FULL: {
                        text = Text.translatable("screen.nexusutils.postbox.full").setStyle(Style.EMPTY.withColor(Formatting.RED));
                        break;
                    }

                    case NOT_FOUND: {
                        text = Text.translatable("screen.nexusutils.postbox.not_found").setStyle(Style.EMPTY.withColor(Formatting.RED));

                        ModNetwork.NETWORK_CHANNEL.serverHandle(access.player()).send(new UpdateMailBoxesPacket(mailDeliveryService.getAllMailBoxes()));
                        break;
                    }
                }

                ModNetwork.NETWORK_CHANNEL.serverHandle(access.player()).send(new SendMailResultPacket(success, text));
            }
        });
    }
}
