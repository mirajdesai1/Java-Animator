package cs3500.animator.view;

import cs3500.animation.model.Shape;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JPanel;

/**
 * This panel represents the region where the shapes of the animation must be drawn.
 */
public class AnimationPanel extends JPanel {

  List<Shape> shapes;

  /**
   * Creates a default animation panel to be shown to the user.
   */
  public AnimationPanel() {
    super();
    this.shapes = new ArrayList<>();
    this.setBackground(Color.WHITE);
  }

  /**
   * Set the list of shapes to draw in the animation.
   *
   * @param shapes the shapes to draw in the animation
   */
  public void setShapesToDraw(Collection<Shape> shapes) {
    this.shapes = new ArrayList<>(shapes);
  }

  private void drawShape(Shape s, Graphics2D g2d) {
    if (s.toString().equals("rectangle")) {
      g2d.setColor(s.getC());
      g2d.fillRect((int) s.getPosition().getX(), (int) s.getPosition().getY(),
          s.getSize().getWidth(), s.getSize().getHeight());
    } else if (s.toString().equals("ellipse")) {
      g2d.setColor(s.getC());
      g2d.fillOval((int) s.getPosition().getX(), (int) s.getPosition().getY(),
          s.getSize().getWidth(), s.getSize().getHeight());
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    AffineTransform originalTransform = g2d.getTransform();

    for (Shape s : this.shapes) {
      drawShape(s, g2d);
    }

    g2d.setTransform(originalTransform);
  }
}
