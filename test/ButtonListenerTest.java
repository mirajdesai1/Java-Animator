import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.ButtonListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import org.junit.Test;

/**
 * Tests and examples for the ButtonListener class.
 */
public class ButtonListenerTest {
  ButtonListener bl;

  private void initData() {
    bl = new ButtonListener();
  }

  @Test
  public void testButtonPress() {
    initData();
    Map<String, Runnable> buttons = new HashMap<>();
    StringBuilder test = new StringBuilder();
    buttons.put("Add A", () -> test.append("A"));
    bl.setButtonClickedActionMap(buttons);

    bl.actionPerformed(new ActionEvent(new JButton(), 1, "Add A"));
    assertEquals("A", test.toString());
  }

  @Test
  public void testButtonPressDoNothing() {
    initData();
    Map<String, Runnable> buttons = new HashMap<>();
    StringBuilder test = new StringBuilder();
    buttons.put("Add A", () -> test.append("A"));
    bl.setButtonClickedActionMap(buttons);

    bl.actionPerformed(new ActionEvent(new JButton(), 1, "Add B"));
    assertEquals("", test.toString());
  }
}
