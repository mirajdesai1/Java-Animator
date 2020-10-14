import static org.junit.Assert.assertEquals;

import cs3500.animation.model.Position2D;
import cs3500.animation.model.ShapeState;
import cs3500.animation.model.Size2D;
import java.awt.Color;
import org.junit.Test;

/**
 * Tests and examples for the ShapeState class.
 */
public class ShapeStateTest {

  @Test(expected = IllegalArgumentException.class)
  public void ConstructorDisallowsInvalidRGB() {
    new ShapeState(new Position2D(50, 50), new Color(-5, 0, 255),
        new Size2D(10, 10), 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void ConstructorDisallowsInvalidSize() {
    new ShapeState(new Position2D(50, 50), new Color(255, 0, 255),
        new Size2D(-3, 10), 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void ConstructorDisallowsSize0() {
    new ShapeState(new Position2D(50, 50), new Color(200, 0, 255),
        new Size2D(10, 0), 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void ConstructorDisallowsNullPosition() {
    new ShapeState(null, new Color(200, 0, 255),
        new Size2D(10, 5), 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void ConstructorDisallowsNullColor() {
    new ShapeState(new Position2D(50, 50), null,
        new Size2D(10, 5), 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void ConstructorDisallowsNullSize() {
    new ShapeState(new Position2D(50, 50), new Color(200, 0, 255),
        null, 4);
  }

  @Test
  public void testToString() {
    ShapeState ss = new ShapeState(new Position2D(100, 200), Color.BLACK, 50, 50, 40);
    assertEquals("40 100 200 50 50 0 0 0", ss.toString());

    ShapeState ss1 = new ShapeState(new Position2D(150.5, 160.2),
        new Color(40, 50, 60), 20, 30, 60);
    assertEquals("60 151 160 20 30 40 50 60", ss1.toString());
  }

  @Test
  public void testGetAtTick() {
    ShapeState ss = new ShapeState(new Position2D(100, 200), Color.BLACK, 50, 50, 40);
    assertEquals(40, ss.getAtTick());
  }

  @Test
  public void testGetPosition() {
    ShapeState ss = new ShapeState(new Position2D(100, 200), Color.BLACK, 50, 50, 40);
    assertEquals(new Position2D(100, 200), ss.getPosition());
  }

  @Test
  public void testGetSize() {
    ShapeState ss = new ShapeState(new Position2D(100, 200), Color.BLACK, 50, 50, 40);
    assertEquals(new Size2D(50, 50), ss.getSize());
  }

  @Test
  public void testGetColor() {
    ShapeState ss = new ShapeState(new Position2D(100, 200), Color.BLACK, 50, 50, 40);
    assertEquals(Color.BLACK, ss.getColor());
  }

  @Test
  public void testHashCodeEquals() {
    ShapeState ss1 = new ShapeState(new Position2D(100, 200), Color.BLACK, 50, 50, 40);
    ShapeState ss2 = new ShapeState(new Position2D(100, 200), Color.BLACK, 50, 50, 40);
    assertEquals(ss1, ss2);
    assertEquals(ss2, ss1);
    assertEquals(ss1.hashCode(), ss2.hashCode());
  }

  @Test
  public void testInterpolate() {
    ShapeState ss1 = new ShapeState(new Position2D(100, 200), Color.BLACK, 50, 50, 1);
    ShapeState ss2 = new ShapeState(new Position2D(100, 200), Color.BLACK, 100, 100, 6);

    assertEquals(new Size2D(50, 50), ss1.interpolate(ss2, 1, "rectangle").getSize());
    assertEquals(new Size2D(60, 60), ss1.interpolate(ss2, 2, "rectangle").getSize());
    assertEquals(new Size2D(70, 70), ss1.interpolate(ss2, 3, "rectangle").getSize());
    assertEquals(new Size2D(80, 80), ss1.interpolate(ss2, 4, "rectangle").getSize());
    assertEquals(new Size2D(90, 90), ss1.interpolate(ss2, 5, "rectangle").getSize());
    assertEquals(new Size2D(100, 100), ss1.interpolate(ss2, 6, "rectangle").getSize());

    assertEquals(new Position2D(100, 200), ss1.interpolate(ss2, 1, "rectangle").getPosition());
    assertEquals(new Position2D(100, 200), ss1.interpolate(ss2, 2, "rectangle").getPosition());
    assertEquals(new Position2D(100, 200), ss1.interpolate(ss2, 3, "rectangle").getPosition());

    assertEquals(Color.BLACK, ss1.interpolate(ss2, 1, "rectangle").getC());
    assertEquals(Color.BLACK, ss1.interpolate(ss2, 2, "rectangle").getC());
    assertEquals(Color.BLACK, ss1.interpolate(ss2, 3, "rectangle").getC());

  }
}