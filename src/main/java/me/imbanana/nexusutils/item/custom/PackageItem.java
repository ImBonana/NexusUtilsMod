package me.imbanana.nexusutils.item.custom;

import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.screen.postbox.PostBoxScreenHandler;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PackageItem extends Item {
    public PackageItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSoundToPlayer(SoundEvents.ITEM_BOOK_PUT, SoundCategory.PLAYERS, 1.5f, 1f);
        user.incrementStat(Stats.USED.getOrCreateStat(this));

        if(world.isClient) return TypedActionResult.pass(user.getStackInHand(hand));

        ItemStack itemStack = user.getStackInHand(hand);

        ContainerComponent containerComponent = itemStack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT);
        containerComponent.stream().forEach(stack ->
                user.getInventory().offerOrDrop(stack));

        Criteria.USING_ITEM.trigger((ServerPlayerEntity) user, itemStack);
        itemStack.decrement(1);

        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    public static ItemStack packScreen(PostBoxScreenHandler postBoxScreenHandler, String message, ServerPlayerEntity player) {
        ItemStack itemStack = new ItemStack(ModItems.PACKAGE);
        itemStack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(postBoxScreenHandler.getInventory().getItems()));

        List<Text> lore = new ArrayList<>();
        lore.add(Text.translatable("item.nexusutils.package.sent", player.getName()).setStyle(Style.EMPTY.withColor(Formatting.YELLOW).withItalic(false)));
        if(!message.isBlank()) lore.add(Text.literal("\"" + message + "\"").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(false)));
        lore.add(Text.empty());
        lore.add(Text.translatable("item.nexusutils.package.info").setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(true)));

        itemStack.set(DataComponentTypes.LORE, new LoreComponent(lore));

        return itemStack;
    }
}
