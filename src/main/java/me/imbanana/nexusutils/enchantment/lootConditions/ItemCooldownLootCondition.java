package me.imbanana.nexusutils.enchantment.lootConditions;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;

import java.util.Set;

public record ItemCooldownLootCondition(boolean onCooldown, LootContext.EntityTarget entity) implements LootCondition {
    public static final MapCodec<ItemCooldownLootCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Codec.BOOL.fieldOf("onCooldown").forGetter(ItemCooldownLootCondition::onCooldown),
                    LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(ItemCooldownLootCondition::entity)
            ).apply(instance, ItemCooldownLootCondition::new)
    );

    @Override
    public LootConditionType getType() {
        return ModLootConditionTypes.ITEM_COOLDOWN;
    }

    @Override
    public Set<LootContextParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootContextParameters.TOOL);
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(this.entity.getParameter());
        if(!(entity instanceof PlayerEntity player)) return false;
        ItemStack itemStack = lootContext.get(LootContextParameters.TOOL);
        if(itemStack == null) return false;
        return player.getItemCooldownManager().isCoolingDown(itemStack.getItem()) == this.onCooldown;
    }

    public static LootCondition.Builder builder(LootContext.EntityTarget entity, boolean onCooldown) {
        return () -> new ItemCooldownLootCondition(onCooldown, entity);
    }
}
