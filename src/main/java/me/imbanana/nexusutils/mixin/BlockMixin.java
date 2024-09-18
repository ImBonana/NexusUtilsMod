package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements ItemConvertible, FabricBlock {

    public BlockMixin(Settings settings) {
        super(settings);
    }

    @Redirect(
            method = "dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;)Ljava/util/List;"
            )
    )
    private static List<ItemStack> redirectDropStacks(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack tool) {
        if(entity instanceof PlayerEntity player) {
            List<ItemStack> drops = Block.getDroppedStacks(state, world, pos, blockEntity, player, tool);

            if(EnchantmentHelper.hasAnyEnchantmentsWith(tool, ModEnchantmentEffectComponentTypes.AUTO_SMELT)) {
                drops = drops.stream().map(itemStack -> {
                    Optional<RecipeEntry<SmeltingRecipe>> recipeEntry = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, new SingleStackRecipeInput(itemStack), world);
                    if(recipeEntry.isPresent()) {
                        ItemStack result = recipeEntry.get().value().getResult(world.getRegistryManager()).copyWithCount(itemStack.getCount());
                        if(result != null && !result.isEmpty()) {
                            return result;
                        }
                    }

                    return itemStack;
                }).toList();
            }

            //! NEED TO BE LAST
            if(EnchantmentHelper.hasAnyEnchantmentsWith(tool, ModEnchantmentEffectComponentTypes.TELEPARHY)) {
                drops.forEach(item -> player.getInventory().offerOrDrop(item));
                return new ArrayList<>();
            }

            return drops;
        }

        return Block.getDroppedStacks(state, world, pos, blockEntity, entity, tool);
    }
}
