package me.imbanana.nexusutils.screen.postbox;

import com.mojang.blaze3d.systems.RenderSystem;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.NexusUtilsClient;
import me.imbanana.nexusutils.networking.ModPackets;
import me.imbanana.nexusutils.util.MailBox;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.EditBoxWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.StringUtils;
import org.joml.Math;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class PostBoxScreen extends HandledScreen<PostBoxScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(NexusUtils.MOD_ID, "textures/gui/container/post_box.png");
    private static final List<MailBox> MAIL_BOXES_CACHE = new ArrayList<>();
    private static boolean mailBoxesDirty = false;

    private static final int MAILBOX_ENTRY_HEIGHT = 15;
    private static final int MAILBOX_ENTRY_WIDTH = 109;
    private static final int CONTAINER_HEIGHT = 120;
    private static final int CONTAINER_WIDTH = 109;
    private static final int CONTAINER_X = 8;
    private static final int CONTAINER_Y = 41;
    private static final int SCROLL_BAR_HEIGHT = 27;
    private static final int SCROLBAR_WIDTH = 6;
    private static final int MAX_VISIBLE_ITEMS = CONTAINER_HEIGHT / MAILBOX_ENTRY_HEIGHT + 1;
    private static final int SCROLL_SPEED = 5;
    private TextFieldWidget searchEditBox;
    private EditBoxWidget messageEditBox;
    private SendButtonWidget sendButton;

    private String query = "";
    private String message = "";
    private int scroll = 0;
    private int clickedY = -1;
    private MailBox selected;

    private boolean sendStatus = true;
    private Text sendStatusMessage = Text.empty();
    private int statusRenderTicks = -1;

    private final List<MailBox> mailBoxes = new ArrayList<>();

    public PostBoxScreen(PostBoxScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 318;
        this.backgroundHeight = 169;
        this.playerInventoryTitleX = 136;
        this.playerInventoryTitleY += 2;
        this.titleX = 136;
        this.updateSearchFilter();
    }

    @Override
    protected void init() {
        super.init();

        this.handler.updateMailBoxes();

        this.searchEditBox = new TextFieldWidget(this.textRenderer, this.x + 9, this.y + 23, 116, 11, Text.empty());
        this.searchEditBox.setEditable(true);
        this.searchEditBox.setDrawsBackground(false);
        this.searchEditBox.setPlaceholder(Text.literal("Search..."));
        this.searchEditBox.setChangedListener(s -> {
            this.query = s;
            this.updateSearchFilter();
            this.scroll(0);
        });

        if(!this.query.isBlank()) this.searchEditBox.setText(this.query);

        this.addDrawableChild(this.searchEditBox);
        this.addSelectableChild(this.searchEditBox);

        this.messageEditBox = new EditBoxWidget(this.textRenderer, this.x + 137, this.y + 18, 93, 52, Text.translatable("screen.nexusutils.postbox.textbox.placeholder"), Text.translatable("screen.nexusutils.postbox.textbox.message")) {
            @Override
            protected void drawBox(DrawContext context, int x, int y, int width, int height) { }

            @Override
            protected int getPadding() {
                return 1;
            }
        };
        this.messageEditBox.setChangeListener(s -> this.message = s);
        this.messageEditBox.setMaxLength(1000);
        if (!this.message.isBlank()) this.messageEditBox.setText(this.message);

        this.addDrawableChild(this.messageEditBox);
        this.addSelectableChild(this.messageEditBox);

        this.sendButton = new SendButtonWidget(this.x + 299, this.y + 37);
        addDrawableChild(this.sendButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.sendButton.active = this.selected != null && !this.handler.getInventory().isEmpty();
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int darkTextColor = 0xAAAAAA;
        int lightTextColor = 0x404040;

        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, NexusUtilsClient.isDarkUiEnabled() ? darkTextColor : lightTextColor, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, NexusUtilsClient.isDarkUiEnabled() ? darkTextColor : lightTextColor, false);
        context.drawText(this.textRenderer, Text.translatable("screen.nexusutils.postbox.mailboxes"), 6, this.titleY, NexusUtilsClient.isDarkUiEnabled() ? darkTextColor : lightTextColor, false);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 512, 256);

        context.enableScissor(this.x + CONTAINER_X, this.y + CONTAINER_Y, this.x + CONTAINER_X + CONTAINER_WIDTH, this.y + CONTAINER_Y + CONTAINER_HEIGHT);
        int scroll = this.clampScroll(this.scroll + this.getDeltaScroll(mouseY));
        int startIndex = MathHelper.clamp(scroll / MAILBOX_ENTRY_HEIGHT, 0, Math.max(0, this.mailBoxes.size() - 1 - MAX_VISIBLE_ITEMS));
        int maxItems = Math.min(MAX_VISIBLE_ITEMS, this.mailBoxes.size());

        for(int i = 0; i < maxItems; i++) {
            int entryIndex = startIndex + i;
            int entryX = this.x + CONTAINER_X;
            int entryY = this.y + CONTAINER_Y + entryIndex * MAILBOX_ENTRY_HEIGHT - scroll;
            MailBox mailBox = this.mailBoxes.get(entryIndex);
            boolean selected = this.selected == mailBox;

            context.drawTexture(TEXTURE, entryX, entryY, 0, selected ? 184 : 169, MAILBOX_ENTRY_WIDTH, MAILBOX_ENTRY_HEIGHT, 512, 256);

            if(this.client != null && this.client.getNetworkHandler() != null) {
                PlayerListEntry playerInfo = this.client.getNetworkHandler().getPlayerListEntry(mailBox.owner());
                if(playerInfo != null) PlayerSkinDrawer.draw(context, playerInfo.getSkinTextures(), entryX + 3, entryY + 3, 10);
            }

            context.drawText(this.textRenderer, mailBox.name(), entryX + 17, entryY + 3, selected ? 0xFFFFFF55 : 0xFFFFFFFF, false);

            if(this.client != null && this.client.getNetworkHandler() != null && this.isPointWithinBounds((entryX - this.x) + 3, (entryY - this.y) + 3, 10, 10, mouseX, mouseY)) {
                PlayerListEntry playerInfo = this.client.getNetworkHandler().getPlayerListEntry(mailBox.owner());
                String ownerName = "Unknown Player";
                if(playerInfo != null) ownerName = playerInfo.getProfile().getName();
                context.drawTooltip(this.textRenderer, Text.literal(ownerName), mouseX, mouseY);
            }
        }

        context.disableScissor();

        if(MAX_VISIBLE_ITEMS < this.mailBoxes.size()) {
            context.drawTexture(TEXTURE, this.x + CONTAINER_X + CONTAINER_WIDTH + 1, this.y + CONTAINER_Y + getScrollBarOffset(mouseY), 109, this.canScroll() ? 169 : 169 + SCROLL_BAR_HEIGHT, SCROLBAR_WIDTH, SCROLL_BAR_HEIGHT, 512, 256);
        } else {
            context.drawTexture(TEXTURE, this.x + CONTAINER_X + CONTAINER_WIDTH + 1, this.y + CONTAINER_Y, 109, 169 + SCROLL_BAR_HEIGHT, SCROLBAR_WIDTH, SCROLL_BAR_HEIGHT, 512, 256);
        }

        if(statusRenderTicks > 0) {
            context.drawTexture(TEXTURE, this.x + 288, this.y + 3, this.sendStatus ?  185 : 171, 169, 14, 13, 512, 256);

            if(this.isPointWithinBounds(288, 3, 14, 13, mouseX, mouseY)) {
                context.drawTooltip(this.textRenderer, this.sendStatusMessage, mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(button == GLFW.GLFW_MOUSE_BUTTON_1) {
            this.setFocused(null);

            if(this.isPointWithinBounds(CONTAINER_X, CONTAINER_Y, CONTAINER_WIDTH, CONTAINER_HEIGHT, mouseX, mouseY)) {
                int relativeMouseY = (int) (mouseY - this.y - CONTAINER_Y);
                int clickIndex = (this.scroll + relativeMouseY) / MAILBOX_ENTRY_HEIGHT;
                if(clickIndex >= 0 && clickIndex < this.mailBoxes.size() && this.client != null) {
                    MailBox mailBox = this.mailBoxes.get(clickIndex);
                    this.selected = this.selected != mailBox ? mailBox : null;
                    this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ITEM_BOOK_PUT, 1f, 1f));
                    this.sendButton.active = this.selected != null;
                    return true;
                }
            }

            if(this.isPointWithinBounds(CONTAINER_X + CONTAINER_WIDTH + 1, CONTAINER_Y + this.getScrollBarOffset((int) mouseY), SCROLBAR_WIDTH, SCROLL_BAR_HEIGHT, mouseX, mouseY)) {
                this.clickedY = (int) mouseY;
                return true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(button == GLFW.GLFW_MOUSE_BUTTON_1) {
            if(this.clickedY >= 0) {
                this.scroll(this.getDeltaScroll((int) mouseY));
                this.clickedY = -1;
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(this.searchEditBox.isFocused()) return this.searchEditBox.keyPressed(keyCode, scanCode, modifiers);
        if(this.messageEditBox.isFocused()) return this.messageEditBox.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if(this.isPointWithinBounds(CONTAINER_X, CONTAINER_Y, CONTAINER_WIDTH, CONTAINER_HEIGHT, mouseX, mouseY)) {
            this.scroll((int) (-SCROLL_SPEED * verticalAmount));
        }

        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    private void scroll(int amount) {
        this.scroll = this.clampScroll(this.scroll + amount);
    }

    private int clampScroll(int scroll) {
        return MathHelper.clamp(scroll, 0, this.getMaxScroll());
    }

    private int getMaxScroll() {
        return Math.max((this.mailBoxes.size() - 1) * MAILBOX_ENTRY_HEIGHT - CONTAINER_HEIGHT, 0);
    }

    private int getDeltaScroll(int mouseY) {
        if(this.clickedY == -1) return 0;
        double pixelsPerScroll = (double) (CONTAINER_HEIGHT - SCROLL_BAR_HEIGHT) / this.getMaxScroll();
        return (int) ((mouseY - this.clickedY) / pixelsPerScroll);
    }

    private int getScrollBarOffset(int mouseY) {
        int scroll = this.clampScroll(this.scroll + this.getDeltaScroll(mouseY));
        return (int) ((CONTAINER_HEIGHT - SCROLL_BAR_HEIGHT) * (scroll / (double) this.getMaxScroll()));
    }

    private boolean canScroll() {
        return this.getMaxScroll() > 0;
    }

    @Override
    protected void handledScreenTick() {
        if(mailBoxesDirty) updateSearchFilter();
        if(this.statusRenderTicks > 0) this.statusRenderTicks--;
    }

    private void updateSearchFilter() {
        List<MailBox> filteredMailBoxes = MAIL_BOXES_CACHE.stream().filter(mailBox -> {
            if(this.query.startsWith("@") && this.client != null && this.client.getNetworkHandler() != null) {
                String ownerName = "Unknown";
                PlayerListEntry playerInfo = this.client.getNetworkHandler().getPlayerListEntry(mailBox.owner());
                if(playerInfo != null) ownerName = playerInfo.getProfile().getName();
                return StringUtils.containsIgnoreCase(ownerName, this.query.substring(1));
            }
            String mailboxName = mailBox.name();
            return StringUtils.containsIgnoreCase(mailboxName, this.query);
        }).sorted(Comparator.comparing(MailBox::name)).toList();

        this.mailBoxes.clear();
        this.mailBoxes.addAll(filteredMailBoxes);
    }

    public void setSendStatus(boolean sendStatus, Text message) {
        this.sendStatusMessage = message;
        this.sendStatus = sendStatus;
        this.statusRenderTicks = 100;
    }

    public void clearMessage() {
        this.message = "";
        this.messageEditBox.setText(this.message);
    }

    public static void markMailBoxesDirty() {
        mailBoxesDirty = true;
    }

    public static void setMailBoxesCache(List<MailBox> mailBoxes) {
        MAIL_BOXES_CACHE.clear();
        MAIL_BOXES_CACHE.addAll(mailBoxes);
    }

    private class SendButtonWidget extends PressableWidget {

        public SendButtonWidget(int x, int y) {
            super(x, y, 14, 14, Text.translatable("screen.nexusutils.postbox.send"));
            this.setTooltip(Tooltip.of(Text.translatable("screen.nexusutils.postbox.send")));
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            int textureX = !this.active ? 157 : (this.isFocused() ? 143 : (this.isHovered() ? 129 : 115));

            context.drawTexture(TEXTURE, this.getX(), this.getY(), textureX, 169, this.getWidth(), this.getHeight(), 512, 256);

            context.drawTexture(TEXTURE, this.getX() + 2, this.getY() + 3, 115, 183, 10, 9, 512, 256);
        }

        @Override
        public void onPress() {
            PacketByteBuf packet = PacketByteBufs.create();
            packet.writeString(PostBoxScreen.this.message);
            PostBoxScreen.this.selected.toPacket(packet);

            ClientPlayNetworking.send(ModPackets.SEND_MAIL, packet);
        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {
            this.appendDefaultNarrations(builder);
        }
    }
}
