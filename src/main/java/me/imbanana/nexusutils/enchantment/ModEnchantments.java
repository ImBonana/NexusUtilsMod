package me.imbanana.nexusutils.enchantment;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.enchantment.custom.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static Enchantment ICE_ASPECT = register("ice_aspect",
            new IceAspectEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static Enchantment POISON = register("poison",
            new PoisonEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static Enchantment BLIND = register("blind",
            new BlindEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static Enchantment ARROW_DEFLECT = register("arrow_deflect",
            new ArrowDeflectEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_LEGS, EquipmentSlot.LEGS));

    public static Enchantment VOODOO = register("voodoo",
            new VoodooEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_HEAD, EquipmentSlot.HEAD));


    public static Enchantment NETHER_SLAYER = register("nether_slayer",
            new NetherSlayerEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_CHEST, EquipmentSlot.CHEST));

    public static Enchantment ENDER_SLAYER = register("ender_slayer",
            new EnderSlayerEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_CHEST, EquipmentSlot.CHEST));

    public static Enchantment HEADLIGHT = register("headlight",
            new HeadlightEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_HEAD, EquipmentSlot.HEAD));

    public static Enchantment EXTRA_HEALTH = register("extra_health",
            new ExtraHealthEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));

    public static Enchantment DOUBLE_STRIKE = register("double_strike",
            new DoubleStrikeEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static Enchantment POSEIDON = register("poseidon",
            new PoseidonEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.TRIDENT, EquipmentSlot.MAINHAND));

    public static Enchantment TWINGE = register("twinge",
            new TwingeEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.TRIDENT, EquipmentSlot.MAINHAND));

    public static Enchantment ATTRACTION = register("attraction",
            new AttractionEnchantment(Enchantment.Rarity.UNCOMMON, new EnchantmentTarget[]{EnchantmentTarget.BOW, EnchantmentTarget.CROSSBOW}, EquipmentSlot.MAINHAND));

    public static Enchantment CHAOS = register("chaos",
            new ChaosEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.TRIDENT, EquipmentSlot.MAINHAND));

    public static Enchantment NIGHT_OWL = register("night_owl",
            new NightOwlEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static Enchantment FAMINE = register("famine",
            new FamineEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static Enchantment LAUNCH = register("launch",
            new LaunchEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));


    public static Enchantment DEVOUR = register("devour",
            new DevourEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));


    public static Enchantment LIFESTEAL = register("lifesteal",
            new LifestealEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    public static Enchantment SHOCKWAVE = register("shockwave",
            new ShockwaveEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_CHEST, EquipmentSlot.CHEST));

    public static Enchantment ROCKET_ESCAPE = register("rocket_escape",
            new RocketEscapeEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_LEGS, EquipmentSlot.LEGS));

    public static Enchantment PLUMMET = register("plummet",
            new PlummetEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET));

    public static Enchantment PERISH = register("perish",
            new PerishEnchantment(Enchantment.Rarity.UNCOMMON, new EnchantmentTarget[]{EnchantmentTarget.TRIDENT, EnchantmentTarget.BOW, EnchantmentTarget.CROSSBOW}, EquipmentSlot.MAINHAND));

    public static Enchantment LAVA_WALKER = register("lava_walker",
            new LavaWalkerEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET));

    public static Enchantment IMPACT = register("impact",
            new ImpactEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.TRIDENT, EquipmentSlot.MAINHAND));

    public static Enchantment DISAPPEAR = register("disappear",
            new DisappearEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_LEGS, EquipmentSlot.LEGS));


    public static Enchantment DIMINISH = register("diminish",
            new DiminishEnchantment(Enchantment.Rarity.UNCOMMON, null, EquipmentSlot.MAINHAND));


    public static Enchantment AEGIS = register("aegis",
            new AegisEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET));

    public static Enchantment JELLY_LEGS = register("jelly_legs",
            new JellyLegsEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_FEET, EquipmentSlot.FEET));

    public static Enchantment PHOENIX = register("phoenix",
            new PhoenixEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR_HEAD, EquipmentSlot.HEAD));

    public static Enchantment LIGHTNING = register("lightning",
            new LightningEnchantment(Enchantment.Rarity.UNCOMMON, new EnchantmentTarget[]{EnchantmentTarget.BOW, EnchantmentTarget.CROSSBOW}, EquipmentSlot.MAINHAND));

    public static Enchantment TELEPATHY = register("telepathy",
            new TelepathyEnchantment(Enchantment.Rarity.UNCOMMON, new EnchantmentTarget[]{EnchantmentTarget.DIGGER, EnchantmentTarget.WEAPON, EnchantmentTarget.CROSSBOW, EnchantmentTarget.BOW, EnchantmentTarget.TRIDENT}, EquipmentSlot.HEAD));

    public static Enchantment BLAST = register("blast",
            new BlastEnchantment(Enchantment.Rarity.UNCOMMON, null, EquipmentSlot.MAINHAND));

    public static Enchantment TIMBER = register("timber",
            new TimberEnchantment(Enchantment.Rarity.UNCOMMON, null, EquipmentSlot.MAINHAND));

    public static Enchantment ORE_EXCAVATION = register("ore_excavation",
            new OreExcavationEnchantment(Enchantment.Rarity.UNCOMMON, null, EquipmentSlot.MAINHAND));

    public static Enchantment AUTO_SMELT = register("auto_smelt",
            new AutoSmeltEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND));

    public static Enchantment REPLANTER = register("replanter",
            new ReplanterEnchantment(Enchantment.Rarity.UNCOMMON, null, EquipmentSlot.MAINHAND));

    public static Enchantment EXPERIENCE = register("experience",
            new ExperienceEnchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));


    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(NexusUtils.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments() {
        NexusUtils.LOGGER.info("Registering Enchantments for " + NexusUtils.MOD_ID);
    }
}
