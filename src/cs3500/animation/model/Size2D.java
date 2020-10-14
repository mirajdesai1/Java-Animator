package cs3500.animation.model;

import java.util.Objects;

/**
 * This class represents a 2D size object that is immutable.
 */
public final class Size2D {

  private final int width;
  private final int height;

  /**
   * Constructs a Size2D object with the given width and height parameters.
   *
   * @param width  the width of the object
   * @param height the height of the object
   * @throws IllegalArgumentException if width or height are non-positive
   */
  public Size2D(int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive");
    }
    this.width = width;
    this.height = height;
  }

  /**
   * Constructs a Size2D object with the given Size2D.
   *
   * @param s a Size2D object
   * @throws IllegalArgumentException if width or height are non-positive
   */
  public Size2D(Size2D s) {
    this(s.width, s.height);
  }

  /**
   * Return the width of this Size2D object.
   *
   * @return the width of this Size2D object
   */
  public int getWidth() {
    return width;
  }

  /**
   * Return the height of this Size2D object.
   *
   * @return the height of this Size2D object
   */
  public int getHeight() {
    return height;
  }

  @Override
  public String toString() {
    return String.format("%dx%d", this.width, this.height);
  }

  @Override
  public boolean equals(Object a) {
    if (this == a) {
      return true;
    }
    if (!(a instanceof Size2D)) {
      return false;
    }

    Size2D that = (Size2D) a;

    return this.height == that.height
        && this.width == that.width;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.width, this.height);
  }
}
