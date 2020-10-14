package cs3500.animator.view;

import java.io.FileWriter;
import java.io.IOException;

/**
 * This class abstracts certain functions for the non-visual view implementations, i.e. the SVG
 * view and the text view.
 */
public abstract class TextViewAbstract implements AnimationView {
  IViewModel model;
  Appendable out;

  /**
   * Constructs a TextViewAbstract object.
   * @param model the view-model that is used to give input
   * @param out the output of the view
   */
  public TextViewAbstract(IViewModel model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  /**
   * Appends the given string to the output.
   * @param str the string to be appended
   */
  protected void appendToOut(CharSequence str) {
    try {
      this.out.append(str);
    } catch (IOException ioe) {
      throw new IllegalArgumentException("Append failed!", ioe);
    }
  }

  @Override
  public void refresh() {
    //This method is only relevant in the VisualAnimationView, so here it is overridden
    //to do nothing.
  }

  @Override
  public void setShapesToDraw() {
    //This method is only relevant in the VisualAnimationView, so here it is overridden
    //to do nothing.
  }

  @Override
  public void makeFile(String fileName) {
    this.out = new StringBuilder();
    makeVisible();
    try {
      FileWriter textWriter = new FileWriter(fileName);
      textWriter.write(out.toString());
      textWriter.close();
    } catch (IOException ioe) {
      throw new IllegalStateException("File writing failed.");
    }
  }
}
