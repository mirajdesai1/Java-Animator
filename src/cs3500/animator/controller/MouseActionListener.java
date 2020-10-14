package cs3500.animator.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

/**
 * A class to handle mouse actions, namely when a mouse button is released.
 */
public class MouseActionListener implements MouseListener {

  private Map<Integer, Runnable> mouseReleasedMap;

  /**
   * Set the map for key released events. Key released events in Java Swing are integer codes
   *
   * @param map a hashmap of operations
   */
  public void setMouseReleasedMap(Map<Integer, Runnable> map) {
    mouseReleasedMap = map;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    //not implemented
  }

  @Override
  public void mousePressed(MouseEvent e) {
    //not implemented
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (mouseReleasedMap.containsKey(e.getButton())) {
      mouseReleasedMap.get(e.getButton()).run();
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    //not implemented
  }

  @Override
  public void mouseExited(MouseEvent e) {
    //not implemented
  }
}
