package me.imbanana.nexusutils.item;

import me.imbanana.nexusutils.NexusUtils;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItemGroups {
    public static final ItemGroup NEXUS_GROUP = Registry.register(Registries.ITEM_GROUP,
            NexusUtils.idOf("nexus"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.nexus"))
                    .icon(() -> new ItemStack(ModItems.SNAIL_ITEM)).entries(ModItems::addItemsToIngredientTabItemGroup).build());

    public static void registerItemGroups() {
        NexusUtils.LOGGER.info("Registering Item Groups for " + NexusUtils.MOD_ID);
    }
}
