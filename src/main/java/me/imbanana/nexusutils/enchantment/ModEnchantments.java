package me.imbanana.nexusutils.enchantment;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.enchantment.custom.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ModEnchantments {
    private static final List<TradableEnchantment> tradableEnchantments = new ArrayList<>();

    public static final Enchantment ICE_ASPECT = register("ice_aspect",
            new IceAspectEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static final Enchantment POISON = register("poison",
            new PoisonEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static final Enchantment BLIND = register("blind",
            new BlindEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static final Enchantment ARROW_DEFLECT = register("arrow_deflect",
            new ArrowDeflectEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_LEGS, EquipmentSlot.LEGS));

    public static final Enchantment VOODOO = register("voodoo",
            new VoodooEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_HEAD, EquipmentSlot.HEAD));


    public static final Enchantment NETHER_SLAYER = register("nether_slayer",
            new NetherSlayerEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_CHEST, EquipmentSlot.CHEST));

    public static final Enchantment ENDER_SLAYER = register("ender_slayer",
            new EnderSlayerEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_CHEST, EquipmentSlot.CHEST));

    public static final Enchantment HEADLIGHT = register("headlight",
            new HeadlightEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_HEAD, EquipmentSlot.HEAD));

    public static final Enchantment EXTRA_HEALTH = register("extra_health",
            new ExtraHealthEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));

    public static final Enchantment DOUBLE_STRIKE = register("double_strike",
            new DoubleStrikeEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static final Enchantment POSEIDON = register("poseidon",
            new PoseidonEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.TRIDENT, EquipmentSlot.MAINHAND));

    public static final Enchantment TWINGE = register("twinge",
            new TwingeEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.TRIDENT, EquipmentSlot.MAINHAND));

    public static final Enchantment ATTRACTION = register("attraction",
            new AttractionEnchantment(Enchantment.Rarity.UNCOMMON, new EnchantmentTarget[]{EnchantmentTarget.BOW, EnchantmentTarget.CROSSBOW}, EquipmentSlot.MAINHAND));

    public static final Enchantment CHAOS = register("chaos",
            new ChaosEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.TRIDENT, EquipmentSlot.MAINHAND));

    public static final Enchantment NIGHT_OWL = register("night_owl",
            new NightOwlEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static final Enchantment FAMINE = register("famine",
            new FamineEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static final Enchantment LAUNCH = register("launch",
            new LaunchEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));


    public static final Enchantment DEVOUR = register("devour",
            new DevourEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));


    public static final Enchantment LIFESTEAL = register("lifesteal",
            new LifestealEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static final Enchantment SHOCKWAVE = register("shockwave",
            new ShockwaveEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_CHEST, EquipmentSlot.CHEST));

    public static final Enchantment ROCKET_ESCAPE = register("rocket_escape",
            new RocketEscapeEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_LEGS, EquipmentSlot.LEGS));

    public static final Enchantment PLUMMET = register("plummet",
            new PlummetEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET));

    public static final Enchantment PERISH = register("perish",
            new PerishEnchantment(Enchantment.Rarity.UNCOMMON, new EnchantmentTarget[]{EnchantmentTarget.TRIDENT, EnchantmentTarget.BOW, EnchantmentTarget.CROSSBOW}, EquipmentSlot.MAINHAND));

    public static final Enchantment LAVA_WALKER = register("lava_walker",
            new LavaWalkerEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET));

    public static final Enchantment IMPACT = register("impact",
            new ImpactEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.TRIDENT, EquipmentSlot.MAINHAND));

    public static final Enchantment DISAPPEAR = register("disappear",
            new DisappearEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_LEGS, EquipmentSlot.LEGS));

    public static final Enchantment DIMINISH = register("diminish",
            new DiminishEnchantment(Enchantment.Rarity.UNCOMMON, null, EquipmentSlot.MAINHAND));

    public static final Enchantment AEGIS = register("aegis",
            new AegisEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET));

    public static final Enchantment JELLY_LEGS = register("jelly_legs",
            new JellyLegsEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET));

    public static final Enchantment PHOENIX = register("phoenix",
            new PhoenixEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_HEAD, EquipmentSlot.HEAD));

    public static final Enchantment LIGHTNING = register("lightning",
            new LightningEnchantment(Enchantment.Rarity.UNCOMMON, new EnchantmentTarget[]{EnchantmentTarget.BOW, EnchantmentTarget.CROSSBOW}, EquipmentSlot.MAINHAND));

    public static final Enchantment TELEPATHY = register("telepathy",
            new TelepathyEnchantment(Enchantment.Rarity.UNCOMMON, new EnchantmentTarget[]{EnchantmentTarget.DIGGER, EnchantmentTarget.WEAPON, EnchantmentTarget.CROSSBOW, EnchantmentTarget.BOW, EnchantmentTarget.TRIDENT}, EquipmentSlot.HEAD));

    public static final Enchantment BLAST = register("blast",
            new BlastEnchantment(Enchantment.Rarity.UNCOMMON, null, EquipmentSlot.MAINHAND));

    public static final Enchantment TIMBER = register("timber",
            new TimberEnchantment(Enchantment.Rarity.UNCOMMON, null, EquipmentSlot.MAINHAND));

    public static final Enchantment ORE_EXCAVATION = register("ore_excavation",
            new OreExcavationEnchantment(Enchantment.Rarity.UNCOMMON, null, EquipmentSlot.MAINHAND));

    public static final Enchantment AUTO_SMELT = register("auto_smelt",
            new AutoSmeltEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND));

    public static final Enchantment REPLANTER = register("replanter",
            new ReplanterEnchantment(Enchantment.Rarity.UNCOMMON, null, EquipmentSlot.MAINHAND));

    public static final Enchantment EXPERIENCE = register("experience",
            new ExperienceEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));


    public static List<TradableEnchantment> getTradableEnchantments() {
        return tradableEnchantments;
    }

    private static Enchantment register(String name, Enchantment enchantment) {
        Enchantment enchant = Registry.register(Registries.ENCHANTMENT, new Identifier(NexusUtils.MOD_ID, name), enchantment);
        if(enchant instanceof TradableEnchantment tradableEnchantment) tradableEnchantments.add(tradableEnchantment);
        return enchant;
    }

    public static void registerModEnchantments() {
        NexusUtils.LOGGER.info("Registering Enchantments for " + NexusUtils.MOD_ID);
    }
}
