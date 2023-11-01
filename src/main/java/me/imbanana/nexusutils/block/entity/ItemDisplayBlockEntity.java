package me.imbanana.nexusutils.block.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import me.imbanana.nexusutils.screen.itemdisplay.ItemDisplayScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationCalculator;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ItemDisplayBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;

    private int rotationValue = 0;

    public ItemDisplayBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ITEM_DISPLAY_BLOCK_ENTITY, pos, state);
    }

    public ItemStack getRenderStack() {
        return this.getStack(INPUT_SLOT);
    }

    @Override
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.nexusutils.item_display");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ItemDisplayScreenHandler(syncId, playerInventory, this);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(!world.isClient) return;

        ++this.rotationValue;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public float getRotation(float deltaTick) {
        float deg = ((float)this.rotationValue + deltaTick) * 2;
        if(deg >= 360) {
            deg = 0;
            this.rotationValue = 0;
        }
        return deg;
    }
}
