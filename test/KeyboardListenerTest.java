import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.KeyboardListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import org.junit.Test;

/**
 * Tests and examples for the KeyboardListener class.
 */
public class KeyboardListenerTest {

  KeyboardListener kl;

  private void initData() {
    kl = new KeyboardListener();
  }

  @Test
  public void testKeyType() {
    initData();
    Map<Character, Runnable> commands = new HashMap<>();
    StringBuilder test = new StringBuilder();
    commands.put('a', () -> test.append("a key typed"));
    kl.setKeyTypedMap(commands);

    kl.keyTyped(new KeyEvent(new JPanel(), 1, 2, 3, 4, 'a'));
    assertEquals("a key typed", test.toString());
  }

  @Test
  public void testKeyPressed() {
    initData();
    Map<Integer, Runnable> commands = new HashMap<>();
    StringBuilder test = new StringBuilder();
    commands.put(KeyEvent.VK_RIGHT, () -> test.append("right arrow pressed"));
    kl.setKeyPressedMap(commands);

    kl.keyPressed(new KeyEvent(new JPanel(), 1, 2, 3, KeyEvent.VK_RIGHT, 'r'));
    assertEquals("right arrow pressed", test.toString());
  }

  @Test
  public void testKeyReleased() {
    initData();
    Map<Integer, Runnable> commands = new HashMap<>();
    StringBuilder test = new StringBuilder();
    commands.put(KeyEvent.VK_L, () -> test.append("l key released"));
    kl.setKeyReleasedMap(commands);

    kl.keyReleased(new KeyEvent(new JPanel(), 1, 2, 3, KeyEvent.VK_L, 'l'));
    assertEquals("l key released", test.toString());
  }
}
