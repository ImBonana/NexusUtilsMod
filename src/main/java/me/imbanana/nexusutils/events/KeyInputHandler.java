package me.imbanana.nexusutils.events;

import me.imbanana.nexusutils.networking.ModPackets;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_MAIN = "key.category.nexusutils.main";
    public static final String KEY_SIT = "key.nexusutils.sit";
    public static final String KEY_OPEN_BACKPACK = "key.nexusutils.open_backpack";
    public static final String KEY_BACKPACK_SLEEPING_BAG = "key.nexusutils.backpack_sleeping_bag";

    public static KeyBinding sitKey;
    public static KeyBinding openBackpackKey;
    public static KeyBinding backpackSleepingBagKey;

    public static void registerKeyInput() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(sitKey.wasPressed()) {
                ClientPlayNetworking.send(ModPackets.SIT_ID, PacketByteBufs.create());
            }

            if(openBackpackKey.wasPressed()) {
                ClientPlayNetworking.send(ModPackets.OPEN_BACKPACK, PacketByteBufs.create());
            }

            if(backpackSleepingBagKey.wasPressed()) {
                ClientPlayNetworking.send(ModPackets.PLACE_SLEEPING_BAG_BACKPACK, PacketByteBufs.create());
            }
        });
    }

    public static void register() {
        sitKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_SIT, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CATEGORY_MAIN));
        openBackpackKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_OPEN_BACKPACK, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY_MAIN));
        backpackSleepingBagKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_BACKPACK_SLEEPING_BAG, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y, KEY_CATEGORY_MAIN));

        registerKeyInput();
    }
}
