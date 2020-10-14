package cs3500.animation.model;

import java.util.Objects;

/**
 * This class represents a 2D position.
 */
public final class Position2D {

  private final double x;

  private final double y;

  /**
   * Initialize this object to the specified position.
   *
   * @param x the x position
   * @param y the y position
   */
  public Position2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Initialize this object to the specified Position2D.
   *
   * @param v the Position2D object to initialize this position with
   */
  public Position2D(Position2D v) {
    this(v.x, v.y);
  }


  /**
   * Return this position's x value.
   *
   * @return this position's x value
   */
  public double getX() {
    return x;
  }

  /**
   * Return this position's y value.
   *
   * @return this position's y value
   */
  public double getY() {
    return y;
  }

  @Override
  public String toString() {
    return String.format("(%f, %f)", this.x, this.y);
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Position2D)) {
      return false;
    }

    Position2D that = (Position2D) a;

    return ((Math.abs(this.x - that.x) < 0.01) && (Math.abs(this.y - that.y) < 0.01));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}
