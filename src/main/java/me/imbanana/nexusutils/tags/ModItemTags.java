package me.imbanana.nexusutils.tags;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> SLEEPING_BAGS = TagKey.of(RegistryKeys.ITEM, new Identifier(NexusUtils.MOD_ID, "sleeping_bags"));

}
