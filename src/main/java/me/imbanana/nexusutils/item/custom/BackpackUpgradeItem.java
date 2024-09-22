package me.imbanana.nexusutils.item.custom;

import me.imbanana.nexusutils.components.custom.BackpackTierComponent;
import net.minecraft.item.Item;

public class BackpackUpgradeItem extends Item {
    private final int tier;

    public BackpackUpgradeItem(Settings settings, int tier) {
        super(settings);
        this.tier = tier;
    }

    public BackpackTierComponent.Tier getTier() {
        BackpackTierComponent.Tier t = BackpackTierComponent.Tier.fromNumber(this.tier);
        return t == null ? BackpackTierComponent.Tier.TIER_0 : t;
    }
}
