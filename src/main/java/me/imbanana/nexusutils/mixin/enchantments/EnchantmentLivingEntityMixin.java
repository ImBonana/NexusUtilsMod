package me.imbanana.nexusutils.mixin.enchantments;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.effect.ModEffects;
import me.imbanana.nexusutils.enchantment.ModEnchantments;
import me.imbanana.nexusutils.enchantment.custom.LavaWalkerEnchantment;
import me.imbanana.nexusutils.networking.ModPackets;
import me.imbanana.nexusutils.util.accessors.ITridentEntity;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public abstract class EnchantmentLivingEntityMixin extends Entity implements Attackable {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot var1);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Shadow public abstract void remove(RemovalReason reason);

    @Shadow public abstract boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source);

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean clearStatusEffects();

    @Shadow public abstract ItemStack getStackInHand(Hand hand);

    @Shadow protected int playerHitTimer;

    @Shadow protected abstract boolean shouldDropLoot();

    @Shadow protected abstract void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops);

    @Shadow protected abstract void dropInventory();


    @Shadow public abstract Identifier getLootTable();

    @Shadow @Nullable protected PlayerEntity attackingPlayer;

    @Shadow public abstract long getLootTableSeed();

    @Shadow public abstract boolean isExperienceDroppingDisabled();

    @Shadow protected abstract boolean shouldAlwaysDropXp();

    @Shadow public abstract boolean shouldDropXp();

    @Shadow public abstract int getXpToDrop();

    public EnchantmentLivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    protected void InjectTick(CallbackInfo info) {
        ItemStack helmet = getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = getEquippedStack(EquipmentSlot.FEET);


        updateHeadlightEnchantment(helmet);
        updateExtraHealthEnchantment(helmet, chestplate, leggings, boots);
    }

    @ModifyVariable(method = "damage", at = @At(value = "HEAD"), ordinal = 0, argsOnly = true)
    protected float ModifyDamage(float amount, DamageSource source) {
        if(!(source.getAttacker() instanceof LivingEntity)) return amount;
        LivingEntity attacker = (LivingEntity) source.getAttacker();
        ItemStack chestplate = attacker.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack mainHandItem = attacker.getStackInHand(Hand.MAIN_HAND);

        float finalDamage = amount;

        // Nether / Ender Slayer Enchantment
        if (chestplate != null && chestplate.getItem() instanceof ArmorItem) {
            int netherSlayerEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.NETHER_SLAYER, chestplate);
            int enderSlayerEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.ENDER_SLAYER, chestplate);

            if (netherSlayerEnchantmentLevel > 0 && this.getWorld().getRegistryKey().getValue().equals(DimensionTypes.THE_NETHER_ID))
                finalDamage *= (1f + (0.1f * netherSlayerEnchantmentLevel));
            else if (enderSlayerEnchantmentLevel > 0 && this.getWorld().getRegistryKey().getValue().equals(DimensionTypes.THE_END_ID))
                finalDamage *= (1f + (0.1f * enderSlayerEnchantmentLevel));
        }

        // Double Strike Enchantment
        if(mainHandItem != null) {
            int doubleStrikeEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.DOUBLE_STRIKE, mainHandItem);

            if(doubleStrikeEnchantmentLevel > 0 && new Random().nextInt(1, 11) == 1) {
                finalDamage *= 1 + doubleStrikeEnchantmentLevel;
                this.getWorld().addBlockBreakParticles(this.getBlockPos().up(), Blocks.REDSTONE_BLOCK.getDefaultState());
            }
        }

        // Night Owl
        if(mainHandItem != null && EnchantmentHelper.getLevel(ModEnchantments.NIGHT_OWL, mainHandItem) > 0 && this.getWorld().isNight()) {
            finalDamage *= 1.2f;
        }

        // Impact Enchantment
        if((mainHandItem != null || source.getSource() instanceof TridentEntity) && !this.getWorld().isClient()) {
            int impactEnchantmentLevel = 0;
            if(source.getSource() instanceof TridentEntity) {
                ITridentEntity tridentEntity = (ITridentEntity) (TridentEntity) source.getSource();
                ItemStack itemStack = tridentEntity.nexusUtils$getTridentItemStack();
                impactEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.IMPACT, itemStack);
            } else {
                impactEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.IMPACT, mainHandItem);
            }

            if(impactEnchantmentLevel > 0 && new Random().nextInt(1, 11) == 1) {
                finalDamage *= 1 + impactEnchantmentLevel;

                PacketByteBuf packet = PacketByteBufs.create();
                packet.writeString(this.getWorld().getRegistryKey().getValue().toString());
                packet.writeBlockPos(this.getBlockPos().up());
                packet.writeString(Blocks.REDSTONE_BLOCK.getRegistryEntry().getKey().get().getValue().toString());

                for(ServerPlayerEntity playerEntity : this.getServer().getPlayerManager().getPlayerList()) {
                    if (playerEntity.getWorld() == ((ServerWorld) this.getWorld())) {
                        BlockPos blockPos = playerEntity.getBlockPos();
                        if (blockPos.isWithinDistance(new Vec3d(this.getX(), this.getX(), this.getX()), 512.0)) {
                            ServerPlayNetworking.send(playerEntity, ModPackets.BLOCK_PARTICLE, packet);
                        }
                        playerEntity.getServerWorld().addBlockBreakParticles(this.getBlockPos().up(), Blocks.REDSTONE_BLOCK.getDefaultState());
                    }
                }

            }
        }

        return finalDamage;
    }

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    protected void InjectDamageHead(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        ItemStack leggings = this.getEquippedStack(EquipmentSlot.LEGS);

        if(leggings != null) {
            if(source.getSource() instanceof ArrowEntity) {
                int arrowDeflectEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.ARROW_DEFLECT, leggings);

                if(arrowDeflectEnchantmentLevel > 0) {
                    if(new Random().nextInt(1, 9) == 1)
                        info.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "damage", at = @At(value = "TAIL"))
    protected void InjectDamageTail(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if(!(source.getAttacker() instanceof LivingEntity livingAttacker)) return;
        ItemStack attackerMainHandItemStack = livingAttacker.getStackInHand(Hand.MAIN_HAND);

        if(attackerMainHandItemStack != null) {
            if(source.getAttacker() instanceof PlayerEntity player) {
                int devourEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.DEVOUR, attackerMainHandItemStack);


                if(devourEnchantmentLevel > 0) {
                    if(new Random().nextInt(1, 3) == 1) {
                        float foodToGive = Math.min(6f + devourEnchantmentLevel, (amount / (5 - devourEnchantmentLevel)));
                        player.getHungerManager().setFoodLevel((int) Math.floor(Math.min(foodToGive + player.getHungerManager().getFoodLevel(), 20)));
                    }
                }
            }
        }

        if(attackerMainHandItemStack != null) {
            int lifestealEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.LIFESTEAL, attackerMainHandItemStack);

            if(lifestealEnchantmentLevel > 0) {
                if(new Random().nextInt(1, 5) == 1) {
                    float healthToGive = Math.min(3f + lifestealEnchantmentLevel, (amount / (6 - lifestealEnchantmentLevel)));
                    livingAttacker.heal(Math.max(healthToGive, 1f));
                }
            }
        }
    }

    @Inject(method = "tryUseTotem", at = @At(value = "TAIL"), cancellable = true)
    private void InjectTryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> info) {
        ItemStack helmet = this.getEquippedStack(EquipmentSlot.HEAD);

        ItemStack itemStack = null;
        for (Hand hand : Hand.values()) {
            ItemStack itemStack2 = this.getStackInHand(hand);
            if (!itemStack2.isOf(Items.TOTEM_OF_UNDYING)) continue;
            itemStack = itemStack2.copy();
            itemStack2.decrement(1);
            break;
        }

        if(itemStack == null && helmet != null) {
            int phoenixEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.PHOENIX, helmet);

            if(phoenixEnchantmentLevel > 0) {
                if(new Random().nextInt(1, 11) == 1) {
                    this.setHealth(1.0f);
                    this.clearStatusEffects();
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                    this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
                    this.getWorld().sendEntityStatus(this, EntityStatuses.USE_TOTEM_OF_UNDYING);

                    info.setReturnValue(true);
                }
            }
        }
    }

    @Inject(
            method = "handleFallDamage",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void InjectHandleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info, boolean bl, int i) {
        ItemStack boots = this.getEquippedStack(EquipmentSlot.FEET);

        // Plummet enchantment
        if(boots != null) {
            int plummetEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.PLUMMET, boots);

            if(plummetEnchantmentLevel > 0) {
                Box myBox = new Box(this.getBlockPos()).expand(3);
                List<MobEntity> entitiyList = this.getWorld().getEntitiesByClass(MobEntity.class, myBox, LivingEntity::isAlive);
                List<PlayerEntity> playerList = this.getWorld().getEntitiesByClass(PlayerEntity.class, myBox, LivingEntity::isAlive);

                float damage = ((float) i /2)+plummetEnchantmentLevel;

                for (MobEntity mobEntity : entitiyList) {
                    if(mobEntity.getUuid() == this.getUuid()) continue;
                    mobEntity.damage(this.getWorld().getDamageSources().mobAttack((LivingEntity) (Object) this), Math.min(mobEntity.getMaxHealth() / 2, damage));
                }

                for (PlayerEntity playerEntity : playerList) {
                    if(playerEntity.getUuid() == this.getUuid()) continue;
                    playerEntity.damage(this.getWorld().getDamageSources().mobAttack((LivingEntity) (Object) this), Math.min(playerEntity.getMaxHealth() / 2, damage));
                }
            }
        }

        // Aegis Enchantment
        if(boots != null) {
            int aegisEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.AEGIS, boots);

            if(aegisEnchantmentLevel > 0) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200));
            }
        }


        //! THIS NEED TO BE AT THE BOTTOM OF THE FUNC!!!
        // Jelly Legs Enchantment
        if(boots != null && !this.getWorld().isClient()) {
            int aegisEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.JELLY_LEGS, boots);

            if(aegisEnchantmentLevel > 0) {
                double minVel = 2;
                double velY = Math.min(this.fallDistance / 15, minVel);
                NexusUtils.LOGGER.info(String.valueOf(velY));
                NexusUtils.LOGGER.info(String.valueOf(this.fallDistance));
                this.setVelocity(new Vec3d(0, velY, 0));
                this.velocityModified = true;
                this.onLanding();
                info.setReturnValue(true);
            }
        }
    }

    @Inject(method = "applyMovementEffects", at = @At(value = "HEAD"))
    private void InjectApplyMovementEffects(BlockPos pos, CallbackInfo ci) {
        ItemStack boots = this.getEquippedStack(EquipmentSlot.FEET);
        if(boots == null) return;

        int lavaWalkerEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.LAVA_WALKER, boots);
        if (lavaWalkerEnchantmentLevel > 0) {
            LavaWalkerEnchantment.freezeLava((LivingEntity) (Object) this, this.getWorld(), pos, lavaWalkerEnchantmentLevel);
        }
    }

    @Inject(method = "drop", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;shouldDropLoot()Z", shift = At.Shift.BEFORE), cancellable = true)
    private void InjectDrop(DamageSource source, CallbackInfo info) {
        boolean bl;

        Entity entity = source.getAttacker();

        boolean bl2 = bl = this.playerHitTimer > 0;

        if(entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);

            int j = 0;

            if(source.getSource() instanceof TridentEntity tridentEntity) {
                ItemStack tridentStack = ((ITridentEntity) tridentEntity).nexusUtils$getTridentItemStack();

                if(tridentStack != null) j = EnchantmentHelper.getLevel(ModEnchantments.TELEPATHY, tridentStack);
            } else if(itemStack != null) {
                j = EnchantmentHelper.getLevel(ModEnchantments.TELEPATHY, itemStack);
            }

            if (j > 0) {
                int i = EnchantmentHelper.getLooting((LivingEntity) entity);

                if (this.shouldDropLoot() && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                    getDropLoot(source, bl, (item) -> player.getInventory().offerOrDrop(item));
                    this.dropEquipment(source, i, bl);
                }

                this.dropInventory();

                if (this.getWorld() instanceof ServerWorld && !this.isExperienceDroppingDisabled() && (this.shouldAlwaysDropXp() || this.playerHitTimer > 0 && this.shouldDropXp() && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT))) {
                    int experienceEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.EXPERIENCE, itemStack);
                    player.addExperience((int) Math.floor(this.getXpToDrop() * (experienceEnchantmentLevel > 0 ? (1.2f+(0.1f*experienceEnchantmentLevel)) : 1)));
                }

                info.cancel();
            }
        }
    }

    @Inject(method = "drop", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;dropXp()V", shift = At.Shift.BEFORE), cancellable = true)
    public void InjectDropAtDropXp(DamageSource source, CallbackInfo info) {
        if(source.getAttacker() instanceof LivingEntity livingAttacker) {
            ItemStack itemStack = livingAttacker.getStackInHand(Hand.MAIN_HAND);

            int experienceEnchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.EXPERIENCE, itemStack);

            if(experienceEnchantmentLevel > 0) {
                if (this.getWorld() instanceof ServerWorld && !this.isExperienceDroppingDisabled() && (this.shouldAlwaysDropXp() || this.playerHitTimer > 0 && this.shouldDropXp() && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT))) {
                    ExperienceOrbEntity.spawn((ServerWorld) this.getWorld(), this.getPos(), (int) Math.floor(this.getXpToDrop() * (1.2f + (0.1f * experienceEnchantmentLevel))));
                    info.cancel();
                }
            }
        }
    }

    @Unique
    void updateHeadlightEnchantment(ItemStack itemStack) {
        if(!(itemStack.getItem() instanceof ArmorItem)) return;

        int level = EnchantmentHelper.getLevel(ModEnchantments.HEADLIGHT, itemStack);
        if(level <= 0) return;

        this.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1, 0));
    }

    @Unique
    void updateExtraHealthEnchantment(ItemStack... armorPieces) {
        int healthToAdd = 0;

        for(ItemStack itemStack : armorPieces) {
            healthToAdd += getHealthAmountFromArmorPiece(itemStack);
        }

        if(healthToAdd > 0)
            this.addStatusEffect(new StatusEffectInstance(ModEffects.MORE_HEALTH, 2, healthToAdd - 1, false, false, false));
    }

    @Unique
    int getHealthAmountFromArmorPiece(ItemStack armor) {
        if(!(armor.getItem() instanceof ArmorItem)) return 0;
        return EnchantmentHelper.getLevel(ModEnchantments.EXTRA_HEALTH, armor);
    }

    @Unique
    void getDropLoot(DamageSource damageSource, boolean causedByPlayer, Consumer<ItemStack> loot) {
        Identifier identifier = this.getLootTable();
        LootTable lootTable = this.getWorld().getServer().getLootManager().getLootTable(identifier);
        LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld)this.getWorld()).add(LootContextParameters.THIS_ENTITY, this).add(LootContextParameters.ORIGIN, this.getPos()).add(LootContextParameters.DAMAGE_SOURCE, damageSource).addOptional(LootContextParameters.KILLER_ENTITY, damageSource.getAttacker()).addOptional(LootContextParameters.DIRECT_KILLER_ENTITY, damageSource.getSource());
        if (causedByPlayer && this.attackingPlayer != null) {
            builder = builder.add(LootContextParameters.LAST_DAMAGE_PLAYER, this.attackingPlayer).luck(this.attackingPlayer.getLuck());
        }
        LootContextParameterSet lootContextParameterSet = builder.build(LootContextTypes.ENTITY);
        lootTable.generateLoot(lootContextParameterSet, this.getLootTableSeed(), loot);
    }
}
