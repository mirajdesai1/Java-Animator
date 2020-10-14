package cs3500.animator.controller;

import cs3500.animation.model.Position2D;
import cs3500.animation.model.Shape;
import cs3500.animation.model.ShapeAnimationModel;
import cs3500.animation.model.Size2D;
import cs3500.animator.view.IEditingView;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Handles all keyboard, button and mouse inputs. Retrieves data from the model and sends data to
 * the view to play an animation.
 */
public class AnimationController {

  ShapeAnimationModel model;
  IEditingView view;

  /**
   * Constructs a controller to receive data from the model and send data to the view.
   *
   * @param model the model to retrieve data from
   * @param view  the animation player to send data to
   */
  public AnimationController(ShapeAnimationModel model, IEditingView view) {
    this.view = view;
    this.model = model;

    this.view.setViewModel(model);
  }

  /**
   * Configure all of the key board, mouse and button for the animation and play it.
   */
  public void execute() {
    //create and set the keyboard listener
    configureKeyBoardListener();
    configureButtonListener();
    configureMouseListener();
    view.setChangeListener(new ScrubberListener());

    view.makeVisible();
  }

  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyTypes.put(' ', () -> view.pauseAndResume());
    keyReleases.put(KeyEvent.VK_LEFT, () -> view.decreaseSpeed());
    keyReleases.put(KeyEvent.VK_RIGHT, () -> view.increaseSpeed());
    keyTypes.put('r', () -> view.restart());
    keyTypes.put('l', () -> view.toggleLooping());

    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    view.addKeyListener(kbd);
  }

  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<>();
    ButtonListener buttonListener = new ButtonListener();
    ArrayList<String> templates = new ArrayList<>(
        Arrays.asList("", "Click these buttons for a template",
            "name", "name type", "name tick",
            "name x y r g b width height tick"));

    buttonClickedMap.put("Pause and Resume", () -> {
      view.resetFocus();
      view.pauseAndResume();
    });
    buttonClickedMap.put("Speed up", () -> {
      view.resetFocus();
      view.increaseSpeed();
    });
    buttonClickedMap.put("Slow down", () -> {
      view.resetFocus();
      view.decreaseSpeed();
    });

    buttonClickedMap.put("Restart Button", () -> {
      view.restart();
      view.resetFocus();
    });
    buttonClickedMap.put("Toggle Looping", () -> {
      view.resetFocus();
      view.toggleLooping();
    });
    buttonClickedMap.put("Remove Shape", () -> {
      view.resetFocus();
      if (templates.contains(view.getInput())) {
        view.setInput(templates.get(2));
      } else {
        model.removeShape(view.getInput());
        view.clearInput();
        view.refresh();
      }
    });
    buttonClickedMap.put("Add Shape", () -> {
      view.resetFocus();
      if (templates.contains(view.getInput())) {
        view.setInput(templates.get(3));
      } else {
        Scanner scan = new Scanner(view.getInput());
        model.addShape(scan.next(), new Shape(scan.next()));
        view.clearInput();
        view.refresh();
      }
    });
    buttonClickedMap.put("Add Keyframe", () -> {
      view.resetFocus();
      if (templates.contains(view.getInput())) {
        view.setInput(templates.get(5));
      } else {
        Scanner scan = new Scanner(view.getInput());
        Shape s = model.getShapes().get(scan.next());
        s.addFrame(new Position2D(scan.nextDouble(), scan.nextDouble()),
            new Color(scan.nextInt(), scan.nextInt(), scan.nextInt()),
            new Size2D(scan.nextInt(), scan.nextInt()), scan.nextInt());
        view.clearInput();
        view.refresh();
      }
    });
    buttonClickedMap.put("Delete Keyframe", () -> {
      view.resetFocus();
      if (templates.contains(view.getInput())) {
        view.setInput(templates.get(4));
      } else {
        Scanner scan = new Scanner(view.getInput());
        Shape s = model.getShapes().get(scan.next());
        s.removeShapeState(scan.nextInt());
        view.clearInput();
        view.refresh();
      }
    });

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  private void configureMouseListener() {
    Map<Integer, Runnable> mouseReleasedMap = new HashMap<>();

    mouseReleasedMap.put(MouseEvent.BUTTON1, () -> view.pauseAndResume());
    mouseReleasedMap.put(MouseEvent.BUTTON3, () -> view.maximize());

    MouseActionListener ml = new MouseActionListener();
    ml.setMouseReleasedMap(mouseReleasedMap);

    view.addMouseListener(ml);
  }

  /**
   * A change listener for the scrubber implemented in the editing view.
   */
  public class ScrubberListener implements ChangeListener {

    @Override
    public void stateChanged(ChangeEvent e) {
      JSlider scrubber = (JSlider) e.getSource();
      view.setCurTick(scrubber.getValue());
      view.resetFocus();
      view.refresh();
    }
  }


}
