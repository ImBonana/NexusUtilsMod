package me.imbanana.nexusutils.block.entity;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.custom.MailBoxBlock;
import me.imbanana.nexusutils.screen.mailbox.MailBoxScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MailBoxBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, Nameable {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    private Text customName;

    public MailBoxBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MAIL_BOX_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    @Override
    public void onClose(PlayerEntity player) {
        if(getCachedState().getBlock() instanceof MailBoxBlock mailBoxBlock) {
            mailBoxBlock.updateThing(world, pos, getCachedState(), this);
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getName() {
        if (this.customName != null) {
            return this.customName;
        }
        return Text.translatable("gui.nexusutils.mail_box");
    }

    public void setCustomName(@Nullable Text customName) {
        this.customName = customName;
    }

    @Override
    @Nullable
    public Text getCustomName() {
        return this.customName;
    }

    @Override
    public Text getDisplayName() {
        return this.getName();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        if (this.hasCustomName()) {
            nbt.putString("CustomName", Text.Serialization.toJsonString(this.customName));
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        if (nbt.contains("CustomName", NbtElement.STRING_TYPE)) {
            this.customName = Text.Serialization.fromJson(nbt.getString("CustomName"));
        }
    }

    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MailBoxScreenHandler(syncId, playerInventory, this);
    }
}