import static org.junit.Assert.assertEquals;

import cs3500.animation.model.Position2D;
import org.junit.Test;

/**
 * Represents tests and examples for the Position2D class.
 */
public class Position2DTest {

  @Test
  public void getXandY() {
    Position2D pos = new Position2D(5, 10);
    assertEquals(5, pos.getX(), 0.001);
    assertEquals(10, pos.getY(), 0.001);
  }

  @Test
  public void testToString() {
    Position2D pos = new Position2D(5, 10);
    assertEquals("(5.000000, 10.000000)", pos.toString());
  }

  @Test
  public void testHashCodeEquals() {
    Position2D a = new Position2D(5, 10);
    Position2D b = new Position2D(5, 10);
    assertEquals(a, b);
    assertEquals(b, a);
    assertEquals(a.hashCode(), b.hashCode());

  }
}