package cs3500.animation.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shape to be used in the animation model. Stores the shape's position, color, size,
 * and the number of sides it has (0 if it's a circle or an oval).
 */
public class Shape {

  private Position2D position;
  private Color c;
  private Size2D size;
  private final String type;
  private final List<ShapeState> history;

  /**
   * Constructs a shape object with default starting parameters.
   *
   * @param type the type of shape
   */
  public Shape(String type) {
    checkNull(type);
    this.position = new Position2D(0, 0);
    this.c = new Color(0, 0, 0);
    this.size = new Size2D(10, 10);
    this.type = type;
    this.history = new ArrayList<>();
  }

  /**
   * Constructs a Shape object with given starting parameters.
   *
   * @param startPos the starting position (x, y) of a shape
   * @param color    the color of a shape
   * @param size     the size (width and height) of a shape
   * @param type     the type of shape
   * @throws IllegalArgumentException if r, g, b values are outside of the range 0 to 255,
   *                                  inclusive
   * @throws IllegalArgumentException if size values are non-positive
   * @throws IllegalArgumentException if the given inputs are null
   */
  public Shape(Position2D startPos, Color color, Size2D size, String type)
      throws IllegalArgumentException {
    checkNull(startPos, color, size, type);
    this.position = startPos;
    this.c = color;
    this.size = size;
    this.type = type;
    this.history = new ArrayList<>();
    this.addShapeState(0);
  }


  /**
   * Changes the position of the shape to the given coordinates.
   *
   * @param pos      the position to set this shape to
   * @param fromTick the tick at which this change starts
   * @param toTick   the tick at which this change ends
   * @throws IllegalArgumentException if the given Position2D is null
   */
  public void changePosition(Position2D pos, int fromTick, int toTick)
      throws IllegalArgumentException {
    checkNull(pos);
    insert(new ShapeState(this.position, this.c, this.size, fromTick), history);
    position = pos;
    insert(new ShapeState(pos, this.c, this.size, toTick), history);
  }

  /**
   * Changes the color of the shape to the given color.
   *
   * @param col      the color to set this shape as
   * @param fromTick the tick at which this change starts
   * @param toTick   the tick at which this change ends
   * @throws IllegalArgumentException if the rbg parameters are not between 0 and 255 inclusive
   */
  public void changeColor(Color col, int fromTick, int toTick) throws IllegalArgumentException {
    checkNull(col);
    insert(new ShapeState(this.position, this.c, this.size, fromTick), history);
    this.c = col;
    insert(new ShapeState(this.position, col, this.size, toTick), history);
  }

  /**
   * Changes the size of the shape to the given width and height.
   *
   * @param s        the size to set this shape to
   * @param fromTick the tick at which this change starts
   * @param toTick   the tick at which this change ends
   * @throws IllegalArgumentException if the width or height values are non-positive
   */
  public void changeSize(Size2D s, int fromTick, int toTick) throws IllegalArgumentException {
    checkNull(s);
    insert(new ShapeState(this.position, this.c, this.size, fromTick), history);
    this.size = s;
    insert(new ShapeState(this.position, this.c, s, toTick), history);
  }

  /**
   * Checks if the objects are null and throws an IllegalArgumentException if any of them are.
   *
   * @param o the objects to be checked
   * @throws IllegalArgumentException if any of the objects are null
   */
  private void checkNull(Object... o) throws IllegalArgumentException {
    for (Object obj : o) {
      if (obj == null) {
        throw new IllegalArgumentException("Requires non-null input!");
      }
    }
  }

  @Override
  public String toString() {
    return this.type;
  }


  /**
   * Creates and adds the current shape state to this shape's history.
   *
   * @param atTick the tick at which this shape state is present
   */
  public void addShapeState(int atTick) {
    ShapeState ss = new ShapeState(position, c, size.getWidth(), size.getHeight(), atTick);
    history.add(ss);
  }

  /**
   * Goes through the shape history and removes the shape state at the given tick.
   *
   * @param atTick the tick at which to remove the shape state
   */
  public void removeShapeState(int atTick) {

    for (int i = 0; i < history.size(); i++) {
      if (history.get(i).getAtTick() == atTick) {
        history.remove(i);
      }
    }
  }

  /**
   * Return a copy of this shape's list of motions.
   *
   * @return a copy of this shape's list of motions
   */
  public ArrayList<ShapeState> getHistory() {
    return new ArrayList<>(history);
  }

  /**
   * Return the max tick of this shape's list of motions.
   *
   * @return the max tick of this shape's list of motions
   */
  public int getMaxTick() {
    if (history.size() > 0) {
      return history.get(history.size() - 1).getAtTick();
    }
    return 0;
  }

  /**
   * Adds a new ShapeState to the history.
   *
   * @param pos  the position of the ShapeState
   * @param col  the color of the ShapeState
   * @param size the size of the ShapeState
   * @param tick the tick at which the shape is in the given ShapeState
   */
  public void addFrame(Position2D pos, Color col, Size2D size, int tick) {
    insert(new ShapeState(pos, col, size, tick), history);
  }

  /**
   * Inserts the given shape state into the shape's history.
   *
   * @param ss      the shape state to insert
   * @param history the list in which to insert the shape state
   */
  private void insert(ShapeState ss, List<ShapeState> history) {
    if (history.isEmpty()) {
      history.add(ss);
      return;
    }

    int tick = ss.getAtTick();
    for (int i = 0; i < history.size(); i++) {
      if (tick == history.get(i).getAtTick()) {
        history.set(i, ss);
        return;
      }
      if (tick < history.get(i).getAtTick()) {
        history.add(i - 1, ss);
        return;
      }
    }
    history.add(ss);

  }

  /**
   * Calculate the interpolation of this shape at the given tick.
   *
   * @param tick the tick at which the interpolation should occur
   * @return a new shape that has all of the parameters that reflect the effect of interpolation
   */
  public Shape interpolate(double tick) {
    if (history.isEmpty()) {
      return null;
    }
    if (tick < history.get(0).getAtTick() || tick > getMaxTick()) {
      return null;
    }
    for (int i = 0; i < history.size(); i++) {
      if (tick <= history.get(i).getAtTick()) {
        if (i > 0) {
          return history.get(i - 1).interpolate(history.get(i), tick, this.type);
        }
      }
    }
    return null;
  }

  /**
   * Returns the color of the shape.
   *
   * @return the color of the shape
   */
  public Color getC() {
    return c;
  }

  /**
   * Returns the position of the shape.
   *
   * @return the position of the shape
   */
  public Position2D getPosition() {
    return position;
  }

  /**
   * Returns the size of the shape.
   *
   * @return the size of the shape
   */
  public Size2D getSize() {
    return size;
  }

}
