package cs5004.marblesolitaire.model.hw05;

import java.util.HashMap;

public class EuropeanSolitaireModel extends AbstractRectangularModel {

  /**
   * Constructor for EnglishSolitaireModel.
   * Creates a 7 sized board with the empty slot in the centre.
   */
  public EuropeanSolitaireModel() {
    this(3, 3);
  }

  /**
   * Constructor for EnglishSolitaireModel.
   * Creates a 7 sized board with the empty slot at the given position.
   *
   * @param row row for empty slot.
   * @param col column for empty slot.
   * @throws IllegalArgumentException if the given position is invalid.
   */
  public EuropeanSolitaireModel(int row, int col) throws IllegalArgumentException {
    this(3, row, col);
  }

  /**
   * Constructor for EnglishSolitaireModel.
   * Creates a board with the given boardSize and empty slot in the centre.
   *
   * @param armThickness size of the board.
   * @throws IllegalArgumentException if the given boardSize is invalid.
   */
  public EuropeanSolitaireModel(int armThickness) throws IllegalArgumentException {
    this (armThickness, (armThickness + (armThickness - 1) * 2) / 2,
            (armThickness + (armThickness - 1) * 2) / 2);
  }

  /**
   * Constructor for EnglishSolitaireModel.
   * Creates a board with the given arm thickness and empty slot in the input.
   *
   * @param armThickness arm thickness of the board.
   * @param row row for empty slot.
   * @param col column for empty slot.
   * @throws IllegalArgumentException if the given position is invalid.
   */
  public EuropeanSolitaireModel(int armThickness, int row, int col) throws IllegalArgumentException {
    super();

    // check if armThickness is odd
    if (armThickness % 2 == 0) {
      throw new IllegalArgumentException("Arm thickness must be an odd number");
    }

    // assign values to fields
    this.board = new HashMap<>();
    int sideRectangle = (armThickness - 1);
    this.boardSize = armThickness + sideRectangle * 2;


    for (int i = 0; i <= boardSize - 1; i++) {
      for (int j = 0; j <= boardSize - 1; j++) {
        // top rectangle
        if (i < sideRectangle) {
          if (j < sideRectangle - i || j > (sideRectangle) * 2 + i) {
            board.put(i + "," + j, SlotState.Invalid);
          } else {
            board.put(i + "," + j, SlotState.Marble);
          }
        }

        // bottom rectangle
        else if (i > (sideRectangle * 2)) {
          if (j < i - (sideRectangle * 2) || j > (boardSize - (i - (sideRectangle * 2)) -1)) {
            board.put(i + "," + j, SlotState.Invalid);
          } else {
            board.put(i + "," + j, SlotState.Marble);
          }
        }

        // middle rectangle
        else {
          board.put(i + "," + j, SlotState.Marble);
        }
      }
    }

    // check if row and col are valid
    if (row < 0 || col < 0 || super.getSlotAt(row, col) == SlotState.Invalid) {
      throw new IllegalArgumentException("Invalid empty cell position^^ (" + row + "," + col + ")");
    }

    // make the given position empty
    board.replace(row + "," + col, SlotState.Empty);
  }


/*
  *//**
   * Tests if the given position is invalid.
   *
   * @param row row to check against.
   * @param col column to check against.
   * @return true if the given position is invalid, false otherwise.
   *//*
  private boolean isInvalidPosition(int row, int col) {
    int sideRectangle = (boardSize / 3);
    // check if row and col are valid
    if (row < 0 || col < 0 || row > boardSize - 1 || col > boardSize - 1) {
      return true;
    }
    // top rectangle range
    boolean isTopRectangle = row >= 0 && row < sideRectangle;
    // bottom rectangle range
    boolean isBottomRectangle = row >= (boardSize - sideRectangle) && row < boardSize;
    // Left rectangle range
    boolean isLeftRectangle = col >= 0 && col < sideRectangle;
    // right rectangle range
    boolean isRightRectangle = col >= (boardSize - sideRectangle) && col < boardSize;

    // is top left, or top right
    // or is bottom left or bottom right
    return isTopRectangle && (isLeftRectangle || isRightRectangle)
            || isBottomRectangle && (isLeftRectangle || isRightRectangle);
  }*/
}
