package me.imbanana.nexusutils.item.custom;

import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.screen.postbox.PostBoxScreenHandler;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class PackageItem extends Item {
    public PackageItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSound(SoundEvents.ITEM_BOOK_PUT, SoundCategory.PLAYERS, 1.5f, 1f);
        user.incrementStat(Stats.USED.getOrCreateStat(this));

        if(world.isClient) return TypedActionResult.pass(user.getStackInHand(hand));

        ItemStack itemStack = user.getStackInHand(hand);

        DefaultedList<ItemStack> items = DefaultedList.ofSize(9, ItemStack.EMPTY);

        Inventories.readNbt(itemStack.getOrCreateNbt(), items);

        for(ItemStack stack : items) {
            user.getInventory().offerOrDrop(stack);
        }

        Criteria.USING_ITEM.trigger((ServerPlayerEntity) user, itemStack);

        itemStack.decrement(1);

        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    public static ItemStack packScreen(PostBoxScreenHandler postBoxScreenHandler, String message, ServerPlayerEntity player) {
        ItemStack itemStack = new ItemStack(ModItems.PACKAGE);
        NbtCompound nbt = itemStack.getOrCreateNbt();
        Inventories.writeNbt(nbt, postBoxScreenHandler.getInventory().getItems());

        NbtList lore = itemStack.getOrCreateSubNbt("display").getList("Lore", NbtElement.STRING_TYPE);
        lore.add(NbtString.of(Text.Serialization.toJsonString(Text.translatable("item.nexusutils.package.sent", player.getName()).setStyle(Style.EMPTY.withColor(Formatting.YELLOW).withItalic(false)))));
        if(!message.isBlank()) lore.add(NbtString.of(Text.Serialization.toJsonString(Text.literal("\"" + message + "\"").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(false)))));
        lore.add(NbtString.of(Text.Serialization.toJsonString(Text.empty())));
        lore.add(NbtString.of(Text.Serialization.toJsonString(Text.translatable("item.nexusutils.package.info").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(true)))));

        itemStack.getOrCreateSubNbt("display").put("Lore", lore);

        return itemStack;
    }
}
