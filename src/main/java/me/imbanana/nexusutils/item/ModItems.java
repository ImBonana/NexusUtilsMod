package me.imbanana.nexusutils.item;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import me.imbanana.nexusutils.components.custom.FluidTanksComponent;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import me.imbanana.nexusutils.item.custom.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    private static final List<Item> modGroupItems = new ArrayList<>();

    public static final Item CRAFTING_ON_A_STICK = registerItem("crafting_on_a_stick", new CraftingOnAStickItem(new Item.Settings().maxCount(1)));
    public static final Item MOB_GRABBER = registerItem("mob_grabber", new MobGrabberItem(new Item.Settings().maxCount(1)));
    public static final Item VOID_TOTEM = registerItem("void_totem", new VoidTotem(new Item.Settings().maxCount(1)));
//    public static final Item MOD_TEST = registerItem("mod_test", new ModTestItem(new Item.Settings().maxCount(1)));
    public static final Item SNAIL_ITEM = registerItem("snail_item", new SnailItem(new Item.Settings().maxCount(1)));
    public static final Item BACKPACK_IRON_UPGRADE = registerItem("backpack_iron_upgrade", new BackpackUpgradeItem(new Item.Settings().maxCount(16), 1));
    public static final Item BACKPACK_GOLD_UPGRADE = registerItem("backpack_gold_upgrade", new BackpackUpgradeItem(new Item.Settings().maxCount(16), 2));
    public static final Item BACKPACK_DIAMOND_UPGRADE = registerItem("backpack_diamond_upgrade", new BackpackUpgradeItem(new Item.Settings().maxCount(16), 3));
    public static final Item BACKPACK_NETHERITE_UPGRADE = registerItem("backpack_netherite_upgrade", new BackpackUpgradeItem(new Item.Settings().maxCount(16), 4));
    public static final Item HOPPER_FILTER = registerItem("hopper_filter", new HopperFilterItem(
            new Item.Settings()
                    .component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
            ));
    public static final Item BACKPACK = registerItem("backpack", new BackpackItem(
            new Item.Settings()
                    .maxCount(1)
                    .component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
                    .component(ModComponents.FLUID_TANKS, FluidTanksComponent.createTanks(0))
                    .component(ModComponents.BACKPACK_TIER, BackpackTierComponent.createDefaultBackpackTier())
    ));
    public static final Item PACKAGE = registerItem("package", new PackageItem(
            new Item.Settings()
                    .maxCount(1)
                    .component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
    ));
    public static final Item PINK_QUARTZ = registerItem("pink_quartz", new Item(
            new Item.Settings()
                    .food(
                            new FoodComponent.Builder()
                                    .alwaysEdible()
                                    .nutrition(4)
                                    .saturationModifier(1.2f)
                                    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200), 1f)
                                    .build()
                    )
            ));

    public static final Item TERRORIST_DOG_REMOTE = registerItem("terrorist_dog_remote", new TerroristDogRemoteItem(new Item.Settings().maxCount(1).maxDamage(32)));
    public static final Item TERRORIST_DOG = registerItem("terrorist_dog", new TerroristDogItem(new Item.Settings().maxCount(16)));

    private static Item registerItem(String name, Item item) {
        return registerItem(name, item, true);
    }

    private static Item registerItem(String name, Item item, boolean addToGroupTab) {
        Item registered = Registry.register(Registries.ITEM, NexusUtils.idOf(name), item);
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
