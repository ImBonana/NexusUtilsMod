package me.imbanana.nexusutils.enchantment;

public interface TradableEnchantment {
    int getMaxPrice();

    int getMinPrice();

    default int getMaxLevelToGet()  {
        return 1;
    }

    default int getMinLevelToGet() {
        return 1;
    }

    default boolean canGet() {
        return true;
    }
}
