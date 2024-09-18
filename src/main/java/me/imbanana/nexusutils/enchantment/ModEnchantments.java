package me.imbanana.nexusutils.enchantment;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.enchantment.componentTypes.ModEnchantmentEffectComponentTypes;
import me.imbanana.nexusutils.enchantment.custom.*;
import me.imbanana.nexusutils.enchantment.effects.ModEnchantmentEffects;
import me.imbanana.nexusutils.enchantment.lootConditions.ModLootConditionTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.*;

public class ModEnchantments {
    public static final NexusEnchantment AEGIS = new AegisEnchantment(of("aegis"));

    public static final NexusEnchantment ATTRACTION = new AttractionEnchantment(of("attraction"));

    public static final NexusEnchantment AUTO_SMELT = new AutoSmeltEnchantment(of("auto_smelt"));

    public static final NexusEnchantment BLAST = new BlastEnchantment(of("blast"));

    public static final NexusEnchantment BLIND = new BlindEnchantment(of("blind"));

    public static final NexusEnchantment CHAOS = new ChaosEnchantment(of("chaos"));

    public static final NexusEnchantment DEVOUR = new DevourEnchantment(of("devour"));

    public static final NexusEnchantment DIMINISH = new DiminishEnchantment(of("diminish"));

    public static final NexusEnchantment DISAPPEAR = new DisappearEnchantment(of("disappear"));

    public static final NexusEnchantment DOUBLE_STRIKE = new DoubleStrikeEnchantment(of("double_strike"));

    public static final NexusEnchantment ENDER_SLAYER = new EnderSlayerEnchantment(of("ender_slayer"));

    public static final NexusEnchantment EXPERIENCE = new ExperienceEnchantment(of("experience"));

    public static final NexusEnchantment EXTRA_HEALTH = new ExtraHealthEnchantment(of("extra_health"));

    public static final NexusEnchantment FAMINE = new FamineEnchantment(of("famine"));

    public static final NexusEnchantment HEADLIGHT = new HeadlightEnchantment(of("headlight"));

    public static final NexusEnchantment ICE_ASPECT = new IceAspectEnchantment(of("ice_aspect"));

    public static final NexusEnchantment IMPACT = new ImpactEnchantment(of("impact"));

    public static final NexusEnchantment JUMP = new JumpEnchantment(of("jump"));

    public static final NexusEnchantment LAUNCH = new LaunchEnchantment(of("launch"));

    public static final NexusEnchantment LAVA_WALKER = new LavaWalkerEnchantment(of("lava_walker"));

    public static final NexusEnchantment LIFESTEAL = new LifestealEnchantment(of("lifesteal"));

    public static final NexusEnchantment LIGHTNING = new LightningEnchantment(of("lightning"));

    public static final NexusEnchantment NETHER_SLAYER = new NetherSlayerEnchantment(of("nether_slayer"));

    public static final NexusEnchantment NIGHT_OWL = new NightOwlEnchantment(of("night_owl"));

    public static final NexusEnchantment ORE_EXCAVATION = new OreExcavationEnchantment(of("ore_excavation"));

    public static final NexusEnchantment PERISH = new PerishEnchantment(of("perish"));

    public static final NexusEnchantment PHOENIX = new PhoenixEnchantment(of("phoenix"));

    public static final NexusEnchantment PLUMMET = new PlummetEnchantment(of("plummet"));

    public static final NexusEnchantment POISON = new PoisonEnchantment(of("poison"));

    public static final NexusEnchantment POSEIDON = new PoseidonEnchantment(of("poseidon"));

    public static final NexusEnchantment PROJECTILE_DEFLECT = new ProjectileDeflectEnchantment(of("projectile_deflect"));

    public static final NexusEnchantment REPLANTER = new ReplanterEnchantment(of("replanter"));

    public static final NexusEnchantment ROCKET_ESCAPE = new RocketEscapeEnchantment(of("rocket_escape"));

    public static final NexusEnchantment SHOCKWAVE = new ShockwaveEnchantment(of("shockwave"));

    public static final NexusEnchantment TELEPATHY = new TelepathyEnchantment(of("telepathy"));

    public static final NexusEnchantment TIMBER = new TimberEnchantment(of("timber"));

    public static final NexusEnchantment TWINGE = new TwingeEnchantment(of("twinge"));

    public static final NexusEnchantment VOODOO = new VoodooEnchantment(of("voodoo"));

    public static void registerModEnchantments() {
        ModEnchantmentEffectComponentTypes.init();
        ModEnchantmentEffects.init();
        ModLootConditionTypes.init();

        NexusUtils.LOGGER.info("Registering Enchantments for " + NexusUtils.MOD_ID);
    }

    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, NexusUtils.idOf(id));
    }
}
