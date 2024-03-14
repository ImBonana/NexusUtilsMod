package me.imbanana.nexusutils.mixin;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.item.backpack.BackpackItem;
import me.imbanana.nexusutils.screen.backpack.BackpackPlayerInventorySlot;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
    @Unique
    private static final Identifier SLOT_TEXTURE = new Identifier(NexusUtils.MOD_ID, "textures/gui/slot.png");


    public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @ModifyArgs(method = "setSelectedTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen$CreativeSlot;<init>(Lnet/minecraft/screen/slot/Slot;III)V"))
    private void modifyArgsSetSelectedTab(Args args) {
        if (!(args.get(0) instanceof BackpackPlayerInventorySlot)) return;
        args.set(2, BackpackItem.CREATIVE_X_POS);
        args.set(3, BackpackItem.CREATIVE_Y_POS);
    }

    @Inject(method = "drawBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;drawEntity(Lnet/minecraft/client/gui/DrawContext;IIIIIFFFLnet/minecraft/entity/LivingEntity;)V", shift = At.Shift.BEFORE))
    private void injectDrawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        context.drawTexture(SLOT_TEXTURE, this.x + BackpackItem.CREATIVE_X_POS - 1, this.y + BackpackItem.CREATIVE_Y_POS - 1, 0, 0, 18, 18, 18, 18);
    }
}
