package net.quendy.companionsmod.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.quendy.companionsmod.CompanionsMod;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> PRISTINE_TOOL = tag("pristine_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(CompanionsMod.MOD_ID, name));
        }
    }
}
