package me.imbanana.nexusutils.events;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.screen.custom.CustomShulkerBoxInventory;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.block.CropBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

public class ModEvents {
     static List<Item> shulkerBoxes = new ArrayList<>(){{
        add(Items.SHULKER_BOX);
        add(Items.BLACK_SHULKER_BOX);
        add(Items.BLUE_SHULKER_BOX);
        add(Items.CYAN_SHULKER_BOX);
        add(Items.GRAY_SHULKER_BOX);
        add(Items.BROWN_SHULKER_BOX);
        add(Items.GREEN_SHULKER_BOX);
        add(Items.LIGHT_BLUE_SHULKER_BOX);
        add(Items.LIGHT_GRAY_SHULKER_BOX);
        add(Items.LIME_SHULKER_BOX);
        add(Items.MAGENTA_SHULKER_BOX);
        add(Items.ORANGE_SHULKER_BOX);
        add(Items.PINK_SHULKER_BOX);
        add(Items.PURPLE_SHULKER_BOX);
        add(Items.RED_SHULKER_BOX);
        add(Items.WHITE_SHULKER_BOX);
        add(Items.YELLOW_SHULKER_BOX);
    }};

    public static void registerEvents() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack itemStack = player.getStackInHand(hand);
            if(itemStack == null) return TypedActionResult.pass(ItemStack.EMPTY);
            if(itemStack.isOf(Items.ENDER_CHEST)) { // open ender chest
                if(!world.isClient()) {
                    player.openHandledScreen(
                            new SimpleNamedScreenHandlerFactory((syncId, inventory, playerx) ->
                                    GenericContainerScreenHandler.createGeneric9x3(syncId, inventory, player.getEnderChestInventory()),
                                    Text.translatable("container.enderchest")
                            )
                    );
                    return TypedActionResult.success(itemStack, true);
                }
            } else if (shulkerBoxes.contains(itemStack.getItem())) { // open shulker box
                if(!world.isClient()) {
                    DefaultedList<ItemStack> items = DefaultedList.ofSize(27, ItemStack.EMPTY);

                    NbtCompound nbt = itemStack.getOrCreateNbt();
                    if (nbt == null) return TypedActionResult.success(ItemStack.EMPTY, false);
                    NbtCompound blockEntityTag = nbt.getCompound("BlockEntityTag");
                    if (blockEntityTag == null) return TypedActionResult.success(ItemStack.EMPTY, false);

                    Inventories.readNbt(blockEntityTag, items);

                    Text displayName = Text.translatable(itemStack.getTranslationKey());

                    player.openHandledScreen(
                            new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player1) -> new CustomShulkerBoxInventory(items, displayName, itemStack).createMenu(syncId, playerInventory, player1), displayName)
                    );
                    return TypedActionResult.success(itemStack, true);
                }
            } else if (itemStack.getItem() instanceof SwordItem && EnchantmentHelper.getLevel(ModEnchantments.LAUNCH, itemStack) > 0 && !player.getItemCooldownManager().isCoolingDown(itemStack.getItem())) { // sword with the launch enchantment
//                NexusUtils.LOGGER.info("Launching!");
                player.setVelocity(player.getRotationVector().multiply(EnchantmentHelper.getLevel(ModEnchantments.LAUNCH, itemStack) * 0.5f));
//                player.velocityModified = true;
                player.getItemCooldownManager().set(itemStack.getItem(), 200);
                return TypedActionResult.success(itemStack, true);
            }

            return TypedActionResult.pass(itemStack);
        });

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            ItemStack mainHandItem = player.getStackInHand(Hand.MAIN_HAND);

            int replanterEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.REPLANTER, mainHandItem);

            if(replanterEnchantmentLevel > 0) {
                if (state.getBlock() instanceof CropBlock cropBlock) {
                    if (cropBlock.isMature(state)) {
                        world.setBlockState(pos, cropBlock.getDefaultState());
                    }
                }
            }
        });
    }
}
