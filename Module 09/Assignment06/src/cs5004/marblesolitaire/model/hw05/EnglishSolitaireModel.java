package cs5004.marblesolitaire.model.hw05;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the English Solitaire Model.
 * It implements the MarbleSolitaireModel interface.
 */
public class EnglishSolitaireModel implements MarbleSolitaireModel {
  private final Map<String, SlotState> board;
  private final int boardSize;

  /**
   * Constructor for EnglishSolitaireModel.
   * Creates a 7 sized board with the empty slot in the centre.
   */
  public EnglishSolitaireModel() {
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
  public EnglishSolitaireModel(int row, int col) throws IllegalArgumentException {
    this(3, row, col);
  }

  /**
   * Constructor for EnglishSolitaireModel.
   * Creates a board with the given boardSize and empty slot in the centre.
   *
   * @param boardSize size of the board.
   * @throws IllegalArgumentException if the given boardSize is invalid.
   */
  public EnglishSolitaireModel(int boardSize) throws IllegalArgumentException {
    this ((boardSize / 3) + 1, (boardSize / 2) + 1, (boardSize / 2) + 1);
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
  public EnglishSolitaireModel(int armThickness, int row, int col) throws IllegalArgumentException {
    this.boardSize = armThickness + (armThickness - 1) * 2;
    // check if row and col are valid
    if (row < 1 || col < 1 || isInvalidPosition(row, col)) {
      throw new IllegalArgumentException("Invalid empty cell position^^ (" + row + "," + col + ")");
    }

    // check if armThickness is odd
    if (armThickness % 2 == 0) {
      throw new IllegalArgumentException("Arm thickness must be an odd number");
    }

    board = new HashMap<>();
    for (int i = 1; i <= this.boardSize; i++) {
      for (int j = 1; j <= this.boardSize; j++) {
        // set top left square and top right square to invalid
        if (isInvalidPosition(i,j)) {
          board.put(i + "," + j, SlotState.Invalid);
        }
        else {
          board.put(i + "," + j, SlotState.Marble);
        }
      }
    }
    // make the given position empty
    board.replace(row + "," + col, SlotState.Empty);
  }


  /**
   * Tests if the given position is invalid.
   *
   * @param row row to check against.
   * @param col column to check against.
   * @return true if the given position is invalid, false otherwise.
   */
  public boolean isInvalidPosition(int row, int col) {
    int sideRectangle = (boardSize / 3);
    // check if row and col are valid
    if (row < 1 || col < 1 || row > boardSize || col > boardSize) {
      return true;
    }
    // top rectangle range
    boolean isTopRectangle = row >= 1 && row <= sideRectangle;
    // bottom rectangle range
    boolean isBottomRectangle = row > (boardSize - sideRectangle) && row <= boardSize;
    // Left rectangle range
    boolean isLeftRectangle = col >= 1 && col <= sideRectangle;
    // right rectangle range
    boolean isRightRectangle = col > (boardSize - sideRectangle) && col <= boardSize;

    // is top left, or top right
    // or is bottom left or bottom right
    return isTopRectangle && (isLeftRectangle || isRightRectangle)
            || isBottomRectangle && (isLeftRectangle || isRightRectangle);
  }

  /**
   * Move a single marble from a given position to another given position.
   * A move is valid only if the from and to positions are valid. Specific
   * implementations may place additional constraints on the validity of a move.
   *
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                (starts at 0)
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0)
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0)
   * @throws IllegalArgumentException if the move is not possible
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    if (fromCol < 1 || fromCol > boardSize
            || fromRow < 1 || fromRow >  boardSize
            || toCol < 1 || toCol > boardSize
            || toRow < 1|| toRow > boardSize) {
      throw new IllegalArgumentException("Move Out of Bounds");
    }

    if (fromCol == toCol && Math.abs(toRow - fromRow) != 2) {
      throw new IllegalArgumentException("not 2 spaces away");
    } else if (fromRow == toRow && Math.abs(toCol - fromCol) != 2) {
      throw new IllegalArgumentException("not 2 spaces away");
    }

    // get the slot states for both coordinates
    SlotState fromSlot = board.get(fromRow + "," + fromCol);
    SlotState toSlot = board.get(toRow + "," + toCol);

    // if either of them is invalid, throw an exception
    if (fromSlot == SlotState.Invalid || toSlot == SlotState.Invalid) {
      throw new IllegalArgumentException("Either from or to is Invalid");
    }

    // if fromSlot is empty or toSlot is NOT empty,
    if (fromSlot == SlotState.Empty || toSlot != SlotState.Empty) {
      throw new IllegalArgumentException("Either from is empty or to is NOT empty"
              + "Move between (" + fromRow + "," + fromCol
              + ") and (" + toRow + "," + toCol
              + ") is not possible");
    }

    // get the middle between from and TO
    int midRow = (fromRow + toRow) / 2;
    int midCol = (fromCol + toCol) / 2;

    // get the slot slate there
    SlotState midSlot = board.get(midRow + "," + midCol);

    // if there is no marble between from and TO, throw exception
    if (midSlot != SlotState.Marble) {
      throw new IllegalArgumentException("No marble in between!");
    }

    // Perform the move by updating the board positions
    board.put(fromRow + "," + fromCol, SlotState.Empty);
    board.put(midRow + "," + midCol, SlotState.Empty);
    board.put(toRow + "," + toCol, SlotState.Marble);
  }

  /**
   * Determine and return if the game is over or not. A game is over if no
   * more moves can be made.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    // Iterate over each position on the board
    for (int i = 1; i <= boardSize; i++) {
      for (int j = 1; j <= boardSize; j++) {
        // Check if the current position contains a marble
        if (board.get(i + "," + j) == SlotState.Marble) {
          // Check if any valid moves can be made from this position
          if (isValidMove(i, j)) {
            return false; // Found a valid move, game is not over
          }
        }
      }
    }
    return true; // No valid moves found, game is over
  }

  /**
   * Tests whether there is a valid move that can be made from the given position.
   *
   * @param row row of the given marble.
   * @param col column of the given marble.
   * @return true if there is a valid move that can be made from the given position.
   */
  private boolean isValidMove(int row, int col) {
    // Check if the current position contains a marble
    if (board.get(row + "," + col) != SlotState.Marble) {
      return false;
    }

    // Check if a move to the left is valid
    if (col >= 2 && board.get(row + "," + (col - 2)) == SlotState.Empty
            && board.get(row + "," + (col - 1)) == SlotState.Marble) {
      return true;
    }

    // Check if a move to the right is valid
    if (col <= boardSize - 3 && board.get(row + "," + (col + 2)) == SlotState.Empty
            && board.get(row + "," + (col + 1)) == SlotState.Marble) {
      return true;
    }

    // Check if a move upwards is valid
    if (row >= 2 && board.get((row - 2) + "," + col) == SlotState.Empty
            && board.get((row - 1) + "," + col) == SlotState.Marble) {
      return true;
    }

    // Check if a move downwards is valid
    return row <= boardSize - 3 && board.get((row + 2) + "," + col) == SlotState.Empty
            && board.get((row + 1) + "," + col) == SlotState.Marble;// No valid moves found
  }

  /**
   * Return the size of this board. The size is roughly the longest dimension of a board
   *
   * @return the size as an integer
   */
  @Override
  public int getBoardSize() {
    return this.boardSize;
  }

  /**
   * Get the state of the slot at a given position on the board.
   *
   * @param row the row of the position sought, starting at 0
   * @param col the column of the position sought, starting at 0
   * @return the state of the slot at the given row and column
   * @throws IllegalArgumentException if the row or the column are beyond
   *                                  the dimensions of the board
   */
  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    // check if row and col are in bounds
    if (row < 1 || row > boardSize || col < 1 || col > boardSize) {
      throw new IllegalArgumentException(row + "," + col + "Slot Out of Bounds");
    }
    else {
      return board.get(row + "," + col);
    }
  }

  /**
   * Return the number of marbles currently on the board.
   *
   * @return the number of marbles currently on the board
   */
  @Override
  public int getScore() {
    int score = 0;
    for (int i = 1; i <= boardSize; i++) {
      for (int j = 1; j <= boardSize; j++) {
        SlotState slot = board.get(i + "," + j);
        if (slot == SlotState.Marble) {
          score++;
        }
      }
    }
    return score;
  }
}
