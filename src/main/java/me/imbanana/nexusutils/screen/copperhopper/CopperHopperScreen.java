package me.imbanana.nexusutils.screen.copperhopper;

import com.mojang.blaze3d.systems.RenderSystem;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.screen.itemdisplay.ItemDisplayScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.BeaconScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.UpdateBeaconC2SPacket;
import net.minecraft.screen.BeaconScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

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

        public ToggleWhitelistButtonWidget(int x, int y) {
            super(x, y, 22, 22, ScreenTexts.EMPTY);
            this.setTooltip(Tooltip.of(Text.literal("Change to " + (CopperHopperScreen.this.handler.isWhitelist() ? "Blacklist" : "Whitelist"))));
        }

        @Override
        public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
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
            this.setTooltip(Tooltip.of(Text.literal("Change to " + (CopperHopperScreen.this.handler.isWhitelist() ? "Blacklist" : "Whitelist"))));
        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {
            this.appendDefaultNarrations(builder);
        }
    }
}
