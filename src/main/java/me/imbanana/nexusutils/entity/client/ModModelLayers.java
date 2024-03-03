package me.imbanana.nexusutils.entity.client;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer SNAIL = new EntityModelLayer(new Identifier(NexusUtils.MOD_ID, "snail"), "main");
    public static final EntityModelLayer TRIDENT_OF_FIRE = new EntityModelLayer(new Identifier(NexusUtils.MOD_ID, "trident_of_fire"), "main");
    public static final EntityModelLayer SLEEPING_BAG_HEAD = new EntityModelLayer(new Identifier(NexusUtils.MOD_ID, "sleeping_bag_head"), "main");
    public static final EntityModelLayer SLEEPING_BAG_FOOT = new EntityModelLayer(new Identifier(NexusUtils.MOD_ID, "sleeping_bag_foot"), "main");
    public static final EntityModelLayer SLEEPING_BAG_FULL = new EntityModelLayer(new Identifier(NexusUtils.MOD_ID, "sleeping_bag_full"), "main");
}
