package net.quendy.companionsmod.entity.custom;

import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.quendy.companionsmod.entity.ai.GlaiveMaidenAttackGoal;

import java.util.*;

public class GlaiveMaidenEntity extends AbstractMaidenEntity {
    public GlaiveMaidenEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.goalSelector.addGoal(2, new GlaiveMaidenAttackGoal(this, 1.0D, true));
    }
}