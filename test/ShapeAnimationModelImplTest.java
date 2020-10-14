import static org.junit.Assert.assertEquals;

import cs3500.animation.model.Position2D;
import cs3500.animation.model.Shape;
import cs3500.animation.model.ShapeAnimationModel;
import cs3500.animation.model.ShapeAnimationModelImpl;
import cs3500.animation.model.Size2D;
import cs3500.animator.view.AnimationView;
import cs3500.animator.view.TextView;
import java.awt.Color;
import java.util.LinkedHashMap;
import org.junit.Test;

/**
 * Tests and examples for the ShapeAnimationModelImpl class.
 */
public class ShapeAnimationModelImplTest {

  ShapeAnimationModel twoShapesModel;
  ShapeAnimationModel modelWithGap;
  ShapeAnimationModel mt;
  Shape decagon;
  AnimationView output;

  private void initData() {
    //model with two shapes and no motions
    twoShapesModel = new ShapeAnimationModelImpl();
    twoShapesModel.addShape("R",
        new Shape(new Position2D(200, 200),
            new Color(255, 0, 0),
            new Size2D(50, 100), "rectangle"));
    twoShapesModel.addShape("C",
        new Shape(new Position2D(440, 70),
            new Color(0, 0, 255),
            new Size2D(70, 120), "ellipse"));

    //model with a gap in movement where the shape is "doing nothing"
    Shape pentagon = new Shape(new Position2D(50, 50),
        new Color(60, 60, 60),
        new Size2D(30, 30), "pentagon");
    modelWithGap = new ShapeAnimationModelImpl("pent", pentagon);
    modelWithGap.moveShape("pent", new Position2D(70, 70), 5, 10);
    modelWithGap.changeShapeSize("pent", new Size2D(35, 35), 40, 50);

    //decagon shape
    decagon = new Shape(new Position2D(-3, 3),
        new Color(40, 90, 200), new Size2D(10, 12), "decagon");

    //empty model
    mt = new ShapeAnimationModelImpl();
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorDisallowsNullKey() {
    initData();
    new ShapeAnimationModelImpl(null, decagon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorDisallowsNullShape() {
    new ShapeAnimationModelImpl("F", null);
  }


  @Test
  public void moveShape() {
    initData();
    Appendable o = new StringBuilder();
    twoShapesModel.moveShape("R", new Position2D(-30, 30), 10, 20);
    output = new TextView(twoShapesModel, o);
    output.makeVisible();
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 0 200 200 50 100 255 0 0\t\t10 200 200 50 100 255 0 0\n"
            + "motion R 10 200 200 50 100 255 0 0\t\t20 -30 30 50 100 255 0 0\n"
            + "\n"
            + "shape C ellipse\n"
            + "\n",
        o.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveDisallowsNullInput() {
    initData();
    twoShapesModel.moveShape(null, null, 40, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveDisallowsInvalidFromAndToTick() {
    initData();
    twoShapesModel.moveShape("R", new Position2D(55, 56), 40, -5);
  }

  @Test
  public void changeShapeSize() {
    initData();
    Appendable o = new StringBuilder();
    twoShapesModel.changeShapeSize("R", new Size2D(30, 40), 10, 20);
    output = new TextView(twoShapesModel, o);
    output.makeVisible();
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 0 200 200 50 100 255 0 0\t\t10 200 200 50 100 255 0 0\n"
            + "motion R 10 200 200 50 100 255 0 0\t\t20 200 200 30 40 255 0 0\n"
            + "\n"
            + "shape C ellipse\n"
            + "\n",
        o.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeSizeDisallowsNullInput() {
    initData();
    twoShapesModel.changeShapeSize(null, null, 40, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeSizeDisallowsInvalidFromAndToTick() {
    initData();
    twoShapesModel.changeShapeSize("R", new Size2D(55, 56), 40, -5);
  }

  @Test
  public void changeShapeColor() {
    initData();
    Appendable o = new StringBuilder();
    twoShapesModel.changeShapeColor("R", new Color(60, 65, 70), 10, 20);
    output = new TextView(twoShapesModel, o);
    output.makeVisible();
    assertEquals("canvas 0 0 500 500\n"
            + "shape R rectangle\n"
            + "motion R 0 200 200 50 100 255 0 0\t\t10 200 200 50 100 255 0 0\n"
            + "motion R 10 200 200 50 100 255 0 0\t\t20 200 200 50 100 60 65 70\n"
            + "\n"
            + "shape C ellipse\n"
            + "\n",
        o.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeColorDisallowsNullInput() {
    initData();
    twoShapesModel.changeShapeColor(null, null, 40, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeColorDisallowsInvalidFromAndToTick() {
    initData();
    twoShapesModel.changeShapeColor("R", new Color(5, 100, 56), 40, -5);
  }

  @Test
  public void addShape() {
    initData();
    Appendable o = new StringBuilder();
    output = new TextView(twoShapesModel, o);
    output.makeVisible();
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n\n"
        + "shape C ellipse\n\n", o.toString());

    twoShapesModel.addShape("D", decagon);
    o = new StringBuilder();
    output = new TextView(twoShapesModel, o);
    output.makeVisible();
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n"
        + "\n"
        + "shape C ellipse\n"
        + "\n"
        + "shape D decagon\n"
        + "\n", o.toString());

    mt.addShape("D", decagon);
    o = new StringBuilder();
    output = new TextView(mt, o);
    output.makeVisible();
    assertEquals("canvas 0 0 500 500\n"
        + "shape D decagon\n\n", o.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addShapeDisallowsNullKey() {
    initData();
    mt.addShape(null, decagon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addShapeDisallowsNullShape() {
    initData();
    mt.addShape("F", null);
  }

  @Test
  public void removeShape() {
    initData();
    Appendable o = new StringBuilder();
    twoShapesModel.moveShape("R", new Position2D(400, 300), 1, 10);
    output = new TextView(twoShapesModel, o);
    output.makeVisible();
    assertEquals("canvas 0 0 500 500\n"
        + "shape R rectangle\n"
        + "motion R 0 200 200 50 100 255 0 0\t\t1 200 200 50 100 255 0 0\n"
        + "motion R 1 200 200 50 100 255 0 0\t\t10 400 300 50 100 255 0 0\n\n"
        + "shape C ellipse\n\n", o.toString());

    o = new StringBuilder();
    twoShapesModel.removeShape("R");
    output = new TextView(twoShapesModel, o);
    output.makeVisible();
    assertEquals("canvas 0 0 500 500\n"
        + "shape C ellipse\n\n", o.toString());

    o = new StringBuilder();
    twoShapesModel.removeShape("C");
    output = new TextView(twoShapesModel, o);
    output.makeVisible();
    assertEquals("canvas 0 0 500 500\n", o.toString());

    o = new StringBuilder();
    twoShapesModel.removeShape("notAKey");
    output = new TextView(twoShapesModel, o);
    output.makeVisible();
    assertEquals("canvas 0 0 500 500\n", o.toString());
  }

  @Test
  public void testGetShapes() {
    initData();
    assertEquals(2, twoShapesModel.getShapes().size());
    assertEquals("rectangle", twoShapesModel.getShapes().get("R").toString());
    assertEquals("ellipse", twoShapesModel.getShapes().get("C").toString());
    assertEquals(0, mt.getShapes().size());
    assertEquals(new LinkedHashMap<>(), mt.getShapes());
  }

  @Test
  public void testGetBounds() {
    ShapeAnimationModel model = ShapeAnimationModelImpl.getBuilder().setBounds(1, 2, 3, 4).build();
    assertEquals(1, model.getBounds()[0]);
    assertEquals(2, model.getBounds()[1]);
    assertEquals(3, model.getBounds()[2]);
    assertEquals(4, model.getBounds()[3]);

    //test that bounds are immutable from the getBounds() method
    model.getBounds()[0] = 41324;
    assertEquals(1, model.getBounds()[0]);

    ShapeAnimationModel mt = ShapeAnimationModelImpl.getBuilder().build();
    assertEquals(0, mt.getBounds()[0]);
    assertEquals(0, mt.getBounds()[1]);
    assertEquals(500, mt.getBounds()[2]);
    assertEquals(500, mt.getBounds()[3]);
  }

  @Test
  public void testShapesAtTick() {
    initData();
    assertEquals(new LinkedHashMap<>(), twoShapesModel.shapesAtTick(0));
    assertEquals("pentagon", modelWithGap.shapesAtTick(5).get("pent").toString());
    assertEquals(new Position2D(50, 50), modelWithGap.shapesAtTick(5).get("pent").getPosition());
    assertEquals(new Position2D(54, 54), modelWithGap.shapesAtTick(6).get("pent").getPosition());
    assertEquals(new Position2D(58, 58), modelWithGap.shapesAtTick(7).get("pent").getPosition());
    assertEquals(new Position2D(62, 62), modelWithGap.shapesAtTick(8).get("pent").getPosition());
    assertEquals(new Position2D(66, 66), modelWithGap.shapesAtTick(9).get("pent").getPosition());
    assertEquals(new Position2D(70, 70), modelWithGap.shapesAtTick(10).get("pent").getPosition());
  }
}