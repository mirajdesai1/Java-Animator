package cs3500.animator;

import cs3500.animation.model.ShapeAnimationModel;
import cs3500.animation.model.ShapeAnimationModelImpl;
import cs3500.animator.controller.AnimationController;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.EditingView;
import cs3500.animator.view.SVGAnimationView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.VisualAnimationView;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.JOptionPane;

/**
 * Play an animation from start to finish. Pass in arguments to the main method to initialize and
 * play an animation. The user is also able to switch between different visualizations and inputs.
 */
public final class Excellence {

  private static ShapeAnimationModel createModel(String in) {
    Readable file = null;
    try {
      file = new FileReader(in);
    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(null, "please input a valid file name");
    }
    return AnimationReader.parseFile(file, ShapeAnimationModelImpl.getBuilder());
  }

  private static void runView(String view, String in, String out, int speed) {
    ShapeAnimationModel model = createModel(in);

    switch (view) {
      case "visual":
        new VisualAnimationView(model, speed);
        break;
      case "svg":
        if (out.equals("out")) {
          new SVGAnimationView(model, System.out, speed).makeVisible();
        } else {
          new SVGAnimationView(model, new StringBuilder(), speed).makeFile(out);
        }
        break;
      case "text":
        if (out.equals("out")) {
          new TextView(model, System.out).makeVisible();
        } else {
          new TextView(model, new StringBuilder()).makeFile(out);
        }
        break;
      case "edit":
        new AnimationController(model, new EditingView(speed)).execute();
        break;
      default:
        JOptionPane.showMessageDialog(null, "you have not inputted a supported view!");
    }
  }


  /**
   * Play an animation by passing in an array of commands. '-view' followed by 'visual,' 'svg,' or
   * 'text' dictates what visualization is shown. '-in' followed by a file name creates an animation
   * with the contents of the file. '-out' followed by 'out' or some file name dictates where the
   * contents of your animation are sent to. The default is System.out. '-speed' followed by some
   * positive integer dictates the speed of the animation. The default is 1.
   *
   * @param args an array of commands that will dictate what kind of animation is played.
   */
  public static void main(String[] args) {
    String in = "";
    String view = "";
    String out = "out";
    int speed = 1;

    if (args.length < 2) {
      JOptionPane.showMessageDialog(null, "you must at least input a file name and a view");
      return;
    }

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-in":
          in = args[i + 1];
          i++;
          break;
        case "-view":
          view = args[i + 1];
          i++;
          break;
        case "-out":
          out = args[i + 1];
          i++;
          break;
        case "-speed":
          speed = Integer.parseInt(args[i + 1]);
          i++;
          break;
        default:
          JOptionPane.showMessageDialog(null, "please input a valid command!");
          return;
      }
    }

    runView(view, in, out, speed);


  }
}