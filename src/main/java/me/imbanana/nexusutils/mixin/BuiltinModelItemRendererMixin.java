package me.imbanana.nexusutils.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.ModBlocks;
import me.imbanana.nexusutils.block.custom.SleepingBagBlock;
import me.imbanana.nexusutils.block.entity.SleepingBagBlockEntity;
import me.imbanana.nexusutils.entity.client.ModModelLayers;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.item.backpack.BackpackEntityModel;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import me.imbanana.nexusutils.screen.backpack.BackpackInventory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(value = BuiltinModelItemRenderer.class, priority = 800)
public abstract class BuiltinModelItemRendererMixin implements SynchronousResourceReloader {
    @Shadow @Final private BlockEntityRenderDispatcher blockEntityRenderDispatcher;
    @Shadow @Final private EntityModelLoader entityModelLoader;
    @Unique private final SleepingBagBlockEntity renderSleepingBag = new SleepingBagBlockEntity(BlockPos.ORIGIN, ModBlocks.RED_SLEEPING_BAG.getDefaultState(), true);
    @Unique private BackpackEntityModel backpackEntityModel;

    @Inject(method = "reload", at = @At(value = "HEAD"))
    private void injectReload(ResourceManager manager, CallbackInfo ci) {
        this.backpackEntityModel = new BackpackEntityModel(this.entityModelLoader.getModelPart(ModModelLayers.BACKPACK));
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void injectRender(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci, Item item, Block block) {
        if(block instanceof SleepingBagBlock) {
            this.renderSleepingBag.setColor(((SleepingBagBlock)block).getColor());
            this.blockEntityRenderDispatcher.renderEntity(this.renderSleepingBag, matrices, vertexConsumers, light, overlay);
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", opcode = 0, shift = At.Shift.BEFORE), cancellable = true)
    private void injectRenderEntityModel(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo info) {
        if(stack.isOf(ModItems.BACKPACK)) {
            BackpackInventory backpackInventory = new BackpackInventory(stack);
            ItemStack sleepingBagItem = backpackInventory.getSleepingBag();

            matrices.push();
            matrices.scale(1.0f, -1.0f, -1.0f);
            matrices.translate(0.5f, -1.5f, -0.5f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
            VertexConsumer vertexConsumer2 = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.backpackEntityModel.getLayer(BackpackEntityModel.TEXTURE), false, stack.hasGlint());
            this.backpackEntityModel.render(sleepingBagItem != null && !sleepingBagItem.isEmpty(), matrices, vertexConsumer2, light, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
            matrices.pop();
            info.cancel();
        }
    }
}
