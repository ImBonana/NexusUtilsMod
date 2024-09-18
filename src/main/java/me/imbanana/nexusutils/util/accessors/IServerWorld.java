package me.imbanana.nexusutils.util.accessors;

import me.imbanana.nexusutils.util.MailDeliveryService;

public interface IServerWorld {
    default MailDeliveryService nexusUtils$getMailDeliveryService() {
        return null;
    }
}
