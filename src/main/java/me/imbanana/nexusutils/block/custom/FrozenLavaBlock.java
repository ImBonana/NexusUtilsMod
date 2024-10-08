package me.imbanana.nexusutils.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class FrozenLavaBlock extends Block {
    public static final MapCodec<FrozenLavaBlock> CODEC = createCodec(FrozenLavaBlock::new);
    public static final IntProperty AGE = Properties.AGE_3;

    public FrozenLavaBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, Integer.valueOf(0)));
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, MathHelper.nextInt(world.getRandom(), 60, 120));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        this.melt(world, pos);
        super.afterBreak(world, player, pos, state, blockEntity, tool);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((random.nextInt(3) == 0 || this.canMelt(world, pos, 4)) && this.increaseAge(state, world, pos)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (Direction direction : Direction.values()) {
                mutable.set(pos, direction);
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isOf(this) || this.increaseAge(blockState, world, mutable)) continue;
                world.scheduleBlockTick(mutable, this, MathHelper.nextInt(random, 20, 40));
            }
            return;
        }
        world.scheduleBlockTick(pos, this, MathHelper.nextInt(random, 20, 40));
    }

    private boolean increaseAge(BlockState state, World world, BlockPos pos) {
        int i = state.get(AGE);
        if (i < 3) {
            world.setBlockState(pos, state.with(AGE, i + 1), Block.NOTIFY_LISTENERS);
            return false;
        }
        this.melt(world, pos);
        return true;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (sourceBlock.getDefaultState().isOf(this) && this.canMelt(world, pos, 2)) {
            this.melt(world, pos);
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    private boolean canMelt(BlockView world, BlockPos pos, int maxNeighbors) {
        int i = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {
            mutable.set(pos, direction);
            if (!world.getBlockState(mutable).isOf(this) || ++i < maxNeighbors) continue;
            return false;
        }
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    public static BlockState getMeltedState() {
        return Blocks.LAVA.getDefaultState();
    }

    protected void melt(World world, BlockPos pos) {
        world.setBlockState(pos, FrozenLavaBlock.getMeltedState());
        world.updateNeighbor(pos, FrozenLavaBlock.getMeltedState().getBlock(), pos);
    }

    @Override
    protected MapCodec<FrozenLavaBlock> getCodec() {
        return CODEC;
    }
}
