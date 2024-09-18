package me.imbanana.nexusutils.screen.backpack;

import com.mojang.blaze3d.systems.RenderSystem;
import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.NexusUtilsClient;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.texture.Sprite;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registries;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class BackpackScreen extends HandledScreen<BackpackScreenHandler> {
    private static final Identifier TEXTURE = NexusUtils.idOf("textures/gui/container/backpack.png");

    public BackpackScreen(BackpackScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 166;
        this.backgroundWidth = 256;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
        this.playerInventoryTitleX = 48;

        this.titleX = 48;
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

        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, NexusUtilsClient.isDarkUiEnabled() ? darkTextColor : lightTextColor, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, NexusUtilsClient.isDarkUiEnabled() ? darkTextColor : lightTextColor, false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        if(!this.handler.getLeftTank().isResourceBlank()) {
            drawTank(context, this.handler.getLeftTank(), 25);
        }

        if(!this.handler.getRightTank().isResourceBlank()) {
            drawTank(context, this.handler.getRightTank(), 215);
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);

        // left tank
        if(inTank(25, 66 - 44, 16, 44, x, y)) {
            context.drawTooltip(textRenderer, this.getTankTooltip(this.getScreenHandler().getLeftTank()), x, y);
        }

        // right tank
        if(inTank(215, 66 - 44, 16, 44, x, y)) {
            context.drawTooltip(textRenderer, this.getTankTooltip(this.getScreenHandler().getRightTank()), x, y);
        }
    }

    private boolean inTank(int tankX, int tankY, int tankWidth, int tankHeight, int mouseX, int mouseY)
    {
        return this.x + tankX <= mouseX && mouseX <= tankX + tankWidth + this.x && tankY + this.y <= mouseY && mouseY <= tankY + tankHeight + this.y;
    }

    private List<Text> getTankTooltip(FluidTank fluidTank) {
        FluidVariant fluidVariant = fluidTank.getResource();

        List<Text> tankTips = new ArrayList<>();
        MutableText fluidName = !fluidVariant.isBlank() ? FluidVariantAttributes.getName(fluidVariant).copy() : Text.translatable("screen.nexusutils.backpack.tank.none");
        Text fluidAmount = !fluidVariant.isBlank() ? Text.literal(fluidTank.getAmount() + "/" + fluidTank.getCapacity()) : Text.translatable("screen.nexusutils.backpack.tank.empty");

        if(fluidName != null && !fluidTank.isResourceBlank()) {
            int color = FluidVariantRendering.getColor(fluidVariant);

            if(color < 0 && (fluidVariant.getFluid() == Fluids.LAVA || fluidVariant.getFluid() == Fluids.FLOWING_LAVA))
                color = 14184981;

            tankTips.add(fluidName.fillStyle(Style.EMPTY.withColor(color)));
        }
        tankTips.add(fluidAmount);

        if (!fluidTank.isResourceBlank() && MinecraftClient.getInstance().options.advancedItemTooltips) {
            tankTips.add(Text.literal(Registries.FLUID.getId(fluidVariant.getFluid()).toString()).formatted(Formatting.DARK_GRAY));
        }

        return tankTips;
    }

    private void drawTank(DrawContext context, FluidTank fluidTank, int x) {
        renderFluid(
                context,
                fluidTank.variant,
                this.x + x,
                this.y + 66,
                0,
                (int) Math.floor(((double) fluidTank.amount / fluidTank.getCapacity()) * 44),
                16
        );

    }

    private void renderFluid(DrawContext context, FluidVariant fluidVariant, int x, int y, int z, int height, int width) {
        if(fluidVariant == null || fluidVariant.getFluid() == null) return;

        Sprite texture = FluidVariantRendering.getSprite(fluidVariant);
        if(texture == null) {
            texture = MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(MissingSprite.getMissingSpriteId());
        }

        int color = FluidVariantRendering.getColor(fluidVariant);
        context.getMatrices().push();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, 1);
        RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
        RenderSystem.disableBlend();

        for(int i = 0; i < width; i += 16)
        {
            for(int j = 0; j < height; j += 16)
            {
                int drawWidth = Math.min(width - i, 16);
                int drawHeight = Math.min(height - j, 16);

                int drawX = x + i;
                int drawY = y + j - height;

                float minU = texture.getMinU();
                float minV = texture.getMinV();

                float maxU = texture.getMaxU();
                float maxV = texture.getMaxV();

                BufferBuilder builder =  Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                Matrix4f matrix4f = context.getMatrices().peek().getPositionMatrix();
                builder.vertex(matrix4f, drawX, drawY + drawHeight, (float)z).texture(minU, minV + (maxV - minV) * (float)drawHeight / 16F);
                builder.vertex(matrix4f, drawX + drawWidth, drawY + drawHeight, (float)z).texture(minU + (maxU - minU) * (float)drawWidth / 16F, minV + (maxV - minV) * drawHeight / 16F);
                builder.vertex(matrix4f, drawX + drawWidth, drawY, (float)z).texture(minU + (maxU - minU) * drawWidth / 16F, minV);
                builder.vertex(matrix4f, drawX, drawY, (float)z).texture(minU, minV);
                BufferRenderer.drawWithGlobalProgram(builder.end());
            }
        }

        context.getMatrices().pop();
    }
}
