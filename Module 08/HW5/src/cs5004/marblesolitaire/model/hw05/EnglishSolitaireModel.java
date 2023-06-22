package cs5004.marblesolitaire.model.hw05;

import java.util.HashMap;
import java.util.Map;

public class EnglishSolitaireModel implements MarbleSolitaireModel {
  private Map<String, SlotState> board;
  private int boardSize;

  public EnglishSolitaireModel() {
    boardSize = 7;
    board = new HashMap<>();
    int bufferRectangle = (boardSize / 2) - 2;
    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        // set top left square and top right square to invalid
        if (isInvalidPosition(i,j)) {
          board.put(i + "," + j, SlotState.Invalid);
        }
        else {
          board.put(i + "," + j, SlotState.Marble);
        }
      }
      // make the centre empty
      board.replace("3,3", SlotState.Empty);
    }
  }

  public boolean isInvalidPosition (int row, int col) {
    int armWidth = boardSize / 2;
    int sideRectangle = armWidth - 1;

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

  }

  /**
   * Determine and return if the game is over or not. A game is over if no
   * more moves can be made.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    return false;
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
    return board.get(row + "," + col);
  }

  /**
   * Return the number of marbles currently on the board.
   *
   * @return the number of marbles currently on the board
   */
  @Override
  public int getScore() {
    return 0;
  }
}
