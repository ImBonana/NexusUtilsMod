package me.imbanana.nexusutils.entity;

import me.imbanana.nexusutils.NexusUtils;
import me.imbanana.nexusutils.entity.custom.SnailEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;


public class ModEntities {
    public static final EntityType<SnailEntity> SNAIL = register(
            NexusUtils.idOf("snail"),
            SnailEntity::new,
            SpawnGroup.AMBIENT,
            builder -> builder
                    .dimensions(0.3f, 0.3f)
    );

    private static <T extends Entity> EntityType<T> register(Identifier id, EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup, Consumer<EntityType.Builder<T>> builderFunc) {
        EntityType.Builder<T> builder = EntityType.Builder.create(factory, spawnGroup);
        builderFunc.accept(builder);

        return Registry.register(
                Registries.ENTITY_TYPE,
                id,
                builder.build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, id))
        );
    }
}
