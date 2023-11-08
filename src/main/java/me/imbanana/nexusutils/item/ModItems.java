package me.imbanana.nexusutils.item;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final Item CRAFTING_ON_A_STICK = registerItem("crafting_on_a_stick", new CraftingOnAStickItem(new FabricItemSettings().maxCount(1)));
    public static final Item MOB_GRABBER = registerItem("mob_grabber", new MobGrabberItem(new FabricItemSettings().maxCount(1)));
    public static final Item VOID_TOTEM = registerItem("void_totem", new VoidTotem(new FabricItemSettings().maxCount(1)));
    public static final Item MOD_TEST = registerItem("mod_test", new ModTestItem(new FabricItemSettings().maxCount(1)));
    public static final Item SNAIL_ITEM = registerItem("snail_item", new SnailItem(new FabricItemSettings().maxCount(1)));

    private static List<Item> modGroupItems = new ArrayList<>();

    private static Item registerItem(String name, Item item) {
        return registerItem(name, item, true);
    }

    private static Item registerItem(String name, Item item, boolean addToGroupTab) {
        if(modGroupItems == null) modGroupItems = new ArrayList<>();
        Item registered = Registry.register(Registries.ITEM, new Identifier(NexusUtils.MOD_ID, name), item);
        if(addToGroupTab) modGroupItems.add(registered);
        return registered;
    }

    public static void registerModItems() {
        NexusUtils.LOGGER.info("Registering Mod Items for " + NexusUtils.MOD_ID);

        modGroupItems = new ArrayList<>();
    }

    public static void addItemsToIngredientTabItemGroup(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
        for(Item item : modGroupItems) entries.add(item);
    }
}
