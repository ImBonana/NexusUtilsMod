package me.imbanana.nexusutils.screen.copperhopper;

import com.mojang.blaze3d.systems.RenderSystem;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.NexusUtilsClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CopperHopperScreen extends HandledScreen<CopperHopperScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(NexusUtils.MOD_ID, "textures/gui/container/copper_hopper.png");

    public CopperHopperScreen(CopperHopperScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 133;
        this.backgroundWidth = 176;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        this.addDrawableChild(new ToggleWhitelistButtonWidget(x+142, y+17));
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        if(handler.getSlot(0).getStack().isEmpty()) renderSortIcon(context);

    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int darkTextColor = 0xAAAAAA;
        int lightTextColor = 0x404040;

        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, isDarkUiEnabled() ? darkTextColor : lightTextColor, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, isDarkUiEnabled() ? darkTextColor : lightTextColor, false);
    }

    private boolean isDarkUiEnabled() {
        for (ResourcePackProfile profile : MinecraftClient.getInstance().getResourcePackManager().getEnabledProfiles()) {
            if(profile.getName().equalsIgnoreCase(NexusUtilsClient.DARK_UI_RESOURCE_PACK_ID.toString())) return true;
        }

        return false;
    }

    private void renderSortIcon(DrawContext context) {
        context.drawTexture(TEXTURE, x + 16, y + 20, backgroundWidth, 0, 16, 16);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }


    @Environment(value= EnvType.CLIENT)
    class ToggleWhitelistButtonWidget extends PressableWidget {
        boolean firstRender = true;

        public ToggleWhitelistButtonWidget(int x, int y) {
            super(x, y, 22, 22, ScreenTexts.EMPTY);
        }

        @Override
        public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            if(firstRender) {
                firstRender = false;
                updateTooltip();
            }
            if(this.isHovered()) context.drawTexture(TEXTURE, this.getX(), this.getY(), CopperHopperScreen.this.backgroundWidth, 38, this.width, this.height);
            else context.drawTexture(TEXTURE, this.getX(), this.getY(), CopperHopperScreen.this.backgroundWidth, 16, this.width, this.height);

            this.renderExtra(context);
        }

        public void renderExtra(DrawContext context) {
            if(CopperHopperScreen.this.handler.isWhitelist()) context.drawTexture(TEXTURE, this.getX()+3, this.getY()+3, CopperHopperScreen.this.backgroundWidth + 16, 0, 16, 16);
            else context.drawTexture(TEXTURE, this.getX()+3, this.getY()+3, CopperHopperScreen.this.backgroundWidth + 32, 0, 16, 16);
        }

        @Override
        public void onPress() {
            CopperHopperScreen.this.handler.toggleWhitelist();
            updateTooltip();
        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {
            this.appendDefaultNarrations(builder);
        }

        private void updateTooltip() {
            this.setTooltip(Tooltip.of(Text.literal("Change to " + (CopperHopperScreen.this.handler.isWhitelist() ? "Blacklist" : "Whitelist"))));
        }
    }
}
