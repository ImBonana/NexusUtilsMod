package me.imbanana.nexusutils.mixin.enchantments;

import com.google.common.collect.Lists;
import me.imbanana.nexusutils.enchantment.MultipleTargetsEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
//    @Redirect(
//            method = "getPossibleEntries",
//            at = @At(value = "FIELD", target = "Lnet/minecraft/enchantment/Enchantment;target:Lnet/minecraft/enchantment/EnchantmentTarget;", opcode = Opcodes.GETFIELD)
//    )
//    private static EnchantmentTarget redirectIsAcceptableItem(Enchantment instance, int power, ItemStack stack, boolean treasureAllowed) {
//        if(!(instance instanceof MultipleTargetsEnchantment)) return instance.target;
//        MultipleTargetsEnchantment targets = (MultipleTargetsEnchantment) instance;
//
//        for(EnchantmentTarget t : targets.getEnchantmentTargets()) {
//            if(t.isAcceptableItem(stack.getItem())) return t;
//        }
//
//        return instance.target;
//    }

    /**
     * @author idk
     * @reason idk
     */
    @Overwrite
    public static List<EnchantmentLevelEntry> getPossibleEntries(int power, ItemStack stack, boolean treasureAllowed) {
        ArrayList<EnchantmentLevelEntry> list = Lists.newArrayList();
        boolean bl = stack.isOf(Items.BOOK);
        block0: for (Enchantment enchantment : Registries.ENCHANTMENT) {
            if (enchantment.isTreasure() && !treasureAllowed || !enchantment.isAvailableForRandomSelection() || !enchantment.isAcceptableItem(stack) && !bl) continue;
            for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                if (power < enchantment.getMinPower(i) || power > enchantment.getMaxPower(i)) continue;
                list.add(new EnchantmentLevelEntry(enchantment, i));
                continue block0;
            }
        }
        return list;
    }
}
