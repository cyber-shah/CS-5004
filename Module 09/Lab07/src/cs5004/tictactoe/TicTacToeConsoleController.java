package cs5004.tictactoe;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TicTacToeConsoleController implements TicTacToeController {

  private final Readable readable;
  private final Appendable appendable;

  /**
   * Constructor for the TicTacToeConsoleController.
   *
   * @param readable  the readable object
   * @param appendable the appendable object
   */
  public TicTacToeConsoleController(Readable readable, Appendable appendable) throws NullPointerException {
    if (readable == null || appendable == null) {
      throw new NullPointerException("Readable or Appendable is null");
    }
    this.readable = readable;
    this.appendable = appendable;
  }

  /**
   * Execute a single game of tic-tac-toe given a tic-tac-toe Model. When the game is over,
   * the playGame method ends.
   *
   * @param model a non-null tic tac toe Model
   */
  @Override
  public void playGame(TicTacToe model) throws IllegalStateException {
    String[] userInput = new String[2];
    Scanner scanner = new Scanner(readable);

    // 1. game goes on until game is over or user enters q or Q
    while (!model.isGameOver() && scanner.hasNext()) {
      // 1. ask for input
      // should catch Exception from Appendable
      try {
        appendable.append("Enter a move for ").append(model.getTurn().toString()).append(":\n");
      }
      catch (IOException e) {
        throw new IllegalStateException("IO Exception when appending");
      }

      // 2. run the loop till userInput is full
      for (int i = 0; i < 2; i++) {

        // 3. scanner.next = token
        String token;
        try {
          token = scanner.next();
        }
        catch (NoSuchElementException e) {
          throw new IllegalStateException("No more inputs found");
        }

        // 4. continue to update token until it is valid
        // Non-integer value rejection
        // TODO: throw an error for invalid inputs
        while (this.isInvalidInput(token)) {
          token = scanner.next();
        }

        // 5. check if token is quit
        if (token.equalsIgnoreCase("q")) {
          try {
            appendable.append("Game quit! Ending game state:\n").append(model.toString()).append("\n");
          }
          catch (IOException e) {
            throw new IllegalStateException("Cannot append quit");
          }
        }
        // 6. token is now valid and not quit
        else {
          userInput[i] = token;
        }
      }

      // 7. by here we have a valid userInput, both tokens are valid and not quit
      // make the move ------------------
      try {
        model.move(Integer.parseInt(userInput[0]), Integer.parseInt(userInput[1]));
      }
      catch (IllegalArgumentException e) {
        throw new IllegalStateException(userInput[0] + ", " + userInput[1] + "is an Invalid move");
      }
    }

    // when the game ends
    // output the result
    try {
      appendable.append(model.toString()).append("\n");
    }
    catch (IOException e) {
      throw new IllegalStateException("Cannot append end of game");
    }
  }


  /**
   * Validate the user input.
   *
   * @param token the user input.
   * @return true if the input is invalid, false otherwise.
   */
  private boolean isInvalidInput(String token) {
    // 1. Check if input contains valid characters
    if (!token.matches("[1-4]") && !token.equalsIgnoreCase("q")) {
      return true;
    }

    // 2. by here we know input has exactly 2 elements and contains valid characters
    try {
      int value = Integer.parseInt(token);
      if (value < 1 || value > 4) {
        return true;
      }
    } catch (NumberFormatException e) {
      return true;
    }
    return false;
  }

}
