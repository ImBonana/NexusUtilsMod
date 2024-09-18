package me.imbanana.nexusutils.entity.client;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class ModModelLayers {
    public static final EntityModelLayer SNAIL = new EntityModelLayer(NexusUtils.idOf("snail"), "main");
    public static final EntityModelLayer BACKPACK = new EntityModelLayer(NexusUtils.idOf("backpack"), "main");
    public static final EntityModelLayer SLEEPING_BAG_HEAD = new EntityModelLayer(NexusUtils.idOf("sleeping_bag_head"), "main");
    public static final EntityModelLayer SLEEPING_BAG_FOOT = new EntityModelLayer(NexusUtils.idOf("sleeping_bag_foot"), "main");
    public static final EntityModelLayer SLEEPING_BAG_FULL = new EntityModelLayer(NexusUtils.idOf("sleeping_bag_full"), "main");
}
