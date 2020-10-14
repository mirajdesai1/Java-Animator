import static org.junit.Assert.assertEquals;

import cs3500.animation.model.ShapeAnimationModelImpl;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.IViewModel;
import cs3500.animator.view.SVGAnimationView;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.junit.Test;

/**
 * Tests and examples for the SVGAnimationView class.
 */
public class SVGAnimationViewTest {

  Readable smallDemo;

  private void initData() {
    try {
      smallDemo = new FileReader("testfiles/smalldemo.txt");
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("bad file");
    }
  }

  @Test
  public void testMakeVisible() {
    initData();
    IViewModel model = AnimationReader.parseFile(smallDemo, ShapeAnimationModelImpl.getBuilder());
    Appendable output = new StringBuilder();
    AnimationView view = new SVGAnimationView(model, output);
    view.makeVisible();
    assertEquals("<svg width=\"360\" height=\"360\" version=\"1.1\" "
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" fill=\"rgb(255,0,0)\" "
        + "visibility=\"hidden\" >\n"
        + "<set attributeName=\"visibility\" to=\"visible\" begin=\"1000.0ms\" />\n"
        + "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"9000.0ms\" "
        + "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"10000.0ms\" dur=\"40000.0ms\" "
        + "attributeName=\"x\" from=\"200\" to=\"300\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"10000.0ms\" dur=\"40000.0ms\" "
        + "attributeName=\"y\" from=\"200\" to=\"300\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"50000.0ms\" dur=\"1000.0ms\" "
        + "attributeName=\"fill\" from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"51000.0ms\" dur=\"19000.0ms\" "
        + "attributeName=\"width\" from=\"50\" to=\"25\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"51000.0ms\" dur=\"19000.0ms\" "
        + "attributeName=\"height\" from=\"100\" to=\"100\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"70000.0ms\" dur=\"30000.0ms\" "
        + "attributeName=\"x\" from=\"300\" to=\"200\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"70000.0ms\" dur=\"30000.0ms\" "
        + "attributeName=\"y\" from=\"300\" to=\"200\" fill=\"freeze\" />\n"
        + "</rect>\n"
        + "<ellipse id=\"C\" cx=\"440\" cy=\"70\" rx=\"120\" ry=\"60\" fill=\"rgb(0,0,255)\" "
        + "visibility=\"hidden\" >\n"
        + "<set attributeName=\"visibility\" to=\"visible\" begin=\"6000.0ms\" />\n"
        + "<animate attributeType=\"xml\" begin=\"6000.0ms\" dur=\"14000.0ms\" "
        + "attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,0,255)\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"20000.0ms\" dur=\"30000.0ms\" "
        + "attributeName=\"cx\" from=\"440\" to=\"440\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"20000.0ms\" dur=\"30000.0ms\" "
        + "attributeName=\"cy\" from=\"70\" to=\"250\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"50000.0ms\" dur=\"20000.0ms\" "
        + "attributeName=\"cx\" from=\"440\" to=\"440\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"50000.0ms\" dur=\"20000.0ms\" "
        + "attributeName=\"cy\" from=\"250\" to=\"370\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"70000.0ms\" dur=\"10000.0ms\" "
        + "attributeName=\"fill\" from=\"rgb(0,170,85)\" to=\"rgb(0,255,0)\" fill=\"freeze\" />\n"
        + "<animate attributeType=\"xml\" begin=\"80000.0ms\" dur=\"20000.0ms\" "
        + "attributeName=\"fill\" from=\"rgb(0,255,0)\" to=\"rgb(0,255,0)\" fill=\"freeze\" />\n"
        + "</ellipse>\n"
        + "</svg>", output.toString());
  }

  @Test
  public void testMakeVisibleEmptyFile() {
    try {
      smallDemo = new FileReader("testfiles/emptyfile.txt");
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("bad file");
    }
    IViewModel model = AnimationReader.parseFile(smallDemo, ShapeAnimationModelImpl.getBuilder());
    Appendable output = new StringBuilder();
    AnimationView view = new SVGAnimationView(model, output);
    view.makeVisible();
    assertEquals("<svg width=\"500\" height=\"500\" version=\"1.1\" "
        + "xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "</svg>", output.toString());
  }

  @Test
  public void testMakeVisibleNoMotions() {
    try {
      smallDemo = new FileReader("testfiles/nomotions.txt");
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("bad file");
    }
    IViewModel model = AnimationReader.parseFile(smallDemo, ShapeAnimationModelImpl.getBuilder());
    Appendable output = new StringBuilder();
    AnimationView view = new SVGAnimationView(model, output);
    view.makeVisible();
    assertEquals(
        "<svg width=\"360\" height=\"360\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n"
            + "<rect id=\"R\" x=\"0\" y=\"0\" width=\"10\" height=\"10\" fill=\"rgb(0,0,0)\" "
            + "visibility=\"hidden\" >\n"
            + "<set attributeName=\"visibility\" to=\"visible\" begin=\"0.0ms\" />\n"
            + "</rect>\n"
            + "<ellipse id=\"C\" cx=\"0\" cy=\"0\" rx=\"10\" ry=\"10\" fill=\"rgb(0,0,0)\" "
            + "visibility=\"hidden\" >\n"
            + "<set attributeName=\"visibility\" to=\"visible\" begin=\"0.0ms\" />\n"
            + "</ellipse>\n"
            + "</svg>", output.toString());
  }
}
