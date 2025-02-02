package me.imbanana.nexusutils.datagen;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.components.ModComponents;
import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import me.imbanana.nexusutils.tags.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.criterion.ChangedDimensionCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.UsingItemCriterion;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.predicate.ComponentPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.predicate.entity.PlayerPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
    public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        RegistryEntryLookup<Item> itemLookup = registryLookup.getOrThrow(RegistryKeys.ITEM);

        AdvancementEntry root = Advancement.Builder.create()
                .display(
                        ModItems.CRAFTING_ON_A_STICK,
                        Text.translatable("advancements.nexus.root.title"),
                        Text.translatable("advancements.nexus.root.description"),
                        Identifier.ofVanilla("textures/block/oak_planks.png"),
                        AdvancementFrame.TASK,
                        false,
                        false,
                        false
                )
                .criterion("obtained_crafting_on_a_stick", InventoryChangedCriterion.Conditions.items(ModItems.CRAFTING_ON_A_STICK))
                .build(NexusUtils.idOf("nexus/root"));
        consumer.accept(root);

        AdvancementEntry copperHopper = Advancement.Builder.create()
                .parent(root)
                .display(
                        ModBlocks.COPPER_HOPPER,
                        Text.translatable("advancements.nexus.copper_hopper.title"),
                        Text.translatable("advancements.nexus.copper_hopper.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("obtained_copper_hopper", InventoryChangedCriterion.Conditions.items(ModBlocks.COPPER_HOPPER))
                .build(NexusUtils.idOf("nexus/copper_hopper"));
        consumer.accept(copperHopper);

        AdvancementEntry mailBox = Advancement.Builder.create()
                .parent(root)
                .display(
                        ModBlocks.MAIL_BOX,
                        Text.translatable("advancements.nexus.mail_box.title"),
                        Text.translatable("advancements.nexus.mail_box.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("obtained_mail_box", InventoryChangedCriterion.Conditions.items(ModBlocks.MAIL_BOX))
                .build(NexusUtils.idOf("nexus/mail_box"));
        consumer.accept(mailBox);

        AdvancementEntry postBox = Advancement.Builder.create()
                .parent(mailBox)
                .display(
                        ModBlocks.POST_BOX,
                        Text.translatable("advancements.nexus.post_box.title"),
                        Text.translatable("advancements.nexus.post_box.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("obtained_post_box", InventoryChangedCriterion.Conditions.items(ModBlocks.POST_BOX))
                .build(NexusUtils.idOf("nexus/post_box"));
        consumer.accept(postBox);

        AdvancementEntry sleepingBag = Advancement.Builder.create()
                .parent(root)
                .display(
                        ModBlocks.RED_SLEEPING_BAG,
                        Text.translatable("advancements.nexus.sleeping_bag.title"),
                        Text.translatable("advancements.nexus.sleeping_bag.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .criterion("obtained_sleeping_bag", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(itemLookup, ModTags.Items.SLEEPING_BAGS)))
                .build(NexusUtils.idOf("nexus/sleeping_bag"));
        consumer.accept(sleepingBag);

        AdvancementEntry pickSnail = Advancement.Builder.create()
                .parent(sleepingBag)
                .display(
                        ModItems.SNAIL_ITEM,
                        Text.translatable("advancements.nexus.pick_snail.title"),
                        Text.translatable("advancements.nexus.pick_snail.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .criterion("obtained_snail", InventoryChangedCriterion.Conditions.items(ModItems.SNAIL_ITEM))
                .build(NexusUtils.idOf("nexus/obtained_snail"));
        consumer.accept(pickSnail);

        AdvancementEntry backpack = Advancement.Builder.create()
                .parent(sleepingBag)
                .display(
                        ModItems.BACKPACK,
                        Text.translatable("advancements.nexus.backpack.title"),
                        Text.translatable("advancements.nexus.backpack.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("obtained_backpack", InventoryChangedCriterion.Conditions.items(ModItems.BACKPACK))
                .build(NexusUtils.idOf("nexus/obtained_backpack"));
        consumer.accept(backpack);

        AdvancementEntry backpackUpgrade = Advancement.Builder.create()
                .parent(backpack)
                .display(
                        BackpackItem.ofTier(BackpackTierComponent.Tier.TIER_3),
                        Text.translatable("advancements.nexus.backpack_upgrade.title"),
                        Text.translatable("advancements.nexus.backpack_upgrade.description"),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR)
                .criterion(
                        "obtained_upgraded_backpack_tier_1",
                        InventoryChangedCriterion.Conditions.items(
                                ItemPredicate.Builder.create()
                                        .items(itemLookup, ModItems.BACKPACK)
                                        .component(
                                                ComponentPredicate.builder()
                                                        .add(
                                                                ModComponents.BACKPACK_TIER,
                                                                new BackpackTierComponent(BackpackTierComponent.Tier.TIER_1)
                                                        )
                                                        .build()
                                        )
                        )
                )
                .criterion(
                        "obtained_upgraded_backpack_tier_2",
                        InventoryChangedCriterion.Conditions.items(
                                ItemPredicate.Builder.create()
                                        .items(itemLookup, ModItems.BACKPACK)
                                        .component(
                                                ComponentPredicate.builder()
                                                        .add(
                                                                ModComponents.BACKPACK_TIER,
                                                                new BackpackTierComponent(BackpackTierComponent.Tier.TIER_2)
                                                        )
                                                        .build()
                                        )
                        )
                )
                .criterion(
                        "obtained_upgraded_backpack_tier_3",
                        InventoryChangedCriterion.Conditions.items(
                                ItemPredicate.Builder.create()
                                        .items(itemLookup, ModItems.BACKPACK)
                                        .component(
                                                ComponentPredicate.builder()
                                                        .add(
                                                                ModComponents.BACKPACK_TIER,
                                                                new BackpackTierComponent(BackpackTierComponent.Tier.TIER_3)
                                                        )
                                                        .build()
                                        )
                        )
                )
                .criterion(
                        "obtained_upgraded_backpack_tier_4",
                        InventoryChangedCriterion.Conditions.items(
                                ItemPredicate.Builder.create()
                                        .items(itemLookup, ModItems.BACKPACK)
                                        .component(
                                                ComponentPredicate.builder()
                                                        .add(
                                                                ModComponents.BACKPACK_TIER,
                                                                new BackpackTierComponent(BackpackTierComponent.Tier.TIER_4)
                                                        )
                                                        .build()
                                        )
                        )
                )
                .build(NexusUtils.idOf("nexus/obtained_upgraded_backpack"));
        consumer.accept(backpackUpgrade);

        AdvancementEntry backpackMaxUpgrade = Advancement.Builder.create()
                .parent(backpackUpgrade)
                .display(
                        BackpackItem.ofTier(BackpackTierComponent.Tier.TIER_4),
                        Text.translatable("advancements.nexus.backpack_max_upgrade.title"),
                        Text.translatable("advancements.nexus.backpack_max_upgrade.description"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .criterion(
                        "obtained_max_upgraded_backpack",
                        InventoryChangedCriterion.Conditions.items(
                                ItemPredicate.Builder.create()
                                        .items(itemLookup, ModItems.BACKPACK)
                                        .component(
                                                ComponentPredicate.builder()
                                                        .add(
                                                                ModComponents.BACKPACK_TIER,
                                                                new BackpackTierComponent(BackpackTierComponent.Tier.TIER_4)
                                                        )
                                                        .build()
                                        )
                        )
                )
                .build(NexusUtils.idOf("nexus/obtained_max_upgraded_backpack"));
        consumer.accept(backpackMaxUpgrade);

        AdvancementEntry voidTotem = Advancement.Builder.create()
                .parent(root)
                .display(
                        ModItems.VOID_TOTEM,
                        Text.translatable("advancements.nexus.void_totem.title"),
                        Text.translatable("advancements.nexus.void_totem.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("obtained_void_totem", InventoryChangedCriterion.Conditions.items(ModItems.VOID_TOTEM))
                .build(NexusUtils.idOf("nexus/obtained_void_totem"));
        consumer.accept(voidTotem);

        AdvancementEntry terroristBelt = Advancement.Builder.create()
                .parent(root)
                .display(
                        ModItems.TERRORIST_DOG,
                        Text.translatable("advancements.nexus.terrorist_dog.title"),
                        Text.translatable("advancements.nexus.terrorist_dog.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .criterion("obtained_terrorist_dog", InventoryChangedCriterion.Conditions.items(ModItems.TERRORIST_DOG))
                .build(NexusUtils.idOf("nexus/obtained_terrorist_dog"));
        consumer.accept(terroristBelt);

        AdvancementEntry terroristRemote = Advancement.Builder.create()
                .parent(terroristBelt)
                .display(
                        ModItems.TERRORIST_DOG_REMOTE,
                        Text.translatable("advancements.nexus.terrorist_dog_remote.title"),
                        Text.translatable("advancements.nexus.terrorist_dog_remote.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("obtained_terrorist_dog", InventoryChangedCriterion.Conditions.items(ModItems.TERRORIST_DOG_REMOTE))
                .build(NexusUtils.idOf("nexus/obtained_terrorist_dog_remote"));
        consumer.accept(terroristRemote);

        AdvancementEntry packageAd = Advancement.Builder.create()
                .parent(mailBox)
                .display(
                        ModItems.PACKAGE,
                        Text.translatable("advancements.nexus.package.title"),
                        Text.translatable("advancements.nexus.package.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion(
                        "use_package",
                        UsingItemCriterion.Conditions.create(
                                EntityPredicate.Builder.create()
                                        .typeSpecific(
                                                PlayerPredicate.Builder.create()
                                                        .build()
                                        ),
                                ItemPredicate.Builder.create()
                                        .items(itemLookup, ModItems.PACKAGE)
                        )
                )
                .build(NexusUtils.idOf("nexus/package"));
        consumer.accept(packageAd);

    }
}
