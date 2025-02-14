package me.alegian.thavma.impl.common.menu.slot;

import me.alegian.thavma.impl.common.menu.Menu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

/**
 * A Slot that implements Sized, and has a back-reference to a Menu
 */
public class T7Slot<T extends Menu> extends Slot implements DynamicSlot<T> {
  private final int size;
  private final T menu;
  private int x,y;

  public T7Slot(Container container, int id, T menu, int size) {
    super(container, id, menu.getSlotPose().getX(), menu.getSlotPose().getY());
    this.size = size;
    this.menu = menu;
  }

  @Override
  public T getMenu() {
    return this.menu;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public void setX(int x) {
    this.x = x;
  }

  @Override
  public void setY(int y) {
    this.y = y;
  }

  @Override
  public int getSize() {
    return this.size;
  }
}
