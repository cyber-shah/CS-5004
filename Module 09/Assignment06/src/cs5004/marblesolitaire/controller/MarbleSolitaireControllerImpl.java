package cs5004.marblesolitaire.controller;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;
import cs5004.marblesolitaire.view.MarbleSolitaireView;

import java.util.Scanner;

/**
 * This class represents the controller for the Marble Solitaire game.
 * It implements the MarbleSolitaireController interface.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {
  private final MarbleSolitaireModel model;
  private final MarbleSolitaireView view;
  private final Readable readableInput;

  /**
   * This is the constructor for the MarbleSolitaireControllerImpl.
   *
   * @param model of the type MarbleSolitaireModel
   * @param view of the type MarbleSolitaireView
   * @param readableInput of the type Readable
   * @throws IllegalArgumentException if the model, view, or readableInput is null.
   */
  public MarbleSolitaireControllerImpl(MarbleSolitaireModel model,
                                       MarbleSolitaireView view, Readable readableInput) throws IllegalArgumentException {
    if (model == null || view == null || readableInput == null) {
      throw new IllegalArgumentException("Model, view, and readable cannot be null");
    }
    this.model = model;
    this.view = view;
    this.readableInput = readableInput;
  }

  private void resetMove(String[] move) {
    move[0] = "-1";
    move[1] = "-1";
    move[2] = "-1";
    move[3] = "-1";
  }

  /**
   * Play a new game of Marble Solitaire using the model.
   * Plays the game until it is over or the user quits.
   * Uses Readable to get input from the user.
   *
   * @throws IllegalStateException if it encounters issues with input or output.
   */
  @Override
  public void playGame() throws IllegalStateException {
    Scanner scanner = new Scanner(readableInput);
    int moveNumber = 0;

    // print the board before making any move
    this.printBoard(moveNumber);
    String[] move = new String[4];

    while(!model.isGameOver() && scanner.hasNextLine()) {
      resetMove(move);
      // for input types = "3\n1\n3\n3\n5\n2\n3\n2"
      if (scanner.nextLine().length() == 1) {
        lineSeperated(scanner, moveNumber, move);
        moveNumber++;
      }
      // for input types = "3 1 3 3\n5 2 3 2" and "3 1 3 3 5 2 3 2"
      else if (scanner.nextLine().length() > 3) {
        String line = scanner.nextLine();
        spaceSeperated(line, moveNumber);
        moveNumber++;
      }
    }

    // if the game is over
    if (model.isGameOver()) {
      try {
        view.renderMessage("\nGame over!");
        view.renderMessage("\nScore: " + Integer.toString(model.getScore()));
      }
      catch (Exception e) {
        e.printStackTrace();
        throw new IllegalStateException("Error rendering the message, game over");
      }
      return;
    }
  }
/*    // WHILE LOOP scanner has next Line --------------------
    while (scanner.hasNextLine()) {
      // 1. if game is over break
      if (model.isGameOver()) {
        try {
          view.renderMessage("\nGame over!");
          view.renderMessage("\nScore: " + Integer.toString(model.getScore()));
        }
        catch (Exception e) {
          e.printStackTrace();
          throw new IllegalStateException("Error rendering the message, game over");
        }
        return;
      }
      // 2. if game is not over and there are moves left

      // 2.1 if moves are separated by space, call spaceSeperated
      if (scanner.next().length() == 4) {
        this.spaceSeperated(scanner, moveNumber);
        moveNumber++;
      }
      else {
        this.lineSeperated(scanner, moveNumber);
      }
    }*/


  private void spaceSeperated(String line, int moveNumber) {
    // values in the line
    String[] values = line.split(" ");
    // if the input is bad
    if (isBadInput(values)) {
      try {
        view.renderMessage("\nBad Input. Play again. " + values + " is not a valid input");
      }
      catch (Exception e) {
        e.printStackTrace();
        throw new IllegalStateException("Error rendering the message, invalid move");
      }
      return;
    }

    // if the user wants to quit
    if (isQPresent(values)) {
      try {
        view.renderMessage("\nGame quit!");
        view.renderMessage("\nState of game when quit:");
        view.renderBoard();
        view.renderMessage("\nScore: " + Integer.toString(model.getScore()));
      }
      catch (Exception e) {
        e.printStackTrace();
        throw new IllegalStateException("Error rendering the message, game quit");
      }
      return;
    }

    // user wants to make a move
    model.move(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
            Integer.parseInt(values[2]), Integer.parseInt(values[3]));
    // render the updated board and score after the move
    this.printBoard(moveNumber);

  }


  private void printBoard(int lineNumber) throws IllegalStateException {
    try {
      // 1. render the board
      view.renderBoard();
      // 2. render the score
      view.renderMessage("\nScore after move " + lineNumber + ": "
              + Integer.toString(model.getScore()) + "\n");
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new IllegalStateException("Error rendering the board or score");
    }
  }


  /**
   * Checks if the input is bad.
   *
   * @param input the input to check.
   * @return true if the input is bad, false otherwise.
   */
  private boolean isBadInput(String[] input) {
    // scan all tokens in the line
    for (int i = 0; i < input.length; i++) {
      // if token is a NUMBER
      try {
        int token = Integer.parseInt(input[i]);
        if (token < 0 || token > this.model.getBoardSize()) {
          return true;
        }
      }
      // if token is a STRING
      catch (NumberFormatException e) {
        String token = input[i];
        if (!token.equals("q")) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean isQPresent(String[] values) {
    for (int i = 0; i < values.length; i++) {
      if (values[i].equalsIgnoreCase("q")) {
        return true;
      }
    }
    return false;
  }
}