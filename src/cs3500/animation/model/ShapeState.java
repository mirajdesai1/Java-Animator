package cs3500.animation.model;

import java.awt.Color;
import java.util.Objects;

/**
 * Represents a shape's position, color and size at a certain point in time.
 */
public class ShapeState {

  private final Position2D position;
  private final Color color;
  private final Size2D size;
  private final int atTick;

  /**
   * Constructs a shape state object to represent a shape's position, color and size at a given
   * point in time.
   *
   * @param pos    the position of the shape
   * @param col    the color of the shape
   * @param width  the width of the shape
   * @param height the height of the shape
   * @param atTick the tick at which this shape assumes this state
   * @throws IllegalArgumentException if color has been given rgb values that are not between 0 and
   *                                  255 inclusive
   * @throws IllegalArgumentException if width and height are non-positive
   * @throws IllegalArgumentException if given position or color are null
   */
  public ShapeState(Position2D pos, Color col, int width, int height, int atTick) {
    if (pos == null || col == null || width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Given invalid inputs");
    }
    this.position = pos;
    this.color = col;
    this.size = new Size2D(width, height);
    this.atTick = atTick;
  }

  /**
   * Constructs a shape state object to represent a shape's position, color and size at a given
   * point in time.
   *
   * @param pos    the position of the shape
   * @param col    the color of the shape
   * @param size   the size of the shape
   * @param atTick the tick at which this shape assumes this state
   * @throws IllegalArgumentException if color has been given rgb values that are not between 0 and
   *                                  255 inclusive
   * @throws IllegalArgumentException if width and height are non-positive
   * @throws IllegalArgumentException if given position or color are null
   */
  public ShapeState(Position2D pos, Color col, Size2D size, int atTick) {
    if (pos == null || col == null || size == null) {
      throw new IllegalArgumentException("Given invalid inputs");
    }
    this.position = pos;
    this.color = col;
    this.size = size;
    this.atTick = atTick;
  }


  @Override
  public String toString() {
    return String
        .format("%d %.0f %.0f %d %d %d %d %d", atTick, position.getX(), position.getY(),
            size.getWidth(), size.getHeight(),
            color.getRed(), color.getGreen(), color.getBlue());
  }

  /**
   * Return this shape state's tick.
   *
   * @return the tick of this shape state
   */
  public int getAtTick() {
    return atTick;
  }

  /**
   * Returns this shape state's position.
   *
   * @return the position of this shape state
   */
  public Position2D getPosition() {
    return new Position2D(position);
  }

  /**
   * Returns this shape state's size.
   *
   * @return the size of this shape state
   */
  public Size2D getSize() {
    return new Size2D(size);
  }

  /**
   * Returns this shape state's color.
   *
   * @return the color of this shape state
   */
  public Color getColor() {
    return new Color(color.getRed(), color.getGreen(), color.getBlue());
  }

  private int calculateInterpolation(int a, int b, int aTick, int bTick, double atTick) {
    return (int) ((a * ((bTick - atTick) / (double) (bTick - aTick))
        + b * ((atTick - aTick) / (double) (bTick - aTick))) + 0.5);
  }

  /**
   * Calculate the interpolation between two shape states at the given tick and return a Shape
   * object with the calculated parameters.
   *
   * @param ss        the given shape state to calculate interpolation with
   * @param tick      the tick at which the interpolation occurs
   * @param shapeType the type of shape to create a shape object of
   * @return a Shape object with parameters that conform with the animation at a given point in time
   */
  public Shape interpolate(ShapeState ss, double tick, String shapeType) {
    Position2D position;
    Size2D size;
    Color col;

    int x = calculateInterpolation((int) this.position.getX(), (int) ss.position.getX(),
        this.atTick, ss.atTick, tick);
    int y = calculateInterpolation((int) this.position.getY(), (int) ss.position.getY(),
        this.atTick, ss.atTick, tick);
    position = new Position2D(x, y);

    int width = calculateInterpolation(this.size.getWidth(), ss.size.getWidth(), this.atTick,
        ss.atTick, tick);
    int height = calculateInterpolation(this.size.getHeight(), ss.size.getHeight(), this.atTick,
        ss.atTick, tick);
    size = new Size2D(width, height);

    int r = calculateInterpolation(this.color.getRed(), ss.color.getRed(), this.atTick, ss.atTick,
        tick);
    int g = calculateInterpolation(this.color.getGreen(), ss.color.getGreen(), this.atTick,
        ss.atTick, tick);
    int b = calculateInterpolation(this.color.getBlue(), ss.color.getBlue(), this.atTick,
        ss.atTick, tick);
    col = new Color(r, g, b);

    return new Shape(position, col, size, shapeType);
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof ShapeState)) {
      return false;
    }
    ShapeState that = (ShapeState) a;
    return this.position.equals(that.position)
        && this.color.equals(that.color)
        && this.size.equals(that.size)
        && this.atTick == that.atTick;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.position, this.color, this.size, this.atTick);
  }
}
