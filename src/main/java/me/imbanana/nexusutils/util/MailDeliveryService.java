package me.imbanana.nexusutils.util;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.block.entity.MailBoxBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MailDeliveryService extends PersistentState {
    public final HashMap<UUID, MailBox> mailBoxes;

    private final ServerWorld world;

    public MailDeliveryService(ServerWorld world) {
        this.world = world;
        this.mailBoxes = new HashMap<>();
    }

    public static PersistentState.Type<MailDeliveryService> getPersistentStateType(ServerWorld world) {
        return new PersistentState.Type<>(() -> new MailDeliveryService(world), nbt -> MailDeliveryService.fromNbt(world, nbt), null);
    }

    public void createMailBox(MailBox mailBox) {
        this.mailBoxes.put(mailBox.id(), mailBox);
        this.markDirty();
    }

    public void deleteMailBox(MailBox mailBox) {
        this.deleteMailBox(mailBox.id());
    }

    public void deleteMailBox(UUID id) {
        this.mailBoxes.remove(id);
        this.markDirty();
    }

    public void deleteMailBox(BlockPos pos) {
        Optional<MailBox> mailBox = this.getMailBox(pos);
        mailBox.ifPresent(this::deleteMailBox);
    }

    public List<MailBox> getAllMailBoxes() {
        return this.mailBoxes.values().stream().toList();
    }

    @Nullable
    public MailBox getMailBox(UUID id) {
        return this.mailBoxes.get(id);
    }

    public Optional<MailBox> getMailBox(BlockPos pos) {
        return this.mailBoxes.values().stream().filter(mailBox -> mailBox.pos().equals(pos)).findFirst();
    }

    public MailSendResult sendMail(MailBox mailBox, ItemStack stack) {
        if(this.world.getBlockEntity(mailBox.pos()) instanceof MailBoxBlockEntity mailBoxBlockEntity) {
            Inventory mailBoxInventory = mailBoxBlockEntity;

            int emptySlotId = -1;
            for (int i = 0; i < mailBoxInventory.size(); i++) {
                if(mailBoxInventory.getStack(i).isEmpty()) {
                    emptySlotId = i;
                    break;
                }
            }

            if(emptySlotId == -1) return MailSendResult.FULL;

            mailBoxInventory.setStack(emptySlotId, stack);
            mailBox.updateMailBoxBlock(this.world);

            return MailSendResult.SUCCESS;
        }

        this.deleteMailBox(mailBox);
        return MailSendResult.NOT_FOUND;
    }



    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList nbtList = new NbtList();
        for (MailBox mailBox : this.mailBoxes.values()) {
            NbtCompound nbtCompound = new NbtCompound();
            mailBox.writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        nbt.put("MailBoxes", nbtList);

        NexusUtils.LOGGER.info("Mail Boxes are saved!");
        return nbt;
    }

    public static MailDeliveryService fromNbt(ServerWorld world, NbtCompound nbt) {
        MailDeliveryService mailDeliveryService = new MailDeliveryService(world);

        NbtList nbtList = nbt.getList("MailBoxes", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            MailBox mailBox = MailBox.fromNbt(nbtCompound);
            mailDeliveryService.mailBoxes.put(mailBox.id(), mailBox);
        }
        return mailDeliveryService;
    }


    public enum MailSendResult {
        NOT_FOUND,
        FULL,
        SUCCESS
    }
}