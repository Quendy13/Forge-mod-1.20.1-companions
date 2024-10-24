package net.quendy.companionsmod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.quendy.companionsmod.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AbstractMaidenEntity extends TamableAnimal {
    private Item previousSkinItem = Items.AIR;
    private static final Map<Item, Integer> ITEM_TO_SKIN_TYPE = new HashMap<>();

    static {
        ITEM_TO_SKIN_TYPE.put(Items.STICK, 0); // Default skin //TODO: changer pour mettre un item custom pour le default skin
        ITEM_TO_SKIN_TYPE.put(ModItems.APRON.get(), 1); // Apron skin
        ITEM_TO_SKIN_TYPE.put(ModItems.CROWN.get(), 2); // Crown skin
    }
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(GlaiveMaidenEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SKIN_TYPE =
            SynchedEntityData.defineId(GlaiveMaidenEntity.class, EntityDataSerializers.INT);
    private static final float START_HEALTH = 8.0F;
    private static final float TAME_HEALTH = 20.0F;

    public AbstractMaidenEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setTame(false);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    //public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    // Les animations d'attaque des différentes Maiden n'auront probablement pas la même durée
    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

//        if (this.isAttacking() && attackAnimationTimeout <= 0) {
//            attackAnimationTimeout = 8;  // Modifier temps
//            attackAnimationState.start(this.tickCount);
//        } else {
//            --this.attackAnimationTimeout;
//        }
//
//        if (!this.isAttacking()) {
//            attackAnimationState.stop();
//        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        //FollowOwner 3
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        //BegGoal 5
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 4f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        //targetSelector.OwnerHurtByTargetGoal 1
        //targetSelector.OwnerHurtTargetGoal 2
        //targetSelector.(HurtByTargetGoal).setAlertOthers 3
        //targetSelector.NearestAttackableTargetGoal 4
        //targetSelector.ResetUniversalAngerTargetGoal 5
    }

    //ATTACK_DAMAGE et ATTACK_KNOCKBACK seront différents
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.ATTACK_KNOCKBACK) //peut-être mettre une valeur
                .add(Attributes.FOLLOW_RANGE, 15.0D)
                .add(Attributes.MAX_HEALTH, 20.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.32D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        this.entityData.define(SKIN_TYPE, 0);
    }

    public void setSkinType(int type) {
        this.entityData.set(SKIN_TYPE, type);
    }

    public int getSkinType() {
        return this.entityData.get(SKIN_TYPE);
    }

    private boolean isValidSkinItem(Item item) {
        return ITEM_TO_SKIN_TYPE.containsKey(item);
    }

    private int getSkinTypeForItem(Item item) {
        return ITEM_TO_SKIN_TYPE.getOrDefault(item, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("SkinType", this.getSkinType());

        ResourceLocation itemKey = ForgeRegistries.ITEMS.getKey(this.previousSkinItem);
        if (itemKey != null) {
            pCompound.putString("PreviousSkinItem", itemKey.toString());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("SkinType")) {
            this.setSkinType(pCompound.getInt("SkinType"));
        }

        if (pCompound.contains("PreviousSkinItem")) {
            ResourceLocation itemKey = new ResourceLocation(pCompound.getString("PreviousSkinItem"));
            this.previousSkinItem = ForgeRegistries.ITEMS.getValue(itemKey);
        }
    }

    public void die(DamageSource pCause) {
        super.die(pCause);
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            Entity entity = pSource.getEntity();
            if (!this.level().isClientSide) {
                this.setOrderedToSit(false);
            }

            if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                pAmount = (pAmount + 1.0F) / 2.0F;
            }

            return super.hurt(pSource, pAmount);
        }
    }

    public boolean doHurtTarget(Entity pEntity) {
        boolean flag = pEntity.hurt(this.damageSources().mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, pEntity);
        }

        return flag;
    }

    //TODO: à modifier (sera différent pour toutes)
    public void setTame(boolean pTamed) {
        super.setTame(pTamed);
        if (pTamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();

        if (this.level().isClientSide) {
            return InteractionResult.CONSUME;
        } else {
            if (isValidSkinItem(item)) {
                if (this.previousSkinItem != Items.AIR && this.previousSkinItem != Items.STICK) {
                    ItemEntity droppedItem = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(),
                            new ItemStack(this.previousSkinItem));
                    this.level().addFreshEntity(droppedItem);
                }

                this.setSkinType(getSkinTypeForItem(item));
                this.previousSkinItem = item;

                if (getSkinTypeForItem(item) != 0) {
                    itemstack.shrink(1);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return super.mobInteract(pPlayer, pHand);

      /*
      if (this.level().isClientSide) {
         boolean flag = this.isOwnedBy(pPlayer) || this.isTame() || itemstack.is(Items.BONE) && !this.isTame() && !this.isAngry();
         return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
      } else if (this.isTame()) {
         if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
            this.heal((float)itemstack.getFoodProperties(this).getNutrition());
            if (!pPlayer.getAbilities().instabuild) {
               itemstack.shrink(1);
            }

            this.gameEvent(GameEvent.EAT, this);
            return InteractionResult.SUCCESS;
         } else {
            if (item instanceof DyeItem) {
               DyeItem dyeitem = (DyeItem)item;
               if (this.isOwnedBy(pPlayer)) {
                  DyeColor dyecolor = dyeitem.getDyeColor();
                  if (dyecolor != this.getCollarColor()) {
                     this.setCollarColor(dyecolor);
                     if (!pPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                     }

                     return InteractionResult.SUCCESS;
                  }

                  return super.mobInteract(pPlayer, pHand);
               }
            }

            InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
            if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(pPlayer)) {
               this.setOrderedToSit(!this.isOrderedToSit());
               this.jumping = false;
               this.navigation.stop();
               this.setTarget((LivingEntity)null);
               return InteractionResult.SUCCESS;
            } else {
               return interactionresult;
            }
         }
      } else if (itemstack.is(Items.BONE) && !this.isAngry()) {
         if (!pPlayer.getAbilities().instabuild) {
            itemstack.shrink(1);
         }

         if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, pPlayer)) {
            this.tame(pPlayer);
            this.navigation.stop();
            this.setTarget((LivingEntity)null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, (byte)7);
         } else {
            this.level().broadcastEntityEvent(this, (byte)6);
         }

         return InteractionResult.SUCCESS;
      } else {
         return super.mobInteract(pPlayer, pHand);*/
    }

    //TODO : custom sounds
    /*protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return SoundEvents.WOLF_GROWL;
        } else if (this.random.nextInt(3) == 0) {
            return this.isTame() && this.getHealth() < 10.0F ? SoundEvents.WOLF_WHINE : SoundEvents.WOLF_PANT;
        } else {
            return SoundEvents.WOLF_AMBIENT;
        }
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.WOLF_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }*/

    //TODO: Choisir ou créer un bloc pour spwan naturellement
    /*public static boolean checkWolfSpawnRules(EntityType<Wolf> pWolf, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
      return pLevel.getBlockState(pPos.below()).is(BlockTags.WOLVES_SPAWNABLE_ON) && isBrightEnoughToSpawn(pLevel, pPos);
   }*/
}
