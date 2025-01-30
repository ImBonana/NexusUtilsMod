package me.imbanana.nexusutils.enchantment.lootContextTypes;

import me.imbanana.nexusutils.NexusUtils;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.context.ContextType;

import java.util.function.Consumer;

public class ModLootContextTypes {
    public static final ContextType ENCHANTED_ENTITY_ITEM = register("enchanted_entity_item", builder -> builder
            .require(LootContextParameters.THIS_ENTITY)
            .require(LootContextParameters.TOOL)
            .require(LootContextParameters.ENCHANTMENT_LEVEL)
            .require(LootContextParameters.ORIGIN));

    public static ContextType register(String name, Consumer<ContextType.Builder> type) {
        ContextType.Builder builder = new ContextType.Builder();
        type.accept(builder);
        ContextType lootContextType = builder.build();
        Identifier identifier = NexusUtils.idOf(name);
        ContextType lootContextType2 = LootContextTypes.MAP.put(identifier, lootContextType);
        if (lootContextType2 != null) {
            throw new IllegalStateException("Loot table parameter set " + identifier + " is already registered");
        } else {
            return lootContextType;
        }
    }
}
