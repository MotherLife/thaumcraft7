package me.alegian.thaumcraft7.impl.common.entity;

import me.alegian.thaumcraft7.impl.init.registries.deferred.T7EntityTypes;
import me.alegian.thaumcraft7.impl.init.registries.deferred.T7Items;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FancyThaumonomicon extends ItemEntity {
  public FancyThaumonomicon(Level pLevel, BlockPos blockPos) {
    super(T7EntityTypes.FANCY_ITEM.get(), pLevel);
    this.setPos(blockPos.getX() + .5f, blockPos.getY() + .5f, blockPos.getZ() + .5f);
    this.setDeltaMovement(0, 0, 0);
    var itemstack = new ItemStack(T7Items.THAUMONOMICON.get());
    this.setItem(itemstack);
    this.setDefaultPickUpDelay();
    this.lifespan = itemstack.getEntityLifespan(pLevel);
  }
}
