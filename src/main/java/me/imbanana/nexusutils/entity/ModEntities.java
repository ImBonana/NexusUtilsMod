package me.imbanana.nexusutils.entity;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.entity.custom.NetherBatEntity;
import me.imbanana.nexusutils.entity.custom.SnailEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModEntities {

    public static final EntityType<SnailEntity> SNAIL = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NexusUtils.MOD_ID, "snail"),

            FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, SnailEntity::new)
                .dimensions(EntityDimensions.fixed(0.3f, 0.3f))
                .build()
    );

    public static final EntityType<NetherBatEntity> NETHER_BAT = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NexusUtils.MOD_ID, "nether_bat"),

            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, NetherBatEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 0.3125f))
                    .trackRangeChunks(8)
                    .fireImmune()
                    .spawnableFarFromPlayer()
                    .build()
    );
}
