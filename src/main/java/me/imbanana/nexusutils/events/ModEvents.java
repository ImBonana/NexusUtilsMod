package me.imbanana.nexusutils.events;

import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import me.imbanana.nexusutils.util.ModEnchantmentHelper;
import me.imbanana.nexusutils.util.SitHandler;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.CropBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;

public class ModEvents {
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
            }

            if(world instanceof ServerWorld serverWorld) {
                ModEnchantmentHelper.applyEntityEffects(serverWorld, player, player.getPos(), itemStack, hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND, ModEnchantmentEffectComponentTypes.ON_RIGHT_CLICK);
            }

            return TypedActionResult.pass(itemStack);
        });

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            ItemStack mainHandItem = player.getStackInHand(Hand.MAIN_HAND);

            if(EnchantmentHelper.hasAnyEnchantmentsWith(mainHandItem, ModEnchantmentEffectComponentTypes.REPLANTER)) {
                if (state.getBlock() instanceof CropBlock cropBlock) {
                    if (cropBlock.isMature(state)) {
                        world.setBlockState(pos, cropBlock.getDefaultState());
                    }
                }
            }
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(server -> server.execute(SitHandler::cleanUp));
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            ServerPlayerEntity player = handler.getPlayer();

            server.execute(() -> {
                Entity seat = player.getVehicle();
                if(seat != null && SitHandler.isSeat(seat.getId())) {
                    player.dismountVehicle();
                    SitHandler.removeSeat(seat.getId());
                }
            });
        });

        DefaultItemComponentEvents.MODIFY.register(context ->
                context.modify(Items.QUARTZ, builder ->
                    builder.add(DataComponentTypes.FOOD, new FoodComponent.Builder()
                            .alwaysEdible()
                            .nutrition(2)
                            .saturationModifier(1)
                            .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200), 1f)
                            .build()
                    )
        ));
    }
}
