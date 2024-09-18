package me.imbanana.nexusutils.enchantment.lootContextTypes;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModLootContextTypes {
    public static final LootContextType ENCHANTED_ENTITY_ITEM = register("enchanted_entity_item", builder -> builder
            .require(LootContextParameters.THIS_ENTITY)
            .require(LootContextParameters.TOOL)
            .require(LootContextParameters.ENCHANTMENT_LEVEL)
            .require(LootContextParameters.ORIGIN));

    public static LootContextType register(String name, Consumer<LootContextType.Builder> type) {
        LootContextType.Builder builder = new LootContextType.Builder();
        type.accept(builder);
        LootContextType lootContextType = builder.build();
        Identifier identifier = NexusUtils.idOf(name);
        LootContextType lootContextType2 = LootContextTypes.MAP.put(identifier, lootContextType);
        if (lootContextType2 != null) {
            throw new IllegalStateException("Loot table parameter set " + identifier + " is already registered");
        } else {
            return lootContextType;
        }
    }
}
