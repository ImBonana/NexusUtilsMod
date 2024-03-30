package me.imbanana.nexusutils.util;

import me.imbanana.nexusutils.block.custom.MailBoxBlock;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public record MailBox(UUID id, BlockPos pos, UUID owner, String name) {

    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound blockPosNbt = new NbtCompound();
        blockPosNbt.putInt("x", this.pos.getX());
        blockPosNbt.putInt("y", this.pos.getY());
        blockPosNbt.putInt("z", this.pos.getZ());

        nbt.putUuid("id", this.id);
        nbt.put("pos", blockPosNbt);
        nbt.putUuid("owner", this.owner);
        nbt.putString("name", this.name);

        return nbt;
    }

    public void updateMailBoxBlock(World world) {
        if(world.getBlockState(this.pos).getBlock() instanceof MailBoxBlock mailBoxBlock) {
            mailBoxBlock.updateThing(world, this.pos);
        }
    }

    public static MailBox fromNbt(NbtCompound nbt) {
        NbtCompound blockPosNbt = nbt.getCompound("pos");
        return new MailBox(
                nbt.getUuid("id"),
                new BlockPos(blockPosNbt.getInt("x"), blockPosNbt.getInt("y"), blockPosNbt.getInt("z")),
                nbt.getUuid("owner"),
                nbt.getString("name")
        );
    }

    public void toPacket(PacketByteBuf buf) {
        buf.writeUuid(this.id);
        buf.writeBlockPos(this.pos);
        buf.writeUuid(this.owner);
        buf.writeString(this.name);
    }

    public static MailBox fromPacket(PacketByteBuf buf) {
        UUID id = buf.readUuid();
        BlockPos pos = buf.readBlockPos();
        UUID owner = buf.readUuid();
        String name = buf.readString();

        return new MailBox(id, pos, owner, name);
    }
}
