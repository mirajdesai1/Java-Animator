package cs3500.animation.model;

import cs3500.animator.util.AnimationBuilder;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a model of an animation. Requires shapes to create an animation and supports
 * operations such as moving a shape, changing its color, and changing its size.
 */
public class ShapeAnimationModelImpl implements ShapeAnimationModel {

  protected final LinkedHashMap<String, Shape> shapes;
  protected final int[] bounds;

  /**
   * Constructs a default shape animation model with an empty list of shapes.
   */
  public ShapeAnimationModelImpl() {
    this.shapes = new LinkedHashMap<>();
    this.bounds = new int[]{0, 0, 500, 500};
  }

  /**
   * Constructs an animation with the given list of shapes and the bounds for the animation.
   *
   * @param shapes a hashmap of shapes to be seen in the animation
   * @param bounds the size of the animation given as an array [leftmost value, upmost value, width,
   *               height]
   * @throws IllegalArgumentException if the given hashmap of shapes is null
   */
  public ShapeAnimationModelImpl(LinkedHashMap<String, Shape> shapes, int[] bounds)
      throws IllegalArgumentException {
    if (shapes == null) {
      throw new IllegalArgumentException("Requires non-null input.");
    }
    this.shapes = shapes;
    this.bounds = bounds;
  }

  /**
   * Constructs a list of shapes with the given shape name and shape.
   *
   * @param key the id of the shape
   * @param s   the shape used in the animation
   * @throws IllegalArgumentException if the given inputs are null
   */
  public ShapeAnimationModelImpl(String key, Shape s) throws IllegalArgumentException {
    if (key == null || s == null) {
      throw new IllegalArgumentException("Requires non-null inputs");
    }
    this.shapes = new LinkedHashMap<>();
    shapes.put(key, s);
    this.bounds = new int[]{0, 0, 500, 500};
  }

  @Override
  public Map<String, Shape> getShapes() {
    return new LinkedHashMap<>(this.shapes);
  }

  @Override
  public int[] getBounds() {
    return this.bounds.clone();
  }

  @Override
  public Map<String, Shape> shapesAtTick(double tick) {
    LinkedHashMap result = new LinkedHashMap();

    for (String key : shapes.keySet()) {
      Shape s = shapes.get(key);
      Shape sInter = s.interpolate(tick);
      if (sInter != null) {
        result.put(key, sInter);
      }
    }
    return result;
  }

  @Override
  public int getMaxTick() {
    int result = 0;
    for (String key : shapes.keySet()) {
      Shape s = shapes.get(key);
      if (s.getMaxTick() > 0) {
        result = s.getMaxTick();
      }
    }
    return result;
  }

  @Override
  public void moveShape(String id, Position2D pos, int fromTick, int toTick)
      throws IllegalArgumentException {
    if (id == null || pos == null) {
      throw new IllegalArgumentException("Requires non-null inputs");
    }
    checkID(id);
    checkTicks(fromTick, toTick);

    shapes.get(id).changePosition(pos, fromTick, toTick);
  }

  @Override
  public void changeShapeSize(String id, Size2D size, int fromTick, int toTick)
      throws IllegalArgumentException {
    if (id == null || size == null) {
      throw new IllegalArgumentException("Requires non-null inputs");
    }
    checkID(id);
    checkTicks(fromTick, toTick);

    shapes.get(id).changeSize(size, fromTick, toTick);
  }

  @Override
  public void changeShapeColor(String id, Color col, int fromTick, int toTick)
      throws IllegalArgumentException {
    if (id == null || col == null) {
      throw new IllegalArgumentException("Requires non-null inputs");
    }
    checkID(id);
    checkTicks(fromTick, toTick);

    shapes.get(id).changeColor(col, fromTick, toTick);
  }

  private void checkID(String id) {
    if (!shapes.containsKey(id)) {
      throw new IllegalArgumentException("No shape by that name has been created!");
    }
  }

  private void checkTicks(int fromTick, int toTick) {
    if (toTick < fromTick || fromTick < 0) {
      throw new IllegalArgumentException("Invalid ticks!");
    }
  }

  @Override
  public void addShape(String id, Shape shape) throws IllegalArgumentException {
    if (id == null || shape == null) {
      throw new IllegalArgumentException("Requires non-null inputs");
    }
    this.shapes.put(id, shape);
  }

  @Override
  public void removeShape(String id) {
    this.shapes.remove(id);
  }

  /**
   * Returns a Builder object to construct a ShapeAnimationModelImpl with.
   *
   * @return a Builder object to build a ShapeAnimationModelImpl
   */
  public static Builder getBuilder() {
    return new Builder();
  }


  /**
   * Builds a ShapeAnimationModelImpl by calling the build method after constructing the model using
   * methods: setBounds, declareShape, addMotion, and addKeyFrame.
   */
  public static final class Builder implements AnimationBuilder<ShapeAnimationModel> {

    private final LinkedHashMap<String, Shape> shapes;
    private int[] bounds;

    Builder() {
      this.shapes = new LinkedHashMap<>();
      this.bounds = new int[]{0, 0, 500, 500};
    }

    @Override
    public ShapeAnimationModel build() {
      return new ShapeAnimationModelImpl(shapes, bounds);
    }

    @Override
    public AnimationBuilder<ShapeAnimationModel> setBounds(int x, int y, int width, int height) {
      this.bounds = new int[]{x, y, width, height};
      return this;
    }

    @Override
    public AnimationBuilder<ShapeAnimationModel> declareShape(String name, String type) {
      this.shapes.put(name, new Shape(type));
      return this;
    }

    @Override
    public AnimationBuilder<ShapeAnimationModel> addMotion(String name, int t1, int x1, int y1,
        int w1, int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2,
        int g2, int b2) {
      shapes.get(name).addFrame(new Position2D(x1, y1), new Color(r1, g1, b1), new Size2D(w1, h1),
          t1);
      shapes.get(name).addFrame(new Position2D(x2, y2), new Color(r2, g2, b2), new Size2D(w2, h2),
          t2);
      return this;
    }

    @Override
    public AnimationBuilder<ShapeAnimationModel> addKeyframe(String name, int t, int x, int y,
        int w, int h, int r, int g, int b) {
      this.shapes.get(name).addFrame(new Position2D(x, y), new Color(r, g, b), new Size2D(w, h),
          t);
      return this;
    }
  }


}
