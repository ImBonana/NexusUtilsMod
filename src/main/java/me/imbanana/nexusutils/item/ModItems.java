package me.imbanana.nexusutils.item;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import me.imbanana.nexusutils.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    private static final List<Item> modGroupItems = new ArrayList<>();

    public static final Item CRAFTING_ON_A_STICK = registerItem("crafting_on_a_stick", new CraftingOnAStickItem(new FabricItemSettings().maxCount(1)));
    public static final Item MOB_GRABBER = registerItem("mob_grabber", new MobGrabberItem(new FabricItemSettings().maxCount(1)));
    public static final Item VOID_TOTEM = registerItem("void_totem", new VoidTotem(new FabricItemSettings().maxCount(1)));
//    public static final Item MOD_TEST = registerItem("mod_test", new ModTestItem(new FabricItemSettings().maxCount(1)));
    public static final Item SNAIL_ITEM = registerItem("snail_item", new SnailItem(new FabricItemSettings().maxCount(1)));
    public static final Item HOPPER_FILTER = registerItem("hopper_filter", new HopperFilterItem(new FabricItemSettings().maxCount(64)));
    public static final Item BACKPACK = registerItem("backpack", new BackpackItem(new FabricItemSettings().maxCount(1)));
    public static final Item PACKAGE = registerItem("package", new PackageItem(new FabricItemSettings().maxCount(1)));


    private static Item registerItem(String name, Item item) {
        return registerItem(name, item, true);
    }

    private static Item registerItem(String name, Item item, boolean addToGroupTab) {
        Item registered = Registry.register(Registries.ITEM, new Identifier(NexusUtils.MOD_ID, name), item);
        if(addToGroupTab) addItemToCategory(registered);
        return registered;
    }

    public static void registerModItems() {
        NexusUtils.LOGGER.info("Registering Mod Items for " + NexusUtils.MOD_ID);
    }

    public static void addItemToCategory(Item item) {
        modGroupItems.add(item);
    }

    public static void addItemsToIngredientTabItemGroup(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
        for(Item item : modGroupItems) {
            entries.add(item);
        }
    }
}
