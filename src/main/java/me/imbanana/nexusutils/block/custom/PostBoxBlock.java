package me.imbanana.nexusutils.block.custom;

import com.mojang.serialization.MapCodec;
import me.imbanana.nexusutils.screen.postbox.PostBoxScreenHandlerFactory;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class PostBoxBlock extends HorizontalFacingBlock {
    public static final MapCodec<PostBoxBlock> CODEC = MailBoxBlock.createCodec(PostBoxBlock::new);

    private static final VoxelShape BODY_SHAPE = Stream.of(
            Block.createCuboidShape(0, 0, 0, 2, 3, 2),
            Block.createCuboidShape(14, 0, 0, 16, 3, 2),
            Block.createCuboidShape(14, 0, 14, 16, 3, 16),
            Block.createCuboidShape(0, 0, 14, 2, 3, 16),
            Block.createCuboidShape(0, 3, 0, 16, 14, 16)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static final VoxelShape NORMAL_SHAPE = Stream.of(
            BODY_SHAPE,
            Block.createCuboidShape(0, 14, 1, 16, 15, 15),
            Block.createCuboidShape(0, 15, 2, 16, 16, 14),
            Block.createCuboidShape(0, 16, 3, 16, 17, 13)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static final VoxelShape SIDE_SHAPE = Stream.of(
            BODY_SHAPE,
            Block.createCuboidShape(1, 14, 0, 15, 15, 16),
            Block.createCuboidShape(2, 15, 0, 14, 16, 16),
            Block.createCuboidShape(3, 16, 0, 13, 17, 16)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();


    public PostBoxBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();

        return this.getDefaultState().with(FACING, direction.getOpposite());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);

        return switch (direction) {
            default -> NORMAL_SHAPE;

            case EAST, WEST -> SIDE_SHAPE;
        };
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));

        return ActionResult.CONSUME;
    }

    @Nullable
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new PostBoxScreenHandlerFactory(world, pos);
    }
}
