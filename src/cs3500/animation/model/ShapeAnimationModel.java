package cs3500.animation.model;

import cs3500.animator.view.IViewModel;
import java.awt.Color;

/**
 * Represents a declarative animation made up of shapes and supports operations such as moving a
 * shape, changing its color, and changing its size.
 */
public interface ShapeAnimationModel extends IViewModel {

  /**
   * Moves the specified shape to the specified x and y positions, from the specified tick to the
   * specified tick.
   *
   * @param id       the HashMap index of the shape that is to be moved
   * @param pos      the shape's ending position
   * @param fromTick the starting time of the movement
   * @param toTick   the ending time of the movement
   * @throws IllegalArgumentException if the given id is null
   */
  void moveShape(String id, Position2D pos, int fromTick, int toTick)
      throws IllegalArgumentException;

  /**
   * Changes the size of the specified shape.
   *
   * @param id       the HashMap index of the shape whose size is being changed
   * @param size     the ending size of the shape
   * @param fromTick the starting time of the change
   * @param toTick   the ending time of the change
   * @throws IllegalArgumentException if the size parameters are non-positive
   * @throws IllegalArgumentException if the given id is null
   */
  void changeShapeSize(String id, Size2D size, int fromTick, int toTick)
      throws IllegalArgumentException;

  /**
   * Changes the color of the specified shape.
   *
   * @param id       the HashMap index of the shape whose size is being changed
   * @param col      the Color the shape is to change to
   * @param fromTick the starting time of the change
   * @param toTick   the ending time of the change
   * @throws IllegalArgumentException if r, g, b are outside of the range 0 to 255, inclusive
   * @throws IllegalArgumentException if the given id is null
   */
  void changeShapeColor(String id, Color col, int fromTick, int toTick)
      throws IllegalArgumentException;

  /**
   * Adds the given shape with the given name to the animation model.
   *
   * @param id    the variable name of the shape
   * @param shape the shape to be added
   * @throws IllegalArgumentException if the given id or shape are null
   */
  void addShape(String id, Shape shape) throws IllegalArgumentException;

  /**
   * Removes the given shape from the animation model.
   *
   * @param id the variable name of the shape
   * @throws IllegalArgumentException if the given id is null
   */
  void removeShape(String id) throws IllegalArgumentException;

}
