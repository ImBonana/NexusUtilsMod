package me.imbanana.nexusutils.block.entity;

import me.imbanana.nexusutils.networking.packets.screens.BlockEntityScreenOpeningData;
import me.imbanana.nexusutils.screen.itemdisplay.ItemDisplayScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ItemDisplayBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockEntityScreenOpeningData>, ImplementedInventory {
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
    public BlockEntityScreenOpeningData getScreenOpeningData(ServerPlayerEntity player) {
        return new BlockEntityScreenOpeningData(this.pos);
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
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
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
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
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
