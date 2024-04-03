package me.imbanana.nexusutils.block.custom;

import com.mojang.serialization.MapCodec;
import me.imbanana.nexusutils.block.entity.SleepingBagBlockEntity;
import me.imbanana.nexusutils.block.enums.SleepingBagPart;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import me.imbanana.nexusutils.screen.backpack.BackpackInventory;
import me.imbanana.nexusutils.util.accessors.IPlayerInventory;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SleepingBagBlock extends HorizontalFacingBlock implements BlockEntityProvider {
    public static final MapCodec<SleepingBagBlock> CODEC = SleepingBagBlock.createCodec(SleepingBagBlock::new);

    public static final EnumProperty<SleepingBagPart> PART = EnumProperty.of("sleeping_bag_part", SleepingBagPart.class);
    public static final BooleanProperty OCCUPIED = Properties.OCCUPIED;
    protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
    protected static final VoxelShape TOP_SHAPE_NORTH = Block.createCuboidShape(0.0, 3.0, 0.0, 16.0, 4.0, 8.0);
    protected static final VoxelShape TOP_SHAPE_SOUTH = Block.createCuboidShape(0.0, 3.0, 8.0, 16.0, 4.0, 16.0);
    protected static final VoxelShape TOP_SHAPE_WEST = Block.createCuboidShape(0.0, 3.0, 0.0, 8.0, 4.0, 16.0);
    protected static final VoxelShape TOP_SHAPE_EAST = Block.createCuboidShape(8.0, 3.0, 0.0, 16.0, 4.0, 16.0);

    protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(BOTTOM_SHAPE, TOP_SHAPE_NORTH);
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(BOTTOM_SHAPE, TOP_SHAPE_SOUTH);
    protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(BOTTOM_SHAPE, TOP_SHAPE_WEST);
    protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(BOTTOM_SHAPE, TOP_SHAPE_EAST);

    private final DyeColor color;

    public SleepingBagBlock(Settings settings) {
        this(DyeColor.RED, settings);
    }

    public SleepingBagBlock(DyeColor dyeColor, Settings settings) {
        super(settings);
        this.color = dyeColor;
        this.setDefaultState(this.stateManager.getDefaultState().with(PART, SleepingBagPart.FOOT).with(OCCUPIED, false));
    }

    @Nullable
    public static Direction getDirection(BlockView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.getBlock() instanceof SleepingBagBlock ? blockState.get(FACING) : null;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        }
        if (state.get(PART) != SleepingBagPart.HEAD && !(state = world.getBlockState(pos = pos.offset(state.get(FACING)))).isOf(this)) {
            return ActionResult.CONSUME;
        }
        ItemStack backpackItem = ((IPlayerInventory) player.getInventory()).nexusUtils$getBackpackItemStack();
        if(player.isSneaking() && !backpackItem.isEmpty() && backpackItem.getItem() instanceof BackpackItem) {
            BackpackInventory backpackInventory = new BackpackInventory(backpackItem);
            if(backpackInventory.getSleepingBag().isEmpty()) {
                world.removeBlock(pos, false);
                BlockPos blockPos = pos.offset(state.get(FACING).getOpposite());
                if (world.getBlockState(blockPos).isOf(this)) {
                    world.removeBlock(blockPos, false);
                }
                backpackInventory.setSleepingBag(new ItemStack(this));
                return ActionResult.SUCCESS;
            }
        }
        if (!SleepingBagBlock.isSleepingBagWorking(world)) {
            world.removeBlock(pos, false);
            BlockPos blockPos = pos.offset(state.get(FACING).getOpposite());
            if (world.getBlockState(blockPos).isOf(this)) {
                world.removeBlock(blockPos, false);
            }
            Vec3d vec3d = pos.toCenterPos();
            world.createExplosion(null, world.getDamageSources().badRespawnPoint(vec3d), null, vec3d, 5.0f, true, World.ExplosionSourceType.BLOCK);
            return ActionResult.SUCCESS;
        }
        if (state.get(OCCUPIED).booleanValue()) {
            player.sendMessage(Text.translatable("block.nexusutils.sleeping_bag.occupied"), true);
            return ActionResult.SUCCESS;
        }
        player.trySleep(pos).ifLeft(reason -> {
            if (reason.getMessage() != null) {
                player.sendMessage(reason.getMessage(), true);
            }
        });
        return ActionResult.SUCCESS;
    }

    public static boolean isSleepingBagWorking(World world) {
        return world.getDimension().bedWorks();
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.onLandedUpon(world, state, pos, entity, fallDistance * 0.75f);
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
        } else {
            this.bounceEntity(entity);
        }
    }

    private void bounceEntity(Entity entity) {
        Vec3d entityVelocity = entity.getVelocity();
        if (entityVelocity.y < 0.0) {
            double d = entity instanceof LivingEntity ? 1.0 : 0.8;
            entity.setVelocity(entityVelocity.x, -entityVelocity.y * (double)0.44f * d, entityVelocity.z);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == SleepingBagBlock.getDirectionTowardsOtherPart(state.get(PART), state.get(FACING))) {
            if (neighborState.isOf(this) && neighborState.get(PART) != state.get(PART)) {
                return state.with(OCCUPIED, neighborState.get(OCCUPIED));
            }
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    private static Direction getDirectionTowardsOtherPart(SleepingBagPart part, Direction direction) {
        return part == SleepingBagPart.FOOT ? direction : direction.getOpposite();
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos blockPos;
        BlockState blockState;
        SleepingBagPart sleepingBagPart;
        if (!world.isClient && player.isCreative() && (sleepingBagPart = state.get(PART)) == SleepingBagPart.FOOT && (blockState = world.getBlockState(blockPos = pos.offset(SleepingBagBlock.getDirectionTowardsOtherPart(sleepingBagPart, state.get(FACING))))).isOf(this) && blockState.get(PART) == SleepingBagPart.HEAD) {
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
            world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, blockPos, Block.getRawIdFromState(blockState));
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(direction);
        World world = ctx.getWorld();
        if (world.getBlockState(blockPos2).canReplace(ctx) && world.getWorldBorder().contains(blockPos2)) {
            return this.getDefaultState().with(FACING, direction);
        }
        return null;
    }


    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if(state.get(SleepingBagBlock.PART) == SleepingBagPart.FOOT) return BOTTOM_SHAPE;
        Direction direction = SleepingBagBlock.getOppositePartDirection(state).getOpposite();
        return switch (direction) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;

            default -> EAST_SHAPE;
        };
    }

    public static Direction getOppositePartDirection(BlockState state) {
        Direction direction = state.get(FACING);
        return state.get(PART) == SleepingBagPart.HEAD ? direction.getOpposite() : direction;
    }

    public static DoubleBlockProperties.Type getSleepingBagPart(BlockState state) {
        SleepingBagPart sleepingBagPart = state.get(PART);
        if (sleepingBagPart == SleepingBagPart.HEAD) {
            return DoubleBlockProperties.Type.FIRST;
        }
        return DoubleBlockProperties.Type.SECOND;
    }

    private static boolean isSleepingBagBelow(BlockView world, BlockPos pos) {
        return world.getBlockState(pos.down()).getBlock() instanceof SleepingBagBlock;
    }

    public static Optional<Vec3d> findWakeUpPosition(EntityType<?> type, CollisionView world, BlockPos pos, Direction sleepingBagDirection, float spawnAngle) {
        Direction direction = sleepingBagDirection.rotateYClockwise();

        Direction direction2 = direction.pointsTo(spawnAngle) ? direction.getOpposite() : direction;
        if (SleepingBagBlock.isSleepingBagBelow(world, pos)) {
            return SleepingBagBlock.findWakeUpPosition(type, world, pos, sleepingBagDirection, direction2);
        }
        int[][] is = SleepingBagBlock.getAroundAndOnSleepingBagOffsets(sleepingBagDirection, direction2);
        Optional<Vec3d> optional = SleepingBagBlock.findWakeUpPosition(type, world, pos, is, true);
        if (optional.isPresent()) {
            return optional;
        }
        return SleepingBagBlock.findWakeUpPosition(type, world, pos, is, false);
    }

    private static Optional<Vec3d> findWakeUpPosition(EntityType<?> type, CollisionView world, BlockPos pos, Direction sleepingBagDirection, Direction respawnDirection) {
        int[][] is = SleepingBagBlock.getAroundSleepingBagOffsets(sleepingBagDirection, respawnDirection);
        Optional<Vec3d> optional = SleepingBagBlock.findWakeUpPosition(type, world, pos, is, true);
        if (optional.isPresent()) {
            return optional;
        }
        BlockPos blockPos = pos.down();
        Optional<Vec3d> optional2 = SleepingBagBlock.findWakeUpPosition(type, world, blockPos, is, true);
        if (optional2.isPresent()) {
            return optional2;
        }
        int[][] js = SleepingBagBlock.getOnSleepingBagOffsets(sleepingBagDirection);
        Optional<Vec3d> optional3 = SleepingBagBlock.findWakeUpPosition(type, world, pos, js, true);
        if (optional3.isPresent()) {
            return optional3;
        }
        Optional<Vec3d> optional4 = SleepingBagBlock.findWakeUpPosition(type, world, pos, is, false);
        if (optional4.isPresent()) {
            return optional4;
        }
        Optional<Vec3d> optional5 = SleepingBagBlock.findWakeUpPosition(type, world, blockPos, is, false);
        if (optional5.isPresent()) {
            return optional5;
        }
        return SleepingBagBlock.findWakeUpPosition(type, world, pos, js, false);
    }

    private static Optional<Vec3d> findWakeUpPosition(EntityType<?> type, CollisionView world, BlockPos pos, int[][] possibleOffsets, boolean ignoreInvalidPos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int[] is : possibleOffsets) {
            mutable.set(pos.getX() + is[0], pos.getY(), pos.getZ() + is[1]);
            Vec3d vec3d = Dismounting.findRespawnPos(type, world, mutable, ignoreInvalidPos);
            if (vec3d == null) continue;
            return Optional.of(vec3d);
        }
        return Optional.empty();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, OCCUPIED);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SleepingBagBlockEntity(pos, state, this.color);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            BlockPos blockPos = pos.offset(state.get(FACING));
            world.setBlockState(blockPos, state.with(PART, SleepingBagPart.HEAD), Block.NOTIFY_ALL);
            world.updateNeighbors(pos, Blocks.AIR);
            state.updateNeighbors(world, pos, Block.NOTIFY_ALL);
        }
    }

    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public long getRenderingSeed(BlockState state, BlockPos pos) {
        BlockPos blockPos = pos.offset(state.get(FACING), state.get(PART) == SleepingBagPart.HEAD ? 0 : 1);
        return MathHelper.hashCode(blockPos.getX(), pos.getY(), blockPos.getZ());
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    private static int[][] getAroundAndOnSleepingBagOffsets(Direction sleepingBagDirection, Direction respawnDirection) {
        return ArrayUtils.addAll(SleepingBagBlock.getAroundSleepingBagOffsets(sleepingBagDirection, respawnDirection), SleepingBagBlock.getOnSleepingBagOffsets(sleepingBagDirection));
    }

    private static int[][] getAroundSleepingBagOffsets(Direction sleepingBagDirection, Direction respawnDirection) {
        return new int[][]{{respawnDirection.getOffsetX(), respawnDirection.getOffsetZ()}, {respawnDirection.getOffsetX() - sleepingBagDirection.getOffsetX(), respawnDirection.getOffsetZ() - sleepingBagDirection.getOffsetZ()}, {respawnDirection.getOffsetX() - sleepingBagDirection.getOffsetX() * 2, respawnDirection.getOffsetZ() - sleepingBagDirection.getOffsetZ() * 2}, {-sleepingBagDirection.getOffsetX() * 2, -sleepingBagDirection.getOffsetZ() * 2}, {-respawnDirection.getOffsetX() - sleepingBagDirection.getOffsetX() * 2, -respawnDirection.getOffsetZ() - sleepingBagDirection.getOffsetZ() * 2}, {-respawnDirection.getOffsetX() - sleepingBagDirection.getOffsetX(), -respawnDirection.getOffsetZ() - sleepingBagDirection.getOffsetZ()}, {-respawnDirection.getOffsetX(), -respawnDirection.getOffsetZ()}, {-respawnDirection.getOffsetX() + sleepingBagDirection.getOffsetX(), -respawnDirection.getOffsetZ() + sleepingBagDirection.getOffsetZ()}, {sleepingBagDirection.getOffsetX(), sleepingBagDirection.getOffsetZ()}, {respawnDirection.getOffsetX() + sleepingBagDirection.getOffsetX(), respawnDirection.getOffsetZ() + sleepingBagDirection.getOffsetZ()}};
    }

    private static int[][] getOnSleepingBagOffsets(Direction sleepingBagDirection) {
        return new int[][]{{0, 0}, {-sleepingBagDirection.getOffsetX(), -sleepingBagDirection.getOffsetZ()}};
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }
}
