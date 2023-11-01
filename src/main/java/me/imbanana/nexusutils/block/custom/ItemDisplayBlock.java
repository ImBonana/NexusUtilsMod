package me.imbanana.nexusutils.block.custom;

import me.imbanana.nexusutils.block.entity.ItemDisplayBlockEntity;
import me.imbanana.nexusutils.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class ItemDisplayBlock extends BlockWithEntity implements BlockEntityProvider {
//    private static final VoxelShape SHAPE = VoxelShapes.fullCube();
    private static final VoxelShape SHAPE = Stream.of(
        Stream.of(
                Block.createCuboidShape(6.5, 14, 6.5, 9.5, 14.75, 9.5),
                Block.createCuboidShape(6.75, 14.75, 6.75, 9.25, 15, 9.25)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get(),
            Block.createCuboidShape(2, 2, 2, 14, 14, 14),
            Block.createCuboidShape(0, 0, 0, 16, 2, 16)
        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();


    public ItemDisplayBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemDisplayBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof ItemDisplayBlockEntity) {
                ItemScatterer.spawn(world, pos, (ItemDisplayBlockEntity) blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = (ItemDisplayBlockEntity) world.getBlockEntity(pos);

            if(screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.ITEM_DISPLAY_BLOCK_ENTITY, (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
