package me.imbanana.nexusutils.entity;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.entity.custom.SnailEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


public class ModEntities {

    public static final EntityType<SnailEntity> SNAIL = Registry.register(
            Registries.ENTITY_TYPE,
            NexusUtils.idOf("snail"),

            EntityType.Builder.create(SnailEntity::new, SpawnGroup.AMBIENT)
                .dimensions(0.3f, 0.3f)
                .build()
    );
}
