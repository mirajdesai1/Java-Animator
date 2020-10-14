package cs3500.animator.view;

/**
 * This interface represents the view of a shape animation.
 */
public interface AnimationView {

  /**
   * Makes the view visible by writing its content to an output.
   */
  void makeVisible();

  /**
   * Commands the view to draw itself.
   */
  void refresh();

  /**
   * Sets the shapes for the view to draw.
   */
  void setShapesToDraw();

  /**
   * Converts the output of the program to a file. Not necessary for the visual animation.
   *
   * @param fileName the name of the file the user would like to create
   */
  void makeFile(String fileName);
}
