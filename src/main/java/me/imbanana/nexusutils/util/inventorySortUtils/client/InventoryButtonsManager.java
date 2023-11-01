package me.imbanana.nexusutils.util.inventorySortUtils.client;

import me.imbanana.nexusutils.util.Position2;
import me.imbanana.nexusutils.util.inventorySortUtils.InventoryUtils;
import me.imbanana.nexusutils.util.inventorySortUtils.client.gui.AutoStackButton;
import me.imbanana.nexusutils.util.inventorySortUtils.client.gui.InventoryManagementButton;
import me.imbanana.nexusutils.util.inventorySortUtils.client.gui.SortInventoryButton;
import me.imbanana.nexusutils.util.inventorySortUtils.client.gui.TransferAllButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;

@Environment(EnvType.CLIENT)
public class InventoryButtonsManager {
    public static final InventoryButtonsManager INSTANCE = new InventoryButtonsManager();

    private static final MinecraftClient MINECRAFT = MinecraftClient.getInstance();
    private static final int BUTTON_SPACING = 1;
    private static final int BUTTON_SHIFT_X = 0;
    private static final int BUTTON_SHIFT_Y = 1;

    private final LinkedHashSet<InventoryManagementButton> playerButtons = new LinkedHashSet<>();
    private final LinkedHashSet<InventoryManagementButton> containerButtons = new LinkedHashSet<>();
    private final HashSet<Class<? extends Inventory>> sortableInventories = new HashSet<>();
    private final HashSet<Class<? extends Inventory>> transerableInventories = new HashSet<>();
    private final HashSet<Class<? extends ScreenHandler>> sortableScreenHandlers = new HashSet<>();
    private final HashSet<Class<? extends ScreenHandler>> transferableScreenHandlers =
            new HashSet<>();

    private InventoryButtonsManager() {
        registerSortableContainer(PlayerInventory.class);
        registerSortableContainer(EnderChestInventory.class);
        registerSortableContainer(LootableContainerBlockEntity.class);

        registerTransferableContainer(PlayerInventory.class);
        registerTransferableContainer(EnderChestInventory.class);
        registerTransferableContainer(LootableContainerBlockEntity.class);

        registerSimpleInventorySortableHandler(GenericContainerScreenHandler.class);
        registerSimpleInventorySortableHandler(ShulkerBoxScreenHandler.class);
        registerSimpleInventorySortableHandler(HorseScreenHandler.class);
        registerSimpleInventorySortableHandler(HopperScreenHandler.class);

        registerSimpleInventoryTransferableHandler(GenericContainerScreenHandler.class);
        registerSimpleInventoryTransferableHandler(ShulkerBoxScreenHandler.class);
        registerSimpleInventoryTransferableHandler(HorseScreenHandler.class);
    }

    public void registerSortableContainer(Class<? extends Inventory> clazz) {
        sortableInventories.add(clazz);
    }

    public void registerTransferableContainer(Class<? extends Inventory> clazz) {
        transerableInventories.add(clazz);
    }

    public void registerSimpleInventorySortableHandler(Class<? extends ScreenHandler> clazz) {
        sortableScreenHandlers.add(clazz);
    }

    public void registerSimpleInventoryTransferableHandler(Class<? extends ScreenHandler> clazz) {
        transferableScreenHandlers.add(clazz);
    }

    public void init() {
        ScreenEvents.AFTER_INIT.register(this::onScreenAfterInit);
    }

    private void onScreenAfterInit(
            MinecraftClient client, Screen screen, float scaledWidth, float scaledHeight) {
        if (!(screen instanceof HandledScreen)) {
            return;
        }

        playerButtons.clear();
        containerButtons.clear();

        HandledScreen<?> handledScreen = ((HandledScreen<?>) screen);

        // Container side
        generateSortButton(handledScreen, false);
        generateAutoStackButton(handledScreen, false);
        generateTransferAllButton(handledScreen, false);

        // Player side
        generateSortButton(handledScreen, true);
        generateAutoStackButton(handledScreen, true);
        generateTransferAllButton(handledScreen, true);
    }

    private void generateSortButton(HandledScreen<?> screen, boolean isPlayerInventory) {
        if (screen instanceof InventoryScreen && !isPlayerInventory) {
            return;
        }

        Slot referenceSlot = getReferenceSlot(screen, isPlayerInventory);
        if (referenceSlot == null) {
            return;
        }

        ClientPlayerEntity player = MINECRAFT.player;
        if (player == null) {
            return;
        }

        Inventory inventory =
                isPlayerInventory ? player.getInventory() : InventoryUtils.getContainerInventory(player);
        if (inventory == null) {
            return;
        }

        if (inventory instanceof SimpleInventory) {
            if (sortableScreenHandlers.stream()
                    .noneMatch(clazz -> clazz.isInstance(screen.getScreenHandler()))) {
                return;
            }
        } else {
            if (sortableInventories.stream().noneMatch(clazz -> clazz.isInstance(inventory))) {
                return;
            }
        }

        if (getNumberOfBulkInventorySlots(screen, isPlayerInventory) < 3) {
            return;
        }

        Position2 position = getButtonPosition(screen, isPlayerInventory);
        SortInventoryButton button =
                new SortInventoryButton(screen, inventory, referenceSlot, position, isPlayerInventory);
        addButton(screen, button, isPlayerInventory);
    }

    private void generateAutoStackButton(HandledScreen<?> screen, boolean isPlayerInventory) {
        if (screen instanceof InventoryScreen && !isPlayerInventory) {
            return;
        }

        Slot referenceSlot = getReferenceSlot(screen, isPlayerInventory);
        if (referenceSlot == null) {
            return;
        }

        ClientPlayerEntity player = MINECRAFT.player;
        if (player == null) {
            return;
        }

        Inventory fromInventory =
                isPlayerInventory ? InventoryUtils.getContainerInventory(player) : player.getInventory();
        Inventory toInventory =
                isPlayerInventory ? player.getInventory() : InventoryUtils.getContainerInventory(player);
        if (fromInventory == null || toInventory == null || fromInventory == toInventory) {
            return;
        }

        if (fromInventory instanceof SimpleInventory) {
            if (transferableScreenHandlers.stream()
                    .noneMatch(clazz -> clazz.isInstance(screen.getScreenHandler()))) {
                return;
            }
        } else {
            if (transerableInventories.stream().noneMatch(clazz -> clazz.isInstance(fromInventory))) {
                return;
            }
        }

        if (toInventory instanceof SimpleInventory) {
            if (transferableScreenHandlers.stream()
                    .noneMatch(clazz -> clazz.isInstance(screen.getScreenHandler()))) {
                return;
            }
        } else {
            if (transerableInventories.stream().noneMatch(clazz -> clazz.isInstance(toInventory))) {
                return;
            }
        }

        if (getNumberOfNonPlayerBulkInventorySlots(screen) < 3) {
            return;
        }

        Position2 position = getButtonPosition(screen, isPlayerInventory);
        AutoStackButton button =
                new AutoStackButton(screen, fromInventory, referenceSlot, position, isPlayerInventory);
        addButton(screen, button, isPlayerInventory);
    }

    private void generateTransferAllButton(HandledScreen<?> screen, boolean isPlayerInventory) {
        if (screen instanceof InventoryScreen && !isPlayerInventory) {
            return;
        }

        Slot referenceSlot = getReferenceSlot(screen, isPlayerInventory);
        if (referenceSlot == null) {
            return;
        }

        ClientPlayerEntity player = MINECRAFT.player;
        if (player == null) {
            return;
        }

        Inventory fromInventory =
                isPlayerInventory ? InventoryUtils.getContainerInventory(player) : player.getInventory();
        Inventory toInventory =
                isPlayerInventory ? player.getInventory() : InventoryUtils.getContainerInventory(player);
        if (fromInventory == null || toInventory == null || fromInventory == toInventory) {
            return;
        }

        if (fromInventory instanceof SimpleInventory) {
            if (transferableScreenHandlers.stream()
                    .noneMatch(clazz -> clazz.isInstance(screen.getScreenHandler()))) {
                return;
            }
        } else {
            if (transerableInventories.stream().noneMatch(clazz -> clazz.isInstance(fromInventory))) {
                return;
            }
        }

        if (toInventory instanceof SimpleInventory) {
            if (transferableScreenHandlers.stream()
                    .noneMatch(clazz -> clazz.isInstance(screen.getScreenHandler()))) {
                return;
            }
        } else {
            if (transerableInventories.stream().noneMatch(clazz -> clazz.isInstance(toInventory))) {
                return;
            }
        }

        if (getNumberOfNonPlayerBulkInventorySlots(screen) < 3) {
            return;
        }

        Position2 position = getButtonPosition(screen, isPlayerInventory);
        TransferAllButton button =
                new TransferAllButton(screen, fromInventory, referenceSlot, position, isPlayerInventory);
        addButton(screen, button, isPlayerInventory);
    }

    private void addButton(
            HandledScreen<?> screen, InventoryManagementButton button, boolean isPlayerInventory) {
        Screens.getButtons(screen).add(button);
        (isPlayerInventory ? playerButtons : containerButtons).add(button);
    }

    private Slot getReferenceSlot(HandledScreen<?> screen, boolean isPlayerInventory) {
        return screen.getScreenHandler().slots.stream()
                .filter(slot -> isPlayerInventory == (slot.inventory instanceof PlayerInventory))
                .max(Comparator.comparingInt(slot -> slot.x - slot.y))
                .orElse(null);
    }

    private int getNumberOfBulkInventorySlots(HandledScreen<?> screen, boolean isPlayerInventory) {
        return screen.getScreenHandler().slots.stream()
                .filter(slot -> isPlayerInventory == (slot.inventory instanceof PlayerInventory))
                .filter(slot -> !(screen.getScreenHandler() instanceof HorseScreenHandler) ||
                        slot.getIndex() >= 2)
                .mapToInt(slot -> 1)
                .sum();
    }

    private int getNumberOfNonPlayerBulkInventorySlots(HandledScreen<?> screen) {
        return screen.getScreenHandler().slots.stream()
                .filter(slot -> !(slot.inventory instanceof PlayerInventory))
                .filter(slot -> !(screen.getScreenHandler() instanceof HorseScreenHandler) ||
                        slot.getIndex() >= 2)
                .mapToInt(slot -> 1)
                .sum();
    }

    private Position2 getButtonPosition(Screen screen, boolean isPlayerInventory) {
        return getButtonPosition((isPlayerInventory ? playerButtons : containerButtons).size(), new Position2(0, 0));
    }

    public Position2 getButtonPosition(int index, Position2 offset) {
        int x =
                offset.getX() + BUTTON_SHIFT_X * (InventoryManagementButton.WIDTH + BUTTON_SPACING) * index;
        int y =
                offset.getY() + BUTTON_SHIFT_Y * (InventoryManagementButton.HEIGHT + BUTTON_SPACING) * index;

        return new Position2(x, y);
    }

    public LinkedList<InventoryManagementButton> getPlayerButtons() {
        return new LinkedList<>(playerButtons);
    }

    public LinkedList<InventoryManagementButton> getContainerButtons() {
        return new LinkedList<>(containerButtons);
    }
}