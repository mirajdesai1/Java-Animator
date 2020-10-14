import static org.junit.Assert.assertEquals;

import cs3500.animation.model.Size2D;
import org.junit.Test;

/**
 * Tests and examples for the Size2D class.
 */
public class Size2DTest {

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSize() {
    new Size2D(0, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSize1() {
    new Size2D(10, -5);
  }

  @Test
  public void getWidthHeight() {
    Size2D size = new Size2D(50, 100);

    assertEquals(50, size.getWidth());
    assertEquals(100, size.getHeight());
  }

  @Test
  public void testToString() {
    Size2D size = new Size2D(50, 100);
    assertEquals("50x100", size.toString());
  }

  @Test
  public void testHashCodeEquals() {
    Size2D a = new Size2D(100, 200);
    Size2D b = new Size2D(100, 200);
    assertEquals(a, b);
    assertEquals(b, a);
    assertEquals(a.hashCode(), b.hashCode());
  }
}