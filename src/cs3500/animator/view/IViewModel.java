package cs3500.animator.view;

import cs3500.animation.model.Shape;
import java.util.Map;

/**
 * Represents an animation model object that is only able to return data to the user. There are
 * no methods to mutate the model object.
 */
public interface IViewModel {
  /**
   * Get a hashmap of the shape names and their respective shapes.
   * @return a hashmap of the shape names and their respective shapes
   */
  Map<String, Shape> getShapes();

  /**
   * Get the bounds of an animation.
   * @return an int array containing [leftmost x value, upmost y value, width, height]
   */
  int[] getBounds();

  /**
   * Get all the shapes that are present at a given tick with their appropriate parameter values.
   *
   * @param tick the tick at which to retrieve the shapes at
   * @return a list of shapes that are present at the current tick
   */
  Map<String, Shape> shapesAtTick(double tick);

  /**
   * Obtain the max tick of the animation.
   *
   * @return an integer representing the maximum tick of the animation
   */
  int getMaxTick();

}
