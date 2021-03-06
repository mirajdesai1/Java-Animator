import static org.junit.Assert.assertEquals;

import cs3500.animation.model.ShapeAnimationModelImpl;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.IViewModel;
import cs3500.animator.view.TextView;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.Test;

/**
 * Tests and examples for the AnimationReader class.
 */
public class AnimationReaderTest {

  FileReader smallDemo;

  private void initData() {
    try {
      smallDemo = new FileReader("testfiles/smalldemo.txt");
    } catch (FileNotFoundException ignored) {

    }
  }

  @Test
  public void parseFile() {
    initData();
    IViewModel viewmodel = AnimationReader
        .parseFile(smallDemo, ShapeAnimationModelImpl.getBuilder());
    StringBuilder out = new StringBuilder();
    AnimationView view = new TextView(viewmodel, out);
    view.makeVisible();
    assertEquals("canvas 200 70 360 360\n"
            + "shape R rectangle\n"
            + "motion R 1 200 200 50 100 255 0 0\t\t10 200 200 50 100 255 0 0\n"
            + "motion R 10 200 200 50 100 255 0 0\t\t50 300 300 50 100 255 0 0\n"
            + "motion R 50 300 300 50 100 255 0 0\t\t51 300 300 50 100 255 0 0\n"
            + "motion R 51 300 300 50 100 255 0 0\t\t70 300 300 25 100 255 0 0\n"
            + "motion R 70 300 300 25 100 255 0 0\t\t100 200 200 25 100 255 0 0\n"
            + "\n"
            + "shape C ellipse\n"
            + "motion C 6 440 70 120 60 0 0 255\t\t20 440 70 120 60 0 0 255\n"
            + "motion C 20 440 70 120 60 0 0 255\t\t50 440 250 120 60 0 0 255\n"
            + "motion C 50 440 250 120 60 0 0 255\t\t70 440 370 120 60 0 170 85\n"
            + "motion C 70 440 370 120 60 0 170 85\t\t80 440 370 120 60 0 255 0\n"
            + "motion C 80 440 370 120 60 0 255 0\t\t100 440 370 120 60 0 255 0\n\n",
        out.toString());
  }
}