package net.quendy.companionsmod.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.quendy.companionsmod.CompanionsMod;
import net.quendy.companionsmod.util.ModTags;
import java.util.List;

public class ModToolTiers {
    public static final Tier PRISTINE = TierSortingRegistry.registerTier(
            new ForgeTier(5,
                    3000,
                    1f,
                    4f,
                    25,
                    ModTags.Blocks.PRISTINE_TOOL,
                    () -> Ingredient.of(Items.NETHERITE_SWORD)),
            new ResourceLocation(CompanionsMod.MOD_ID, "pristine"),
            List.of(Tiers.NETHERITE),
            List.of()
    );
}