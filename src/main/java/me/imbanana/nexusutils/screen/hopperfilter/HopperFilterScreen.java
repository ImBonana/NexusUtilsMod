package me.imbanana.nexusutils.screen.hopperfilter;

import com.mojang.blaze3d.systems.RenderSystem;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.NexusUtilsClient;
import me.imbanana.nexusutils.screen.copperhopper.CopperHopperScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HopperFilterScreen extends HandledScreen<HopperFilterScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(NexusUtils.MOD_ID, "textures/gui/container/hopper_filter.png");

    public HopperFilterScreen(HopperFilterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 148;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
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

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
