package me.imbanana.nexusutils.item;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.item.custom.CraftingOnAStickItem;
import me.imbanana.nexusutils.item.custom.MobGrabberItem;
import me.imbanana.nexusutils.item.custom.ModTestItem;
import me.imbanana.nexusutils.item.custom.VoidTotem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item CRAFTING_ON_A_STICK = registerItem("crafting_on_a_stick", new CraftingOnAStickItem(new FabricItemSettings().maxCount(1)));
    public static final Item MOB_GRABBER = registerItem("mob_grabber", new MobGrabberItem(new FabricItemSettings().maxCount(1)));
    public static final Item VOID_TOTEM = registerItem("void_totem", new VoidTotem(new FabricItemSettings().maxCount(1)));
    public static final Item MOD_TEST = registerItem("mod_test", new ModTestItem(new FabricItemSettings().maxCount(1)));

    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(CRAFTING_ON_A_STICK);
        entries.add(MOB_GRABBER);
        entries.add(VOID_TOTEM);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(NexusUtils.MOD_ID, name), item);
    }

    public static void registerModItems() {
        NexusUtils.LOGGER.info("Registering Mod Items for " + NexusUtils.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
