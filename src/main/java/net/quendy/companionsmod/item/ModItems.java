package net.quendy.companionsmod.item;

import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.quendy.companionsmod.CompanionsMod;
import net.quendy.companionsmod.entity.ModEntities;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CompanionsMod.MOD_ID);

    public static final RegistryObject<Item> PRISTINE_BLADE = ITEMS.register("pristine_blade",
            () -> new SwordItem(ModToolTiers.PRISTINE, 4, 1f, new Item.Properties()));

    public static final RegistryObject<Item> GLAIVE_MAIDEN_SPAWN_EGG = ITEMS.register("glaive_maiden_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GLAIVE_MAIDEN, 0x9f7516, 0x4d8423,
                    new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}