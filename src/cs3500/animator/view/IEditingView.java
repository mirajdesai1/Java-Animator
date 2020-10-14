package cs3500.animator.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import javax.swing.event.ChangeListener;

/**
 * A view interface that allows the user to edit the animation. Supports many operations such as
 * pausing, restarting, adding shapes, removing shapes, and increase/decreasing speed of the
 * animation.
 */
public interface IEditingView extends AnimationView {

  /**
   * Pause and resume the animation.
   */
  void pauseAndResume();

  /**
   * Restart the animation.
   */
  void restart();

  /**
   * Toggle whether or not the animation loops.
   */
  void toggleLooping();

  /**
   * Increase the speed of the animation.
   */
  void increaseSpeed();

  /**
   * Decrease the speed of the animation.
   */
  void decreaseSpeed();

  /**
   * Hook up key listeners to the animation.
   *
   * @param listener the key listener to hook up to the view
   */
  void addKeyListener(KeyListener listener);

  /**
   * Hook up actions listeners to the animation to handle button presses.
   *
   * @param listener the action listener to hook up to the buttons
   */
  void addActionListener(ActionListener listener);

  /**
   * Hook up mouse listeners to handle mouse presses.
   *
   * @param l the mouse listener to hook up to animation
   */
  void addMouseListener(MouseListener l);

  /**
   * Set the a view model object as a field in the view for use in sending data to the JPanel to
   * draw the shapes.
   *
   * @param model the view model object to set as a field in the view
   */
  void setViewModel(IViewModel model);

  /**
   * Get the string from the text field and return it.
   *
   * @return the string the user has typed into the text field
   */
  String getInput();

  /**
   * Clear the text field.
   */
  void clearInput();

  /**
   * Set the text field to a new String.
   *
   * @param input the String to set the text field as
   */
  void setInput(String input);

  /**
   * Maximize the screen.
   */
  void maximize();

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * Sets the current tick of the animation.
   */
  void setCurTick(double curTick);

  /**
   * Set the scrubbers change listener.
   */
  void setChangeListener(ChangeListener listener);
}
