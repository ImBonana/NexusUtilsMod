package me.imbanana.nexusutils.block.entity;

import me.imbanana.nexusutils.block.custom.CopperHopperBlock;
import me.imbanana.nexusutils.item.ModItems;
import me.imbanana.nexusutils.item.custom.HopperFilterItem;
import me.imbanana.nexusutils.screen.copperhopper.CopperHopperScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CopperHopperBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory, Hopper {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;

    private boolean whitelist = true;

    private static final int FILTER_SLOT = 0;
    private int transferCooldown = -1;
    private long lastTickTime;

    @Override
    public double getHopperX() {
        return (double)this.pos.getX() + 0.5;
    }

    @Override
    public double getHopperY() {
        return (double)this.pos.getY() + 0.5;
    }

    @Override
    public double getHopperZ() {
        return (double)this.pos.getZ() + 0.5;
    }

    public CopperHopperBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COPPER_HOPPER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                if(index == 0) {
                    return CopperHopperBlockEntity.this.whitelist ? 1 : 0;
                }

                return 0;
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) {
                    CopperHopperBlockEntity.this.whitelist = (value == 1);
                }
            }

            @Override
            public int size() {
                return 1;
            }
        };
    }

    public void setWhitelist(boolean value) {
        this.whitelist = value;
    }

    @Override
    public void markDirty() {
        this.world.updateListeners(this.pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
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
    public Text getDisplayName() {
        return Text.translatable("gui.nexusutils.copper_hopper");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("TransferCooldown", this.transferCooldown);
        nbt.putBoolean("IsWhitelist", this.whitelist);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        this.transferCooldown = nbt.getInt("TransferCooldown");
        this.whitelist = nbt.getBoolean("IsWhitelist");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CopperHopperScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
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


    public static void serverTick(World world, BlockPos pos, BlockState state, CopperHopperBlockEntity blockEntity) {
        --blockEntity.transferCooldown;
        blockEntity.lastTickTime = world.getTime();
        if (!blockEntity.needsCooldown()) {
            blockEntity.setTransferCooldown(0);
            CopperHopperBlockEntity.insertAndExtract(world, pos, state, blockEntity, () -> CopperHopperBlockEntity.extract(world, blockEntity));
        }
    }

    private static boolean insertAndExtract(World world, BlockPos pos, BlockState state, CopperHopperBlockEntity blockEntity, BooleanSupplier booleanSupplier) {
        if (world.isClient) {
            return false;
        }
        if (!blockEntity.needsCooldown() && state.get(CopperHopperBlock.ENABLED).booleanValue()) {
            boolean bl = false;
            if (!blockEntity.isEmpty()) {
                bl = CopperHopperBlockEntity.insert(world, pos, blockEntity);
            }
            if (!blockEntity.isFull()) {
                bl |= booleanSupplier.getAsBoolean();
            }
            if (bl) {
                blockEntity.setTransferCooldown(8);
                CopperHopperBlockEntity.markDirty(world, pos, state);
                return true;
            }
        }
        return false;
    }

    private boolean isFull() {
        for (ItemStack itemStack : this.inventory) {
            if (!itemStack.isEmpty() && itemStack.getCount() == itemStack.getMaxCount()) continue;
            return false;
        }
        return true;
    }

    private static boolean insert(World world, BlockPos pos, Inventory inventory) {
        Inventory inventory2 = CopperHopperBlockEntity.getOutputInventory(world, pos);
        if (inventory2 == null) {
            return false;
        }
        Direction direction = Direction.DOWN;
        if (CopperHopperBlockEntity.isInventoryFull(inventory2, direction)) {
            return false;
        }
        for (int i = 1; i < inventory.size(); ++i) {
            if (inventory.getStack(i).isEmpty()) continue;
            ItemStack itemStack = inventory.getStack(i).copy();
            ItemStack itemStack2 = CopperHopperBlockEntity.transfer(inventory, inventory2, inventory.removeStack(i, 1), direction);
            if (itemStack2.isEmpty()) {
                inventory2.markDirty();
                return true;
            }
            inventory.setStack(i, itemStack);
        }
        return false;
    }

    private static IntStream getAvailableSlots(Inventory inventory, Direction side) {
        if (inventory instanceof SidedInventory) {
            return IntStream.of(((SidedInventory)inventory).getAvailableSlots(side));
        }
        return IntStream.range(0, inventory.size());
    }

    private static boolean isInventoryFull(Inventory inventory, Direction direction) {
        return CopperHopperBlockEntity.getAvailableSlots(inventory, direction).allMatch(slot -> {
            ItemStack itemStack = inventory.getStack(slot);
            return itemStack.getCount() >= itemStack.getMaxCount();
        });
    }

    private static boolean isInventoryEmpty(Inventory inv, Direction facing) {
        return CopperHopperBlockEntity.getAvailableSlots(inv, facing).allMatch(slot -> inv.getStack(slot).isEmpty());
    }

    public static boolean extract(World world, Hopper hopper) {
        Inventory inventory = CopperHopperBlockEntity.getInputInventory(world, hopper);
        if (inventory != null) {
            Direction direction = Direction.DOWN;
            if (CopperHopperBlockEntity.isInventoryEmpty(inventory, direction)) {
                return false;
            }
            return CopperHopperBlockEntity.getAvailableSlots(inventory, direction).anyMatch(slot -> CopperHopperBlockEntity.extract(hopper, inventory, slot, direction));
        }
        for (ItemEntity itemEntity : CopperHopperBlockEntity.getInputItemEntities(world, hopper)) {
            if (!CopperHopperBlockEntity.extract(hopper, itemEntity)) continue;
            return true;
        }
        return false;
    }

    private static boolean extract(Hopper hopper, Inventory inventory, int slot, Direction side) {
        ItemStack itemStack = inventory.getStack(slot);
        if (!itemStack.isEmpty() && CopperHopperBlockEntity.canExtract(hopper, inventory, itemStack, slot, side)) {
            ItemStack itemStack2 = itemStack.copy();
            ItemStack itemStack3 = CopperHopperBlockEntity.transfer(inventory, hopper, inventory.removeStack(slot, 1), null);
            if (itemStack3.isEmpty()) {
                inventory.markDirty();
                return true;
            }
            inventory.setStack(slot, itemStack2);
        }
        return false;
    }

    public static boolean extract(Inventory inventory, ItemEntity itemEntity) {
        boolean bl = false;
        ItemStack itemStack = itemEntity.getStack().copy();
        ItemStack itemStack2 = CopperHopperBlockEntity.transfer(null, inventory, itemStack, null);
        if (itemStack2.isEmpty()) {
            bl = true;
            itemEntity.setStack(ItemStack.EMPTY);
            itemEntity.discard();
        } else {
            itemEntity.setStack(itemStack2);
        }
        return bl;
    }

    public static ItemStack transfer(@Nullable Inventory from, Inventory to, ItemStack stack, @Nullable Direction side) {
        if (to instanceof SidedInventory sidedInventory) {
            if (side != null) {
                int[] is = sidedInventory.getAvailableSlots(side);
                int i = 0;
                while (i < is.length) {
                    if (stack.isEmpty()) return stack;
                    stack = CopperHopperBlockEntity.transfer(from, to, stack, is[i], side);
                    ++i;
                }
                return stack;
            }
        }
        int j = to.size();
        int i = 0;
        while (i < j) {
            if (stack.isEmpty()) return stack;
            stack = CopperHopperBlockEntity.transfer(from, to, stack, i, side);
            ++i;
        }
        return stack;
    }

    private static boolean canInsert(Inventory inventory, ItemStack stack, int slot, @Nullable Direction side) {
        if (!inventory.isValid(slot, stack)) {
            return false;
        }
        return !(inventory instanceof SidedInventory) || ((SidedInventory)inventory).canInsert(slot, stack, side);
    }

    private static boolean canExtract(Inventory hopperInventory, Inventory fromInventory, ItemStack stack, int slot, Direction facing) {
        if (!fromInventory.canTransferTo(hopperInventory, slot, stack)) {
            return false;
        }
        return !(fromInventory instanceof SidedInventory) || ((SidedInventory)fromInventory).canExtract(slot, stack, facing);
    }

    private static ItemStack transfer(@Nullable Inventory from, Inventory to, ItemStack stack, int slot, @Nullable Direction side) {
        // my inject
        if(to instanceof CopperHopperBlockEntity && slot == FILTER_SLOT) return stack;
        if(to instanceof CopperHopperBlockEntity copperHopperBlockEntity) {
            ItemStack filterItem = copperHopperBlockEntity.inventory.get(0);

            if(filterItem.getItem() == ModItems.HOPPER_FILTER) {
                NbtCompound filterNbt = filterItem.getOrCreateNbt();
                DefaultedList<ItemStack> itemsToFilter = DefaultedList.ofSize(HopperFilterItem.INVENTORY_SIZE, ItemStack.EMPTY);
                Inventories.readNbt(filterNbt, itemsToFilter);
                if(shouldInvert(copperHopperBlockEntity.whitelist, filterHasItem(itemsToFilter, stack))) return stack;

            } else if(!filterItem.isEmpty() && shouldInvert(copperHopperBlockEntity.whitelist, filterItem.getItem() == stack.getItem())) return stack;
        }
        // end
        ItemStack itemStack = to.getStack(slot);
        if (CopperHopperBlockEntity.canInsert(to, stack, slot, side)) {
            int j;
            boolean bl = false;
            boolean bl2 = to.isEmpty();
            if (itemStack.isEmpty()) {
                to.setStack(slot, stack);
                stack = ItemStack.EMPTY;
                bl = true;
            } else if (CopperHopperBlockEntity.canMergeItems(itemStack, stack)) {
                int i = stack.getMaxCount() - itemStack.getCount();
                j = Math.min(stack.getCount(), i);
                stack.decrement(j);
                itemStack.increment(j);
                bl = j > 0;
            }
            if (bl) {
                CopperHopperBlockEntity hopperBlockEntity;
                if (bl2 && to instanceof CopperHopperBlockEntity && !(hopperBlockEntity = (CopperHopperBlockEntity)to).isDisabled()) {
                    j = 0;
                    if (from instanceof CopperHopperBlockEntity hopperBlockEntity2) {
                        if (hopperBlockEntity.lastTickTime >= hopperBlockEntity2.lastTickTime) {
                            j = 1;
                        }
                    }
                    hopperBlockEntity.setTransferCooldown(8 - j);
                }
                to.markDirty();
            }
        }
        return stack;
    }

    @Nullable
    private static Inventory getOutputInventory(World world, BlockPos pos) {
        Direction direction = Direction.DOWN;
        return CopperHopperBlockEntity.getInventoryAt(world, pos.offset(direction));
    }

    @Nullable
    private static Inventory getInputInventory(World world, Hopper hopper) {
        return CopperHopperBlockEntity.getInventoryAt(world, hopper.getHopperX(), hopper.getHopperY() + 1.0, hopper.getHopperZ());
    }

    public static List<ItemEntity> getInputItemEntities(World world, Hopper hopper) {
        return hopper.getInputAreaShape().getBoundingBoxes().stream().flatMap(box -> world.getEntitiesByClass(ItemEntity.class, box.offset(hopper.getHopperX() - 0.5, hopper.getHopperY() - 0.5, hopper.getHopperZ() - 0.5), EntityPredicates.VALID_ENTITY).stream()).collect(Collectors.toList());
    }

    @Nullable
    public static Inventory getInventoryAt(World world, BlockPos pos) {
        return CopperHopperBlockEntity.getInventoryAt(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5);
    }

    @Nullable
    private static Inventory getInventoryAt(World world, double x, double y, double z) {
        List<Entity> list;
        BlockEntity blockEntity;
        Inventory inventory = null;
        BlockPos blockPos = BlockPos.ofFloored(x, y, z);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof InventoryProvider) {
            inventory = ((InventoryProvider) block).getInventory(blockState, world, blockPos);
        } else if (blockState.hasBlockEntity() && (blockEntity = world.getBlockEntity(blockPos)) instanceof Inventory && (inventory = (Inventory) blockEntity) instanceof ChestBlockEntity && block instanceof ChestBlock) {
            inventory = ChestBlock.getInventory((ChestBlock)block, blockState, world, blockPos, true);
        }
        if (inventory == null && !(list = world.getOtherEntities(null, new Box(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5), EntityPredicates.VALID_INVENTORIES)).isEmpty()) {
            inventory = (Inventory) list.get(world.random.nextInt(list.size()));
        }
        return inventory;
    }

    private static boolean canMergeItems(ItemStack first, ItemStack second) {
        return first.getCount() <= first.getMaxCount() && ItemStack.canCombine(first, second);
    }

    private void setTransferCooldown(int transferCooldown) {
        this.transferCooldown = transferCooldown;
    }

    private boolean needsCooldown() {
        return this.transferCooldown > 0;
    }

    private boolean isDisabled() {
        return this.transferCooldown > 8;
    }

    private static boolean filterHasItem(DefaultedList<ItemStack> stacks, ItemStack stack) {
        // make code here

        for(ItemStack s : stacks) {
            if(s.isEmpty()) continue;
            if(stack.getItem() == s.getItem()) return true;
        }

        return false;
    }

    private static boolean shouldInvert(boolean invert, boolean expr) {
        if(invert) return !expr;
        return expr;
    }
}
