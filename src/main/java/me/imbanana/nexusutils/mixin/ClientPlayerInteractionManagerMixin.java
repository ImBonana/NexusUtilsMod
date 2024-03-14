package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.enchantment.ModEnchantments;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(
            method = "breakBlock",
            at = @At("HEAD")
    )
    private void onBreakBlockClient(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        // If the player is holding the tool, we want to let the server handle breaking mechanics.
        // This prevents a small quirk where the middle block in a 3x3 grid would break before the other blocks.
        int blastEnchantmentTarget = EnchantmentHelper.getLevel(ModEnchantments.BLAST, client.player.getMainHandStack());
        int timberEnchantmentTarget = EnchantmentHelper.getLevel(ModEnchantments.TIMBER, client.player.getMainHandStack());

        if(client.player != null && (blastEnchantmentTarget > 0 || timberEnchantmentTarget > 0)) {
            cir.cancel();
            World world = this.client.world;

            if(world != null) {
                BlockState blockState = world.getBlockState(pos);
                Block block = blockState.getBlock();
                block.onBreak(world, pos, blockState, this.client.player);
            }
        }
    }
}
