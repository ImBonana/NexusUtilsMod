package me.imbanana.nexusutils.villager;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.enchantment.TradableEnchantment;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;

import java.util.List;
import java.util.Random;

public class ModCustomTrades {
    public static void registerTrades() {
        NexusUtils.LOGGER.info("Registering Trades for " + NexusUtils.MOD_ID);

        for (int i = 1; i <= 5; i++) {
            int finalI = i * i * 5;
            TradeOfferHelper.registerVillagerOffers(ModVillagers.ENCHANTER, i, factories -> factories.add((entity, random) -> getBookTradeOffer(3, finalI, 0.05f)));
        }
    }


    private static TradableEnchantment getTradableEnchantmentToGet() {
        List<TradableEnchantment> tradableEnchantments = ModEnchantments.getTradableEnchantments();
        if(tradableEnchantments.isEmpty()) return null;
        TradableEnchantment trEnchantment = tradableEnchantments.get(new Random().nextInt(0, tradableEnchantments.size()));
        if(trEnchantment == null) return null;

        if(trEnchantment.canGet()) return trEnchantment;

        return getTradableEnchantmentToGet();
    }

    private static TradeOffer getBookTradeOffer(int maxUses, int xp, float priceMultiplier) {
        TradableEnchantment trEnchantment = getTradableEnchantmentToGet();
        if(trEnchantment == null) return null;

        ItemStack book = EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(
                (Enchantment) trEnchantment,
                new Random().nextInt(trEnchantment.getMinLevelToGet(), trEnchantment.getMaxLevelToGet() + 1))
        );

        return new TradeOffer(
                new ItemStack(Items.EMERALD, new Random().nextInt(trEnchantment.getMinPrice(), trEnchantment.getMaxPrice()+1)),
                new ItemStack(Items.BOOK, 1),
                book,
                maxUses,
                xp,
                priceMultiplier
        );
    }
}
