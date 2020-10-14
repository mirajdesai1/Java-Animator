package cs3500.animator.view;

import cs3500.animation.model.Shape;
import cs3500.animation.model.ShapeState;
import java.util.List;
import java.util.Map;

/**
 * This class represents a SVG animation view and writes the information from its model in SVG file
 * format. It also contains a method to print the output as a SVG file.
 */
public class SVGAnimationView extends TextViewAbstract implements AnimationView {

  double tickRate;

  /**
   * Constructs an SVGAnimationView with the given model and output source with a default tickrate
   * of 1.
   *
   * @param model the animation model to create the svg animation with
   * @param out   the output source
   */
  public SVGAnimationView(IViewModel model, Appendable out) {
    super(model, out);
    this.tickRate = 1;
  }

  /**
   * Constructs an SVGAnimationView with the given model and output source at the given tickrate.
   *
   * @param model    the animation model to create the svg animation with
   * @param out      the output source
   * @param tickRate the tick rate of the svg animation
   */
  public SVGAnimationView(IViewModel model, Appendable out, double tickRate) {
    super(model, out);
    this.tickRate = tickRate;
  }

  /**
   * Creates a shape in SVG format with the visibility set to hidden.
   *
   * @param key the variable name of the shape
   * @param s   the shape
   * @return the shape's parameters in SVG tag format
   */
  private String svgShape(String key, Shape s) {
    ShapeState init;
    if (s.getHistory().size() > 0) {
      init = s.getHistory().get(0);
    } else {
      init = new ShapeState(s.getPosition(), s.getC(), s.getSize(), s.getMaxTick());
    }
    String[] parameters = new String[4];
    StringBuilder builder = new StringBuilder();

    if (s.toString().equals("rectangle")) {
      builder.append("<rect");
      parameters = new String[]{"x", "y", "width", "height"};
    } else if (s.toString().equals("ellipse")) {
      builder.append("<ellipse");
      parameters = new String[]{"cx", "cy", "rx", "ry"};
    }

    builder.append(" id=\"").append(key).append("\" ").append(parameters[0]).append("=\"")
        .append((int) init.getPosition().getX()).append("\" ").append(parameters[1]).append("=\"")
        .append((int) init.getPosition().getY()).append("\" " + parameters[2] + "=\"")
        .append(init.getSize().getWidth()).append("\" " + parameters[3] + "=\"")
        .append(init.getSize().getHeight())
        .append("\" fill=" + String
            .format("\"rgb(%d,%d,%d)\"", init.getColor().getRed(), init.getColor().getGreen(),
                init.getColor().getBlue())).append(" visibility=\"hidden\" >");

    return builder.toString();
  }

  /**
   * Determines when a shape should show itself on screen and sets the visibility to visible in the
   * SVG format.
   *
   * @param s the shape being shown on screen
   * @return the SVG code that allows a shape to become visible
   */
  private String svgShow(Shape s) {
    ShapeState init;
    if (s.getHistory().size() > 0) {
      init = s.getHistory().get(0);
    } else {
      init = new ShapeState(s.getPosition(), s.getC(), s.getSize(), s.getMaxTick());
    }
    StringBuilder builder = new StringBuilder();
    builder.append("<set attributeName=\"visibility\" to=\"visible\" begin=\"")
        .append(init.getAtTick() * 1000 / tickRate).append("ms\" />");
    return builder.toString();
  }

  /**
   * A factory method that makes SVG animation tags.
   *
   * @param begin   the tick at which the animation begins
   * @param dur     the duration of the animation
   * @param attName the name of the attribute that changes
   * @param from    the initial state of the attribute
   * @param to      the final state of the attribute
   * @return the change described in the parameters, formatted in SVG format
   */
  private String svgAnimateTemplate(int begin, int dur, String attName, String from, String to) {
    StringBuilder builder = new StringBuilder();
    builder.append("<animate attributeType=\"xml\" begin=\"").append(begin * 1000 / tickRate)
        .append("ms\" dur=\"").append(dur * 1000 / tickRate)
        .append("ms\" attributeName=\"" + attName + "\" ").append("from=\"")
        .append(from).append("\" to=\"")
        .append(to).append("\" fill=\"freeze\" />");
    return builder.toString();
  }

  /**
   * Creates a SVG animation tag by determining what changes during the specific motion.
   *
   * @param prev the first shape state
   * @param next the second shape state
   * @param type the type of shape in the shape states
   * @return a SVG animation tag
   */
  private String svgAnimate(ShapeState prev, ShapeState next, String type) {
    StringBuilder builder = new StringBuilder();
    String[] parameters = new String[4];
    int dur = next.getAtTick() - prev.getAtTick();
    if (type.equals("rectangle")) {
      parameters = new String[]{"x", "y", "width", "height"};
    } else if (type.equals("ellipse")) {
      parameters = new String[]{"cx", "cy", "rx", "ry"};
    }

    if (!prev.getPosition().equals(next.getPosition())) {
      builder.append(
          svgAnimateTemplate(prev.getAtTick(), dur, parameters[0],
              (int) prev.getPosition().getX() + "",
              (int) next.getPosition().getX() + ""));
      builder.append("\n");
      builder.append(
          svgAnimateTemplate(prev.getAtTick(), dur, parameters[1],
              (int) prev.getPosition().getY() + "",
              (int) next.getPosition().getY() + ""));
    } else if (!prev.getSize().equals(next.getSize())) {
      builder.append(
          svgAnimateTemplate(prev.getAtTick(), dur, parameters[2], prev.getSize().getWidth() + "",
              next.getSize().getWidth() + ""));
      builder.append("\n");
      builder.append(
          svgAnimateTemplate(prev.getAtTick(), dur, parameters[3], prev.getSize().getHeight() + "",
              next.getSize().getHeight() + ""));
    } else {
      builder.append(
          svgAnimateTemplate(prev.getAtTick(), dur, "fill",
              String.format("rgb(%d,%d,%d)", prev.getColor().getRed(),
                  prev.getColor().getGreen(), prev.getColor().getBlue()),
              String.format("rgb(%d,%d,%d)", next.getColor().getRed(),
                  next.getColor().getGreen(), next.getColor().getBlue())));
    }
    return builder.toString();
  }

  @Override
  public void makeVisible() {
    StringBuilder svg = new StringBuilder("<svg width=\"");
    svg.append(model.getBounds()[2]).append("\" ");
    svg.append("height=\"").append(model.getBounds()[3]).append("\" ");
    svg.append("version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n");

    Map<String, Shape> shapes = model.getShapes();
    for (String key : shapes.keySet()) {
      Shape s = shapes.get(key);
      List<ShapeState> ss = s.getHistory();
      svg.append(svgShape(key, s)).append("\n");
      svg.append(svgShow(s)).append("\n");

      for (int i = 0; i < ss.size() - 1; i++) {
        ShapeState prev = ss.get(i);
        ShapeState next = ss.get(i + 1);
        svg.append(svgAnimate(prev, next, s.toString())).append("\n");
      }
      if (s.toString().equals("rectangle")) {
        svg.append("</rect>\n");
      } else if (s.toString().equals("ellipse")) {
        svg.append("</ellipse>\n");
      }
    }
    svg.append("</svg>");
    appendToOut(svg);
  }


}
