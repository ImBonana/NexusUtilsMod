package me.imbanana.nexusutils.block.entity;

import me.imbanana.nexusutils.block.custom.SleepingBagBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class SleepingBagBlockEntity extends BlockEntity {
    private DyeColor color;
    private boolean shouldRenderFull = false;

    public SleepingBagBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SLEEPING_BAG_BLOCK_ENTITY, pos, state);
        this.color = ((SleepingBagBlock)state.getBlock()).getColor();
    }

    public SleepingBagBlockEntity(BlockPos pos, BlockState state, boolean shouldRenderFull) {
        super(ModBlockEntities.SLEEPING_BAG_BLOCK_ENTITY, pos, state);
        this.color = ((SleepingBagBlock)state.getBlock()).getColor();
        this.shouldRenderFull = shouldRenderFull;
    }

    public SleepingBagBlockEntity(BlockPos pos, BlockState state, DyeColor color) {
        super(ModBlockEntities.SLEEPING_BAG_BLOCK_ENTITY, pos, state);
        this.color = color;
    }
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public void setColor(DyeColor color) {
        this.color = color;
    }

    public boolean isShouldRenderFull() {
        return this.shouldRenderFull;
    }
}
