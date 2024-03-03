package me.imbanana.nexusutils.entity.client;

import me.imbanana.nexusutils.NexusUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Comparator;

@Environment(EnvType.CLIENT)
public class ModTexturedRenderLayers {
    public static final SpriteIdentifier[] SLEEPING_BAG_TEXTURES =
            Arrays.stream(DyeColor.values())
                    .sorted(Comparator.comparingInt(DyeColor::getId))
                    .map(color ->
                            new SpriteIdentifier(
                                    TexturedRenderLayers.BEDS_ATLAS_TEXTURE,
                                    new Identifier(NexusUtils.MOD_ID, "entity/sleeping_bag/" + color.getName())
                            ))
                    .toArray(SpriteIdentifier[]::new);

}
