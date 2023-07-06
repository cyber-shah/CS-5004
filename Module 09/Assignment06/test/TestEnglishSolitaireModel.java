import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;
import cs5004.marblesolitaire.view.MarbleSolitaireView;

import cs5004.marblesolitaire.model.hw05.EnglishSolitaireModel;
import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState;
import cs5004.marblesolitaire.view.MarbleSolitaireTextView;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This class tests the EnglishSolitaireModel class.
 */
public class TestEnglishSolitaireModel {

  private MarbleSolitaireModel model;
  private MarbleSolitaireView view;

  @Before
  public void setUp() {
    model = new EnglishSolitaireModel();
    view = new MarbleSolitaireTextView(model);
  }

  @Test
  public void testDefaultConstructor() {
    final MarbleSolitaireView view = new MarbleSolitaireTextView(model);

    // Test board size
    assertEquals(7, model.getBoardSize());

    // Test center slot is empty
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3, 3));
  }

  @Test
  public void testConstructorWithPosition() {
    model = new EnglishSolitaireModel(2, 4);

    // Test board size
    assertEquals(7, model.getBoardSize());

    // Test specified position is empty
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2, 4));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidConstructorWithPosition() {
    model = new EnglishSolitaireModel(2, 6);

    // Test board size
    assertEquals(7, model.getBoardSize());

    // Test specified position is empty
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(0, 0));
  }

  @Test
  public void testConstructorWithArmThicknessAndPosition() {
    model = new EnglishSolitaireModel(5, 1, 5);
    // Test board size
    assertEquals(13, model.getBoardSize());
    // Test specified position is empty
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1, 5));
  }

  @Test
  public void testConstructorWithArmThicknessAndPosition2() {
    model = new EnglishSolitaireModel(3, 4, 4);
    // Test board size
    assertEquals(7, model.getBoardSize());
    // Test specified position is empty
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(4, 4));
  }


  @Test (expected = IllegalArgumentException.class)
  public void testInvalidConstructorWithArmThickness() {
    model = new EnglishSolitaireModel(5, 1, 3);
    // Test board size
    assertEquals(13, model.getBoardSize());
    // Test specified position is empty
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1, 3));
  }

  @Test
  public void testConstructorWithBoardSize() {
    model = new EnglishSolitaireModel(13);

    // Test board size
    assertEquals(13, model.getBoardSize());

    // Test center slot is empty
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(7, 7));
  }

  @Test
  public void testMoveValid() {
    model.move(1, 3, 3, 3);

    // Test moved marble
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3, 3));

    // Test middle slot is empty
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidOutOfBounds() {
    model.move(0, 0, -1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidNotTwoSpacesAway() {
    model.move(1, 3, 3, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidFromSlotEmpty() {
    model.move(3, 3, 4, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidToSlotNotEmpty() {
    model.move(1, 3, 3, 3);
    model.move(3, 1, 3, 3);
  }

  {
    model = new EnglishSolitaireModel(2, 4);

    // Test board size
    assertEquals(7, model.getBoardSize());

    // Test specified position is empty
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2, 4));
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMoveInvalidNoMarbleInBetween() {
    MarbleSolitaireModel model1 = new EnglishSolitaireModel(2, 4);
    MarbleSolitaireView view1 = new MarbleSolitaireTextView(model1);

    model.move(1, 3, 3, 3);
    // invalid move
    model.move(0, 3, 2, 3);
  }

  @Test
  public void testIsGameOverFalse() {
    assertFalse(model.isGameOver());
  }

  @Test
  public void testGetBoardSize() {
    assertEquals(7, model.getBoardSize());
  }

  @Test
  public void testGetSlotAtValid() {
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetSlotAtInvalid() {
    model.getSlotAt(-1, 0);
  }

  @Test
  public void testGetScore() {
    assertEquals(32, model.getScore());
    model.move(3, 1, 3, 3);
    assertEquals(31, model.getScore());
  }

  @Test
  public void testToString() {
    model = new EnglishSolitaireModel(4, 4);
    model.move(4, 2, 4, 4);
    view = new MarbleSolitaireTextView(model);
    assertEquals(view.toString(), "    O O O\n" +
            "    O O O\n" +
            "O O O O O O O\n" +
            "O _ _ O O O O\n" +
            "O O O O O O O\n" +
            "    O O O\n" +
            "    O O O");

    MarbleSolitaireModel model1 = new EnglishSolitaireModel(3, 5);
    MarbleSolitaireView view1 = new MarbleSolitaireTextView(model1);
    assertEquals(view1.toString(),
              "    O O O\n" +
                    "    O O O\n" +
                    "O O O O _ O O\n" +
                    "O O O O O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O");

    MarbleSolitaireModel model2 = new EnglishSolitaireModel(13);
    MarbleSolitaireView view2 = new MarbleSolitaireTextView(model2);
    assertEquals(view2.toString(),
                "        O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "O O O O O O _ O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O");

    MarbleSolitaireModel model3 = new EnglishSolitaireModel(5, 5, 5);
    MarbleSolitaireView view3 = new MarbleSolitaireTextView(model3);
    assertEquals(view3.toString(),
                      "        O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O\n"
                    + "O O O O _ O O O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "O O O O O O O O O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O\n"
                    + "        O O O O O");

  }
}
