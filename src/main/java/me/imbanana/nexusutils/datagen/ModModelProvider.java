package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.block.custom.MailBoxBlock;
import me.imbanana.nexusutils.block.custom.SleepingBagBlock;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.item.backpack.BackpackEntityModelRenderer;
import me.imbanana.nexusutils.item.models.SleepingBagModelRenderer;
import me.imbanana.nexusutils.item.models.SnailModelRenderer;
import me.imbanana.nexusutils.trim.ModTrimMaterials;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.select.TrimMaterialProperty;
import net.minecraft.client.render.item.tint.DyeTintSource;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
    public static final Model TEMPLATE_SLEEPING_BAG = new Model(Optional.of(NexusUtils.idOf("item/" + "sleeping_bag_template")), Optional.empty(), TextureKey.PARTICLE);

    private static final ArrayList<ArmorItem> ARMOR_ITEMS = new ArrayList<>(){{
        add(new ArmorItem(Items.CHAINMAIL_BOOTS, EquipmentAssetKeys.CHAINMAIL, "boots", false));
        add(new ArmorItem(Items.CHAINMAIL_CHESTPLATE, EquipmentAssetKeys.CHAINMAIL, "chestplate", false));
        add(new ArmorItem(Items.CHAINMAIL_LEGGINGS, EquipmentAssetKeys.CHAINMAIL, "leggings", false));
        add(new ArmorItem(Items.CHAINMAIL_HELMET, EquipmentAssetKeys.CHAINMAIL, "helmet", false));

        add(new ArmorItem(Items.DIAMOND_HELMET, EquipmentAssetKeys.DIAMOND, "helmet", false));
        add(new ArmorItem(Items.DIAMOND_CHESTPLATE, EquipmentAssetKeys.DIAMOND, "chestplate", false));
        add(new ArmorItem(Items.DIAMOND_LEGGINGS, EquipmentAssetKeys.DIAMOND, "leggings", false));
        add(new ArmorItem(Items.DIAMOND_BOOTS, EquipmentAssetKeys.DIAMOND, "boots", false));

        add(new ArmorItem(Items.GOLDEN_HELMET, EquipmentAssetKeys.GOLD, "helmet", false));
        add(new ArmorItem(Items.GOLDEN_CHESTPLATE, EquipmentAssetKeys.GOLD, "chestplate", false));
        add(new ArmorItem(Items.GOLDEN_LEGGINGS, EquipmentAssetKeys.GOLD, "leggings", false));
        add(new ArmorItem(Items.GOLDEN_BOOTS, EquipmentAssetKeys.GOLD, "boots", false));

        add(new ArmorItem(Items.IRON_HELMET, EquipmentAssetKeys.IRON, "helmet", false));
        add(new ArmorItem(Items.IRON_CHESTPLATE, EquipmentAssetKeys.IRON, "chestplate", false));
        add(new ArmorItem(Items.IRON_LEGGINGS, EquipmentAssetKeys.IRON, "leggings", false));
        add(new ArmorItem(Items.IRON_BOOTS, EquipmentAssetKeys.IRON, "boots", false));

        add(new ArmorItem(Items.LEATHER_HELMET, EquipmentAssetKeys.LEATHER, "helmet", true));
        add(new ArmorItem(Items.LEATHER_CHESTPLATE, EquipmentAssetKeys.LEATHER, "chestplate", true));
        add(new ArmorItem(Items.LEATHER_LEGGINGS, EquipmentAssetKeys.LEATHER, "leggings", true));
        add(new ArmorItem(Items.LEATHER_BOOTS, EquipmentAssetKeys.LEATHER, "boots", true));

        add(new ArmorItem(Items.NETHERITE_HELMET, EquipmentAssetKeys.NETHERITE, "helmet", false));
        add(new ArmorItem(Items.NETHERITE_CHESTPLATE, EquipmentAssetKeys.NETHERITE, "chestplate", false));
        add(new ArmorItem(Items.NETHERITE_LEGGINGS, EquipmentAssetKeys.NETHERITE, "leggings", false));
        add(new ArmorItem(Items.NETHERITE_BOOTS, EquipmentAssetKeys.NETHERITE, "boots", false));

        add(new ArmorItem(Items.TURTLE_HELMET, EquipmentAssetKeys.TURTLE_SCUTE, "helmet", false));
    }};

    private static final List<TrimMaterial> TRIM_MATERIALS = new ArrayList<>(){{
        add(new TrimMaterial("pink_quartz", ModTrimMaterials.PINK_QUARTZ, Map.of()));
    }};

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        for (SleepingBagBlock block : ModBlocks.SLEEPING_BAGS) {
            blockStateModelGenerator.blockStateCollector.accept(
                    BlockStateModelGenerator.createSingletonBlockState(
                            block,
                            NexusUtils.idOf("block/sleeping_bag")
                    )
            );
        }

        blockStateModelGenerator.blockStateCollector
            .accept(
                VariantsBlockStateSupplier.create(ModBlocks.FROZEN_LAVA)
                    .coordinate(
                        BlockStateVariantMap.create(Properties.AGE_3)
                            .register(0, BlockStateVariant.create().put(VariantSettings.MODEL, blockStateModelGenerator.createSubModel(ModBlocks.FROZEN_LAVA, "_0", Models.CUBE_ALL, TextureMap::all)))
                            .register(1, BlockStateVariant.create().put(VariantSettings.MODEL, blockStateModelGenerator.createSubModel(ModBlocks.FROZEN_LAVA, "_1", Models.CUBE_ALL, TextureMap::all)))
                            .register(2, BlockStateVariant.create().put(VariantSettings.MODEL, blockStateModelGenerator.createSubModel(ModBlocks.FROZEN_LAVA, "_2", Models.CUBE_ALL, TextureMap::all)))
                            .register(3, BlockStateVariant.create().put(VariantSettings.MODEL, blockStateModelGenerator.createSubModel(ModBlocks.FROZEN_LAVA, "_3", Models.CUBE_ALL, TextureMap::all)))
                    )
            );

        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(ModBlocks.MAIL_BOX)
                        .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
                        .coordinate(
                                BlockStateVariantMap.create(MailBoxBlock.THING)
                                        .register(false, BlockStateVariant.create().put(VariantSettings.MODEL, Registries.BLOCK.getId(ModBlocks.MAIL_BOX).withSuffixedPath("_down").withPrefixedPath("block/")))
                                        .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, Registries.BLOCK.getId(ModBlocks.MAIL_BOX).withSuffixedPath("_up").withPrefixedPath("block/")))
                        )
        );

        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(ModBlocks.POST_BOX, BlockStateVariant.create().put(VariantSettings.MODEL, Registries.BLOCK.getId(ModBlocks.POST_BOX).withPrefixedPath("block/")))
                        .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates())
        );

        blockStateModelGenerator.registerSimpleState(ModBlocks.ITEM_DISPLAY);
        blockStateModelGenerator.registerSimpleState(ModBlocks.COPPER_HOPPER);
        blockStateModelGenerator.registerItemModel(ModBlocks.COPPER_HOPPER.asItem());
        blockStateModelGenerator.itemModelOutput.accept(ModBlocks.MAIL_BOX.asItem(), ItemModels.basic(ModelIds.getBlockModelId(ModBlocks.MAIL_BOX).withSuffixedPath("_down")));

        Models.PARTICLE.upload(NexusUtils.idOf("block/sleeping_bag"), TextureMap.particle(Blocks.WHITE_WOOL), blockStateModelGenerator.modelCollector);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        registerTrimMaterials(itemModelGenerator);

        for (SleepingBagBlock block : ModBlocks.SLEEPING_BAGS) {
            Item item = block.asItem();
            Identifier identifier2 = TEMPLATE_SLEEPING_BAG.upload(ModelIds.getItemModelId(item), TextureMap.particle(block.getParticleSource()), itemModelGenerator.modelCollector);
            itemModelGenerator.output.accept(item, ItemModels.special(identifier2, new SleepingBagModelRenderer.Unbaked(block.getColor())));
        }

        itemModelGenerator.register(ModItems.CRAFTING_ON_A_STICK, Models.HANDHELD_ROD);
        itemModelGenerator.register(ModItems.HOPPER_FILTER, Models.GENERATED);
        itemModelGenerator.register(ModItems.PACKAGE, Models.GENERATED);
        itemModelGenerator.register(ModItems.PINK_QUARTZ, Models.GENERATED);
        itemModelGenerator.register(ModItems.TERRORIST_DOG, Models.GENERATED);
        itemModelGenerator.register(ModItems.TERRORIST_DOG_REMOTE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.VOID_TOTEM, Models.GENERATED);
        itemModelGenerator.register(ModItems.BACKPACK_IRON_UPGRADE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BACKPACK_GOLD_UPGRADE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BACKPACK_DIAMOND_UPGRADE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BACKPACK_NETHERITE_UPGRADE, Models.GENERATED);

        itemModelGenerator.output.accept(ModItems.SNAIL_ITEM, ItemModels.special(ModelIds.getItemModelId(ModItems.SNAIL_ITEM), new SnailModelRenderer.Unbaked()));
        itemModelGenerator.output.accept(ModItems.BACKPACK, ItemModels.special(ModelIds.getItemModelId(ModItems.BACKPACK), new BackpackEntityModelRenderer.Unbaked()));
    }

    private void registerTrimMaterials(ItemModelGenerator itemModelGenerator) {
        List<TrimMaterial> materials = new ArrayList<>(TRIM_MATERIALS);
        for(ItemModelGenerator.TrimMaterial trimMaterial : ItemModelGenerator.TRIM_MATERIALS) {
            materials.add(new TrimMaterial(trimMaterial.name(), trimMaterial.materialKey(), trimMaterial.overrideArmorMaterials()));
        }

        for(ArmorItem armorItem : ARMOR_ITEMS) {
            Identifier identifier = ModelIds.getItemModelId(armorItem.item());
            Identifier identifier2 = TextureMap.getId(armorItem.item());
            Identifier identifier3 = TextureMap.getSubId(armorItem.item(), "_overlay");
            List<SelectItemModel.SwitchCase<RegistryKey<ArmorTrimMaterial>>> list = new ArrayList<>(materials.size());

            for(TrimMaterial trimMaterial : materials) {
                Identifier temp = identifier.withSuffixedPath("_" + trimMaterial.name() + "_trim");
                Identifier identifier4 = TRIM_MATERIALS.contains(trimMaterial) ? NexusUtils.idOf(temp.getPath()) : temp;
//                Identifier identifier5 = Identifier.ofVanilla("trims/items/" + armorItem.type() + "_trim_" + trimMaterial.texture(armorItem.equipmentKey()));
                ItemModel.Unbaked unbaked = armorItem.dyeable()
                        ? ItemModels.tinted(identifier4, new DyeTintSource(-6265536))
                        : ItemModels.basic(identifier4);

                list.add(ItemModels.switchCase(trimMaterial.materialKey(), unbaked));
            }

            for (TrimMaterial trimMaterial : TRIM_MATERIALS) {
                Identifier identifier4 = NexusUtils.idOf(identifier.withSuffixedPath("_" + trimMaterial.name() + "_trim").getPath());
                Identifier identifier5 = Identifier.ofVanilla("trims/items/" + armorItem.type() + "_trim_" + trimMaterial.texture(armorItem.equipmentKey()));

                if(armorItem.dyeable()) {
                    itemModelGenerator.uploadArmor(identifier4, identifier2, identifier3, identifier5);
                } else {
                    itemModelGenerator.uploadArmor(identifier4, identifier2, identifier5);
                }
            }

            ItemModel.Unbaked unbaked2 = armorItem.dyeable()
                    ? ItemModels.tinted(identifier, new DyeTintSource(-6265536))
                    : ItemModels.basic(identifier);

            itemModelGenerator.output.accept(armorItem.item(), ItemModels.select(new TrimMaterialProperty(), unbaked2, list));
        }
    }

    @Environment(EnvType.CLIENT)
    private record TrimMaterial(String name, RegistryKey<ArmorTrimMaterial> materialKey, Map<RegistryKey<EquipmentAsset>, String> overrideArmorMaterials) {

        public String texture(RegistryKey<EquipmentAsset> equipmentKey) {
            return this.overrideArmorMaterials.getOrDefault(equipmentKey, this.name);
        }
    }

    private record ArmorItem(Item item, RegistryKey<EquipmentAsset> equipmentKey, String type, boolean dyeable) { }
}
