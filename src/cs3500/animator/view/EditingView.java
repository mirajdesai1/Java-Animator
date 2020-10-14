package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;


/**
 * An editing animation view using a border layout with buttons on the tops and bottoms of the
 * screen. Supports operations such as pausing, restarting, maximizing the view, speeding up and
 * slowing down the animation and toggling looping.
 */
public class EditingView extends JFrame implements IEditingView {

  private final AnimationPanel animationPanel;
  private IViewModel model;
  private double curTick;
  private double tickRate;
  private Timer timer;
  private boolean isLooping;
  private boolean isPaused;
  private int maxTick;
  private final JButton pauseAndResume;
  private final JButton faster;
  private final JButton slower;
  private final JButton loop;
  private final JButton restart;
  private final JButton removeShape;
  private final JButton addShape;
  private final JButton deleteKeyFrame;
  private final JButton addKeyFrame;
  private final JTextField input;
  private final JLabel tickLabel;
  private JSlider scrubber;

  /**
   * Constructs an editing animation view using a border layout with button panels on the top and
   * bottom of the screen.
   *
   * @param tickRate tick rate of the animation
   */
  public EditingView(double tickRate) {
    this.model = null;
    this.timer = new Timer();
    this.tickRate = tickRate;
    this.curTick = 0;
    this.isPaused = true;
    this.maxTick = 0;
    this.isLooping = false;

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    animationPanel = new AnimationPanel();
    this.add(animationPanel, BorderLayout.CENTER);

    JScrollPane scrollPane = new JScrollPane(animationPanel);
    this.add(scrollPane);

    JPanel buttonPanelBottom = new JPanel();
    buttonPanelBottom.setLayout(new FlowLayout());
    this.add(buttonPanelBottom, BorderLayout.SOUTH);

    //play button
    pauseAndResume = new JButton("Play");
    pauseAndResume.setActionCommand("Pause and Resume");
    buttonPanelBottom.add(pauseAndResume);

    //speed buttons
    faster = new JButton("+");
    faster.setActionCommand("Speed up");
    slower = new JButton("-");
    slower.setActionCommand("Slow down");
    buttonPanelBottom.add(slower);
    buttonPanelBottom.add(faster);

    //loop button
    loop = new JButton("Enable Looping");
    loop.setActionCommand("Toggle Looping");
    buttonPanelBottom.add(loop);

    //restart button
    restart = new JButton("Restart");
    restart.setActionCommand("Restart Button");
    buttonPanelBottom.add(restart);

    //tick label
    tickLabel = new JLabel("" + curTick);
    buttonPanelBottom.add(tickLabel);

    //top button panel
    JPanel buttonPanelTop = new JPanel();
    buttonPanelTop.setLayout(new FlowLayout());
    this.add(buttonPanelTop, BorderLayout.NORTH);

    //remove shape button
    removeShape = new JButton("Remove Shape");
    removeShape.setActionCommand("Remove Shape");
    buttonPanelTop.add(removeShape);

    //add shape button
    addShape = new JButton("Add Shape");
    addShape.setActionCommand("Add Shape");
    buttonPanelTop.add(addShape);

    //add text field
    input = new JTextField("Click these buttons for a template", 25);
    buttonPanelTop.add(input);

    //add keyframe button
    addKeyFrame = new JButton("Add/Edit Keyframe");
    addKeyFrame.setActionCommand("Add Keyframe");
    buttonPanelTop.add(addKeyFrame);

    //delete keyframe button
    deleteKeyFrame = new JButton("Delete Keyframe");
    deleteKeyFrame.setActionCommand("Delete Keyframe");
    buttonPanelTop.add(deleteKeyFrame);

    resetFocus();
    this.pack();
  }


  @Override
  public void pauseAndResume() {
    if (isPaused) {
      this.pauseAndResume.setText("Pause");
      long rate = (long) ((double) 1000 / tickRate);
      this.timer.cancel();
      this.timer = new Timer();
      this.timer.scheduleAtFixedRate(new RefreshScreen(), 0, Math.abs(rate));
      this.isPaused = false;
      return;
    }
    this.pauseAndResume.setText("Resume");
    this.timer.cancel();
    this.isPaused = true;
  }

  @Override
  public void restart() {
    this.curTick = 0;
    this.timer.cancel();
    this.isPaused = true;
    this.pauseAndResume();
  }

  @Override
  public void toggleLooping() {
    this.isLooping = !isLooping;

    if (isLooping) {
      this.loop.setText("Looping");
    } else {
      this.loop.setText("Not Looping");
    }
  }

  @Override
  public void increaseSpeed() {
    this.tickRate = this.tickRate + 1;

    if (!isPaused) {
      this.timer.cancel();
      long rate = (long) ((double) 1000 / tickRate);
      this.timer = new Timer();
      this.timer.scheduleAtFixedRate(new RefreshScreen(), 0, Math.abs(rate));
    }
  }

  @Override
  public void decreaseSpeed() {
    if (this.tickRate > 1) {
      this.tickRate = this.tickRate - 1;

      if (!isPaused) {
        this.timer.cancel();
        long rate = (long) ((double) 1000 / tickRate);
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new RefreshScreen(), 0, Math.abs(rate));
      }
    }
  }

  @Override
  public void addActionListener(ActionListener listener) {
    pauseAndResume.addActionListener(listener);
    faster.addActionListener(listener);
    slower.addActionListener(listener);
    restart.addActionListener(listener);
    loop.addActionListener(listener);
    removeShape.addActionListener(listener);
    addShape.addActionListener(listener);
    addKeyFrame.addActionListener(listener);
    deleteKeyFrame.addActionListener(listener);
  }

  @Override
  public void addMouseListener(MouseListener l) {
    animationPanel.addMouseListener(l);
  }

  @Override
  public void setViewModel(IViewModel model) {
    this.model = model;

    int[] bounds = model.getBounds();
    this.setPreferredSize(new Dimension(bounds[2], bounds[3]));
    this.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);

    animationPanel.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
    animationPanel.setPreferredSize(new Dimension(bounds[2], bounds[3]));

    this.maxTick = model.getMaxTick();

    //implementing scrubber
    scrubber = new JSlider(JSlider.VERTICAL, 0, maxTick, 0);
    scrubber.setMajorTickSpacing(5);
    scrubber.setPaintTicks(true);
    this.add(scrubber, BorderLayout.EAST);

  }

  @Override
  public String getInput() {
    return input.getText();
  }

  @Override
  public void clearInput() {
    input.setText("");
  }

  @Override
  public void setInput(String input) {
    this.input.setText(input);
  }

  @Override
  public void maximize() {
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.setShapesToDraw();
    tickLabel.setText("" + curTick);
    this.repaint();
  }

  @Override
  public void setShapesToDraw() {
    animationPanel.setShapesToDraw(model.shapesAtTick(curTick).values());
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void setCurTick(double curTick) {
    this.curTick = curTick;
  }

  @Override
  public void setChangeListener(ChangeListener listener) {
    scrubber.addChangeListener(listener);
  }

  @Override
  public void makeFile(String fileName) {
    throw new UnsupportedOperationException();
  }

  private class RefreshScreen extends TimerTask {

    @Override
    public void run() {
      if (isLooping && curTick > maxTick) {
        curTick = 0;
      }
      curTick += tickRate;
      scrubber.setValue((int)curTick);
      EditingView.this.refresh();
    }
  }
}
