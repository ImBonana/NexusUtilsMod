package me.imbanana.nexusutils.item;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import me.imbanana.nexusutils.components.custom.FluidTanksComponent;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import me.imbanana.nexusutils.item.custom.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ModItems {
    private static final List<Item> MOD_GROUP_ITEMS = new ArrayList<>();

    public static final Item CRAFTING_ON_A_STICK = registerItem("crafting_on_a_stick", CraftingOnAStickItem::new, new Item.Settings().maxCount(1));
    public static final Item VOID_TOTEM = registerItem("void_totem", new Item.Settings().maxCount(1));
//    public static final Item MOD_TEST = registerItem("mod_test", ModTestItem::new, new Item.Settings().maxCount(1));
    public static final Item SNAIL_ITEM = registerItem(
            "snail_item",
            SnailItem::new,
            new Item.Settings()
                .maxCount(1)
                .equippable(EquipmentSlot.HEAD)
    );
    public static final Item BACKPACK_IRON_UPGRADE = registerItem("backpack_iron_upgrade", settings -> new BackpackUpgradeItem(settings, 1), new Item.Settings().maxCount(16));
    public static final Item BACKPACK_GOLD_UPGRADE = registerItem("backpack_gold_upgrade", settings -> new BackpackUpgradeItem(settings, 2), new Item.Settings().maxCount(16));
    public static final Item BACKPACK_DIAMOND_UPGRADE = registerItem("backpack_diamond_upgrade", settings -> new BackpackUpgradeItem(settings, 3), new Item.Settings().maxCount(16));
    public static final Item BACKPACK_NETHERITE_UPGRADE = registerItem("backpack_netherite_upgrade", settings -> new BackpackUpgradeItem(settings, 4), new Item.Settings().maxCount(16));
    public static final Item HOPPER_FILTER = registerItem(
            "hopper_filter",
            HopperFilterItem::new,
            new Item.Settings()
                .component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
    );
    public static final Item BACKPACK = registerItem("backpack", BackpackItem::new,
            new Item.Settings()
                    .maxCount(1)
                    .component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
                    .component(ModComponents.FLUID_TANKS, FluidTanksComponent.createTanks(0))
                    .component(ModComponents.BACKPACK_TIER, BackpackTierComponent.createDefaultBackpackTier())
    );
    public static final Item PACKAGE = registerItem("package", PackageItem::new,
            new Item.Settings()
                    .maxCount(1)
                    .component(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT)
    );
    public static final Item PINK_QUARTZ = registerItem("pink_quartz",
        new Item.Settings()
            .food(
                new FoodComponent.Builder()
                    .alwaysEdible()
                    .nutrition(4)
                    .saturationModifier(1.2f)
                    .build(),
                ConsumableComponent.builder()
                    .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200), 1f))
                    .build()
            )
    );

    public static final Item TERRORIST_DOG_REMOTE = registerItem("terrorist_dog_remote", TerroristDogRemoteItem::new, new Item.Settings().maxCount(1).maxDamage(32));
    public static final Item TERRORIST_DOG = registerItem("terrorist_dog", TerroristDogItem::new, new Item.Settings().maxCount(16));

    private static Item registerItem(String name, Item.Settings settings) {
        return registerItem(name, settings, true);
    }

    private static Item registerItem(String name, Item.Settings settings, boolean addToGroupTab) {
        return registerItem(name, Item::new, settings, addToGroupTab);
    }

    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return registerItem(name, factory, settings, true);
    }

    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings, boolean addToGroupTab) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, NexusUtils.idOf(name));

        Item registered = Registry.register(Registries.ITEM, key, factory.apply(settings.registryKey(key)));
        if(addToGroupTab) addItemToCategory(registered);
        return registered;
    }

    public static void registerModItems() {
        NexusUtils.LOGGER.info("Registering Mod Items for " + NexusUtils.MOD_ID);
    }

    public static void addItemToCategory(Item item) {
        MOD_GROUP_ITEMS.add(item);
    }

    public static void addItemsToIngredientTabItemGroup(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
        for(Item item : MOD_GROUP_ITEMS) {
            entries.add(item);
            if(item instanceof BackpackItem) {
                entries.add(BackpackItem.ofTier(BackpackTierComponent.Tier.TIER_1));
                entries.add(BackpackItem.ofTier(BackpackTierComponent.Tier.TIER_2));
                entries.add(BackpackItem.ofTier(BackpackTierComponent.Tier.TIER_3));
                entries.add(BackpackItem.ofTier(BackpackTierComponent.Tier.TIER_4));
            }
        }
    }
}
