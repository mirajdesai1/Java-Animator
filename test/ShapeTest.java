import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import cs3500.animation.model.Position2D;
import cs3500.animation.model.Shape;
import cs3500.animation.model.ShapeState;
import cs3500.animation.model.Size2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Tests and examples for the Shape class.
 */
public class ShapeTest {

  Shape rectangle;
  Shape circle;

  private void initData() {
    rectangle = new Shape(new Position2D(0, 5), new Color(255, 0, 255),
        new Size2D(50, 60), "rectangle");
    circle = new Shape(new Position2D(60, 65), new Color(255, 255, 0),
        new Size2D(50, 50), "circle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ConstructorDisallowsInvalidRGB() {
    new Shape(new Position2D(50, 50), new Color(-5, 0, 255),
        new Size2D(10, 10), "square");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ConstructorDisallowsInvalidSize() {
    new Shape(new Position2D(50, 50), new Color(255, 0, 255),
        new Size2D(-3, 10), "rectangle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ConstructorDisallowsSize0() {
    new Shape(new Position2D(50, 50), new Color(200, 0, 255),
        new Size2D(10, 0), "rectangle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ConstructorDisallowsNullPosition() {
    new Shape(null, new Color(200, 0, 255),
        new Size2D(10, 5), "rectangle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ConstructorDisallowsNullColor() {
    new Shape(new Position2D(50, 50), null,
        new Size2D(10, 5), "rectangle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void ConstructorDisallowsNullSize() {
    new Shape(new Position2D(50, 50), new Color(200, 0, 255),
        null, "rectangle");
  }

  @Test
  public void changePosition() {
    initData();
    circle.changePosition(new Position2D(3, 5), 20, 40);
    assertEquals("40 3 5 50 50 255 255 0", circle.getHistory().get(2).toString());
  }

  @Test
  public void changeColor() {
    initData();
    circle.changeColor(new Color(20, 40, 60), 20, 21);
    assertEquals("21 60 65 50 50 20 40 60", circle.getHistory().get(2).toString());
  }

  @Test
  public void changeSize() {
    initData();
    circle.changeSize(new Size2D(1, 2), 20, 30);
    assertEquals("30 60 65 1 2 255 255 0", circle.getHistory().get(2).toString());
  }

  @Test
  public void testToString() {
    initData();
    assertEquals("rectangle", rectangle.toString());
    assertEquals("circle", circle.toString());
  }


  @Test
  public void getHistory() {
    initData();
    List<ShapeState> r = new ArrayList<>();
    r.add(new ShapeState(rectangle.getPosition(), rectangle.getC(), rectangle.getSize(),
        rectangle.getMaxTick()));
    assertEquals(r, rectangle.getHistory());

    rectangle.addShapeState(30);
    assertEquals("30 0 5 50 60 255 0 255", rectangle.getHistory().get(1).toString());

    //test history cannot be mutated through getHistory()
    rectangle.getHistory().set(0, null);
    assertNotNull(rectangle.getHistory().get(0));

  }

  @Test
  public void getMaxTick() {
    initData();
    assertEquals(0, rectangle.getMaxTick());

    rectangle.changePosition(new Position2D(4, 5), 1, 40);
    assertEquals(40, rectangle.getMaxTick());
  }

  @Test
  public void testInterpolate() {
    initData();
    rectangle.changePosition(new Position2D(50, 55), 1, 5);
    assertEquals(new Position2D(0, 5), rectangle.interpolate(1).getPosition());
    assertEquals(new Position2D(13, 18), rectangle.interpolate(2).getPosition());
    assertEquals(new Position2D(25, 30), rectangle.interpolate(3).getPosition());
    assertEquals(new Position2D(38, 43), rectangle.interpolate(4).getPosition());
    assertEquals(new Position2D(50, 55), rectangle.interpolate(5).getPosition());

    assertEquals(new Size2D(50, 60), rectangle.interpolate(1).getSize());
    assertEquals(new Size2D(50, 60), rectangle.interpolate(2).getSize());
    assertEquals(new Size2D(50, 60), rectangle.interpolate(3).getSize());

    assertEquals(new Color(255, 0, 255), rectangle.interpolate(1).getC());
    assertEquals(new Color(255, 0, 255), rectangle.interpolate(2).getC());
    assertEquals(new Color(255, 0, 255), rectangle.interpolate(3).getC());

    assertNull(rectangle.interpolate(5235));
  }
}