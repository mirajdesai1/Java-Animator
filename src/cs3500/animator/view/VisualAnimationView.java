package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * This is an implementation of the AnimationView interface that uses Java Swing to draw the results
 * of the animation. Shapes will be drawn at each tick and the tick rate is also adjustable.
 */
public class VisualAnimationView extends JFrame implements AnimationView {

  private final AnimationPanel animationPanel;
  private final IViewModel model;
  private double curTick;
  private final double tickRate;

  /**
   * Constructs a visual animation of the given model.
   *
   * @param model    an IViewModel object that gives information to the view
   * @param tickRate the tick rate of the animation
   */
  public VisualAnimationView(IViewModel model, double tickRate) {
    super();
    this.model = model;
    this.tickRate = tickRate;
    this.curTick = 0;
    Timer timer = new Timer();

    int[] bounds = model.getBounds();
    this.setPreferredSize(new Dimension(bounds[2], bounds[3]));
    this.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    animationPanel = new AnimationPanel();
    animationPanel.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
    animationPanel.setPreferredSize(new Dimension(bounds[2], bounds[3]));
    this.add(animationPanel, BorderLayout.CENTER);

    JScrollPane scrollPane = new JScrollPane(animationPanel);
    this.add(scrollPane);

    long rate = (long) (1000 / tickRate);
    timer.scheduleAtFixedRate(new RefreshScreen(), 1000, rate);
    this.pack();

    this.makeVisible();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    curTick += tickRate;
    this.repaint();
  }

  @Override
  public void setShapesToDraw() {
    this.animationPanel.setShapesToDraw(model.shapesAtTick(curTick).values());
  }

  @Override
  public void makeFile(String fileName) {
    //This method is only relevant to the other views, so here it is overridden to do nothing.
  }

  private class RefreshScreen extends TimerTask {

    @Override
    public void run() {
      VisualAnimationView.this.setShapesToDraw();
      VisualAnimationView.this.refresh();
    }
  }
}
