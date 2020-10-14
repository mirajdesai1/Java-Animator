package cs3500.animator.view;

import cs3500.animation.model.Shape;
import cs3500.animation.model.ShapeState;
import java.util.List;
import java.util.Map;

/**
 * A text representation of an animation, listing each shape and its respective motions and key
 * frames.
 */
public class TextView extends TextViewAbstract implements AnimationView {

  /**
   * Constructs a text view animation with the given model and output source.
   * @param model the IViewModel to construct the text view with
   * @param out the output source to relay to the user
   */
  public TextView(IViewModel model, Appendable out) {
    super(model, out);
  }


  @Override
  public void makeVisible() {
    StringBuilder canvas = new StringBuilder("canvas");
    for (int i : model.getBounds()) {
      canvas.append(" ").append(i);
    }
    canvas.append("\n");

    Map<String, Shape> shapes = model.getShapes();
    StringBuilder result = new StringBuilder();
    for (String key : shapes.keySet()) {
      Shape s = shapes.get(key);
      List<ShapeState> history = s.getHistory();
      result.append("shape ").append(key).append(" ").append(s.toString()).append("\n");

      for (int i = 0; i < history.size() - 1; i++) {
        result.append("motion ").append(key).append(" ");
        result.append(history.get(i).toString()).append("\t\t")
            .append(history.get(i + 1).toString()).append("\n");
      }
      result.append("\n");
    }
    canvas.append(result);

    appendToOut(canvas);
  }
}
