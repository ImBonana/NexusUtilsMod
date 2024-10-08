package me.imbanana.nexusutils.screen.postbox;

import me.imbanana.nexusutils.networking.packets.screens.ScreenOpeningData;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PostBoxScreenHandlerFactory implements ExtendedScreenHandlerFactory<ScreenOpeningData> {
    private final World world;
    private final BlockPos blockPos;

    public PostBoxScreenHandlerFactory(World world, BlockPos blockPos) {
        this.world = world;
        this.blockPos = blockPos;
    }

    @Override
    public ScreenOpeningData getScreenOpeningData(ServerPlayerEntity player) {
        return new ScreenOpeningData();
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.nexusutils.post_box");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PostBoxScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(this.world, this.blockPos));
    }
}
