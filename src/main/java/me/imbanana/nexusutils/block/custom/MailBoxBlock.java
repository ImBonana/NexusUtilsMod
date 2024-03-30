package me.imbanana.nexusutils.block.custom;

import com.mojang.serialization.MapCodec;
import me.imbanana.nexusutils.block.entity.MailBoxBlockEntity;
import me.imbanana.nexusutils.util.MailBox;
import me.imbanana.nexusutils.util.MailDeliveryService;
import me.imbanana.nexusutils.util.accessors.IServerWorld;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MailBoxBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public static final MapCodec<MailBoxBlock> CODEC = MailBoxBlock.createCodec(MailBoxBlock::new);

    private static final VoxelShape NORMAL_SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(7, 0, 7, 9, 12, 9), Block.createCuboidShape(5, 12, 2, 11, 18, 14), BooleanBiFunction.OR);
    private static final VoxelShape SIDE_SHAPE = VoxelShapes.combineAndSimplify(Block.createCuboidShape(7, 0, 7, 9, 12, 9), Block.createCuboidShape(2, 12, 5, 14, 18, 11), BooleanBiFunction.OR);

    private static final BooleanProperty THING = BooleanProperty.of("thing_up");

    public MailBoxBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING ,Direction.NORTH).with(THING, false));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
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
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MailBoxBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof MailBoxBlockEntity) {
                ItemScatterer.spawn(world, pos, (MailBoxBlockEntity) blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if(world.getBlockEntity(pos) instanceof MailBoxBlockEntity mailBoxBlockEntity) {
            if(mailBoxBlockEntity.isEmpty()) {
                return state.with(THING, false);
            }
            return state.with(THING, true);
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public void updateThing(World world, BlockPos pos) {
        updateThing(world, pos, world.getBlockState(pos), world.getBlockEntity(pos));
    }

    public void updateThing(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if(blockEntity instanceof MailBoxBlockEntity mailBoxBlockEntity) {
            if(mailBoxBlockEntity.isEmpty()) {
                world.setBlockState(pos, state.with(THING, false));
                return;
            }
            world.setBlockState(pos, state.with(THING, true));
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();

        return this.getDefaultState().with(FACING, direction.getOpposite());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = (MailBoxBlockEntity) world.getBlockEntity(pos);

            if(screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MailBoxBlockEntity mailBoxBlock) {
            mailBoxBlock.setCustomName(itemStack.hasCustomName() ? itemStack.getName() : Text.translatable("block.nexusutils.mail_box.player", placer.getName()));
            if(!world.isClient && placer instanceof PlayerEntity) {
                ServerWorld serverWorld = (ServerWorld) world;
                MailDeliveryService mailDeliveryService = ((IServerWorld) serverWorld).nexusUtils$getMailDeliveryService();
                MailBox mailBox = new MailBox(UUID.randomUUID(), pos, placer.getUuid(), mailBoxBlock.getCustomName().getString());

                mailDeliveryService.createMailBox(mailBox);
            }
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            MailDeliveryService mailDeliveryService = ((IServerWorld) serverWorld).nexusUtils$getMailDeliveryService();

            mailDeliveryService.deleteMailBox(pos);
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, THING);
    }
}
