package net.quendy.companionsmod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.quendy.companionsmod.CompanionsMod;
import net.quendy.companionsmod.entity.custom.GlaiveMaidenEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CompanionsMod.MOD_ID);

    public static final RegistryObject<EntityType<GlaiveMaidenEntity>> GLAIVE_MAIDEN =
            ENTITY_TYPES.register("glaive_maiden", () -> EntityType.Builder.of(GlaiveMaidenEntity::new, MobCategory.CREATURE)
                    .sized(1.0F, 1.0F).build("glaive_maiden"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}