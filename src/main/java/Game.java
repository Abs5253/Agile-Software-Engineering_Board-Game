import java.util.ArrayList;

/**
 * Game class for Toguz Korgool.
 * @author Vanessa
 */

public class Game {

    // If a player has at least 82 korgools in their kazan (their score), then that player has won the game
    // If both players have 81 korgools in their kazan, then it is a draw
    private static final int NUMBER_KORGOOLS_TO_END_GAME = 81;
    private static final int NUMBER_KORGOOLS_IN_LAST_HOLE_OF_MOVE_DIVISIBLE_BY_TO_CAPTURE = 2;

    private GameBoard gameBoard;
    private Player opponent;

    /**
     * Constructs a new game.
     * Used for when both players are not automated
     */
    Game() {
        gameBoard = new GameBoard();
    }

    Game(GameBoard gameBoard, String difficulty, Side opponentSide) {
        this.gameBoard = gameBoard;
        initialiseOpponent(difficulty, opponentSide);
    }

    /**
     * Initialises the opponent field
     * @param difficulty The level of how hard the game is, this is determined by how good the automated opponent is
     * @param opponentSide The side of the board of the opponent
     */
    private void initialiseOpponent(String difficulty, Side opponentSide) {
        if (difficulty.equals("Easy")) {
          opponent = new RandomPlayer(opponentSide);
        }
        else if (difficulty.equals("Hard")) {
            opponent = new AIPlayer(opponentSide);
        }
    }

    /**
     * Constructs a new game.
     * @param difficulty The level of how hard the game is, this is determined by how good the automated opponent is
     * @param opponentSide The side of the board of the opponent
     */
    Game(String difficulty, Side opponentSide) {
        gameBoard = new GameBoard();
        initialiseOpponent(difficulty, opponentSide);
    }

    /**
     * Constructs a new game when the GameBoard has non-default values
     * @param difficulty The level of how hard the game is, this is determined by how good the automated opponent is
     * @param opponentSide The side of the board of the opponent
     * @param whiteHoles The number of korgools in the holes of the white side of the board
     * @param blackHoles The number of korgools in the holes of the black side of the board
     * @param whiteKazan The number of korgools in the white kazan
     * @param blackKazan The number of korgools in the black kazan
     */
    public Game(String difficulty, Side opponentSide, int[] whiteHoles, int[] blackHoles, int whiteKazan, int blackKazan) {
      gameBoard = new GameBoard(whiteHoles, blackHoles, whiteKazan, blackKazan);
      initialiseOpponent(difficulty, opponentSide);
    }

    /**
     * Used for testing
     * @return the current state of the GameBoard
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Calculates the index of the next hole in the game board
     * Used in the move method
     * @param holeNumber the index of the hole you want to find the next hole of
     * @param incrementBy the number to increase the holeNumber by,
     * if you want the next index use 1
     * @return the index of the next hole
     */
    private int nextGameBoardIndex(int holeNumber, int incrementBy) {
        // BOARD_HOLES +1 as we are using indexes
        int incrementBoardIndex = (holeNumber+ incrementBy) % (GameBoard.BOARD_HOLES);
        return incrementBoardIndex;
    }

    /**
     * Increases the players score by the number of points given
     * @param currentPlayerSide the player whose points will increase (the player who made the move)
     * @param increaseBy number of points to increase the current points by
     */
    private void increasePlayersScore(Side currentPlayerSide, int increaseBy) {
      if (currentPlayerSide == Side.WHITE) {
        gameBoard.increaseWhiteScore(increaseBy);
      }
      else {
        gameBoard.increaseBlackScore(increaseBy);
      }
    }

    /**
     * Increases the opponents score by the number of points given
     * @param currentPlayerSide the side of the board of the player who made the move,
     *    the opposite player will have their points increased
     * @param increaseBy number of points to increase the current points by
     */
    private void increaseOpponentScore(Side currentPlayerSide, int increaseBy) {
        Side opponentSide = null;
        if (currentPlayerSide == Side.WHITE) {
            opponentSide = Side.BLACK;
        }
        else {
            opponentSide = Side.WHITE;
        }
        increasePlayersScore(opponentSide, increaseBy);
    }

    /**
     * Checks if the hole given is a tuz
     * if it is, puts the korgools in this hole into the owner of the tuz's kazan
     * @param currentHole the hole that you are checking is a tuz
     */
    private void checkHoleIsATuz(Hole currentHole, Side currentPlayerSide) {
      // Rule: Tuz. All korgools in a tuz are transferred to the owner of the tuz's kazan
      // the owner of the kazan is the opponent to the side of the board that the hole is on
      if (currentHole.isTuz()) {
        int numberKorgoolsInThisHole = currentHole.clearHole();
        Side sideOfTuz = currentHole.getSide();

        increaseOpponentScore(sideOfTuz, numberKorgoolsInThisHole);

      }
    }

    /**
     * Returns the index of the hole in the ArrayList of the gameBoard
     * @param holeNumber A number 1-9 (inclusive), representing the hole
     * on 1 side of the board
     * @param side The side of the board that the hole is on
     * @return the index of the hole in the ArrayList of the gameBoard
     */
    private int calculateBoardHoleIndexFromHoleNumberAndSide(int holeNumber, Side side) {
        int boardHoleIndexChosen = -1;
        if (side == Side.WHITE) {
            boardHoleIndexChosen = holeNumber-1;
        }
        else {
            boardHoleIndexChosen = holeNumber + (GameBoard.BOARD_HOLES/2) - 1;
        }
        return boardHoleIndexChosen;
    }

    /**
     * @param holeMoveSelected the hole which we want to find out it's board index
     * @return the index in the ArrayList in GameBoard where this hole is found
     */
    private int calculateBoardHoleIndexFromHole(Hole holeMoveSelected) {
      int boardHoleIndexChosen = -1;
      if (holeMoveSelected.getSide() == Side.BLACK) {
        boardHoleIndexChosen=8;
      }
      boardHoleIndexChosen+= holeMoveSelected.getNumber();
      return boardHoleIndexChosen;
    }

    /**
     * Use this method if only 1 korgool is in the hole selected
     * Moves the 1 korgool from hole selected to the next hole
     * @param holeMoveSelected the hole on the board that a player has chosen to make a move from
     * @param holeInBoard the ArrayList containing the holes in the board
     * @param side The side of the board of the player who made the move
     * @return The hole where the korgool is placed in
     */
    private Hole movingKorgoolsFromHoleWith1KorgoolInIt(Hole holeMoveSelected, ArrayList<Hole> holesInBoard, Side side) {
        holeMoveSelected.clearHole();

        int boardHoleIndexChosen = calculateBoardHoleIndexFromHole(holeMoveSelected);

        int nextBoardIndex = nextGameBoardIndex(boardHoleIndexChosen,1);

        Hole holePutKorgoolIn = holesInBoard.get(nextBoardIndex);
        holePutKorgoolIn.increment();
        checkHoleIsATuz(holePutKorgoolIn, side);
        Hole lastHoleKorgoolsPutInMove = holePutKorgoolIn;
        return lastHoleKorgoolsPutInMove;
    }

    /**
     * Use this method if there is more than 1 korgool in the hole selected
     * Take all the korgools from the selected hole and redistrubute them
     * to the other holes in an anticlockwise direction around the board.
     * Put the 1st korgool into the hole that was selected, then
     * put the the next korgool into the adjacent hole to the right, and so on.
     * @param holeMoveSelected the hole on the board that a player has chosen to make a move from
     * @param holeInBoard the ArrayList containing the holes in the board
     * @param side The side of the board of the player who made the move
     * @return The hole where the korgool is placed in
     */
    private Hole movingKorgoolsFromHoleWithMoreThan1KorgoolInIt(Hole holeMoveSelected, ArrayList<Hole> holesInBoard, Side side) {
        int numberKorgoolsInHoleSelected = holeMoveSelected.getKorgools();
        Hole lastHoleKorgoolsPutInMove = null;
        int boardHoleIndexChosen = calculateBoardHoleIndexFromHole(holeMoveSelected);

        // Iterate through the number of korgools in the hole selected
        for (int i=0; i<numberKorgoolsInHoleSelected; i++) {
            int currentHoleIndex = nextGameBoardIndex(boardHoleIndexChosen,i);
            Hole currentHole = holesInBoard.get(currentHoleIndex);

            // Put the 1st korgool in hole that it was taken from
            if (i==0) {
                holeMoveSelected.setKorgools(1);
            }
            // The rest of the korgools go in 1 hole each, around the board
            else {

                currentHole.increment();

                // Currently putting the last korgool in the correct hole
                if (i == numberKorgoolsInHoleSelected-1) {
                    lastHoleKorgoolsPutInMove = holesInBoard.get(currentHoleIndex);
                }
            }

            checkHoleIsATuz(currentHole, side);
        }
        return lastHoleKorgoolsPutInMove;
    }

    /**
     * Moves the korgools based on the condition if the hole selected to make the move from
     * contains 1 korgool, or more than 1 korgool
     * @param holeMoveSelected the hole on the board that a player has chosen to make a move from
     * @param holeInBoard the ArrayList containing the holes in the board
     * @param side The side of the board of the player who made the move
     * @return Hole the hole where the final korgool is placed in
     */
    private Hole movingTheKorgools(Hole holeMoveSelected, ArrayList<Hole> holesInBoard, Side side) {
        int numberKorgoolsInHoleSelected = holeMoveSelected.getKorgools();

        // Rule: If 1 korgools is in the chosen hole, the chosen hole is emptied,
        // and the korgool is moved to the next hole
        if (numberKorgoolsInHoleSelected == 1) {
            Hole lastHoleKorgoolsPutInMove = movingKorgoolsFromHoleWith1KorgoolInIt(holeMoveSelected, holesInBoard, side);
            return lastHoleKorgoolsPutInMove;
        }
        else {
            Hole lastHoleKorgoolsPutInMove = movingKorgoolsFromHoleWithMoreThan1KorgoolInIt(holeMoveSelected, holesInBoard, side);
            return lastHoleKorgoolsPutInMove;
        }
    }

    /**
     * Identifies whether the given hole can become a tuz,
     * if it can be a tuz, set the hole to become a tuz
     * and move the korgools into the correct player's kazan
     *
     * It is based on the rule:
     * Rule: If the last korgool in a move ends up in an opponent's hole
     * containing two korgools (so at the end of the move, the hole contains three korgools),
     * then this hole is marked as a tuz.
     * This means that this hole now belongs to the player who claimed the tuz
     * and all korgools in the tuz are transferred to the owner's kazan.
     *
     * @param lastHoleKorgoolsPutInMove the hole to identify and set as a tuz,
     * it is the last hole that korgools are put in a move
     * @param side The side of the board of the player who made the move
     */
    private void identifyAndSetTuz(Hole lastHoleKorgoolsPutInMove, Side side) {
        Side lastHoleKorgoolsPutInMoveSide = lastHoleKorgoolsPutInMove.getSide();

        // Check if the last hole that the korgools landed in is in the opposite side
        if ((lastHoleKorgoolsPutInMoveSide == Side.WHITE && side == Side.BLACK)
        || (lastHoleKorgoolsPutInMoveSide == Side.BLACK && side == Side.WHITE)) {
            int lastHoleKorgoolsPutInMoveNumber = lastHoleKorgoolsPutInMove.getNumber();
            boolean canHoleBeTuz = gameBoard.setHoleAsTuz(lastHoleKorgoolsPutInMoveNumber, lastHoleKorgoolsPutInMoveSide);

            // If this hole is now a tuz, move the korgools into the correct player's kazan
            if (canHoleBeTuz) {
                int lastHoleKorgoolsPutInMoveNumberOfKorgoolsItContains = lastHoleKorgoolsPutInMove.clearHole();
                increasePlayersScore(side, lastHoleKorgoolsPutInMoveNumberOfKorgoolsItContains);

            }
        }
    }

    /**
     * Identifies if the last hole that korgools are put in during a move
     * can be captured by the player who made the move.
     *
     * It is based on the rule:
     * Rule: If the last korgool in a move ends up in an opponent's hole,
     * and the number of korgools in that hole is now even,
     * then the player who made the move captures all the korgools in that hole,
     * the korgools are moved into the player's kazan
     *
     * @param lastHoleKorgoolsPutInMove the hole to identify if player can capture korgools from,
     * it is the last hole that korgools are put in a move
     * @param side The side of the board of the player who made the move
     */
    private void identifyAndCaptureKorgools(Hole lastHoleKorgoolsPutInMove, Side side){
        if (lastHoleKorgoolsPutInMove.getSide() != side && lastHoleKorgoolsPutInMove.getKorgools()%NUMBER_KORGOOLS_IN_LAST_HOLE_OF_MOVE_DIVISIBLE_BY_TO_CAPTURE ==0) {
            int numberKorgoolsInLastHole = lastHoleKorgoolsPutInMove.clearHole();

            increasePlayersScore(side, numberKorgoolsInLastHole);
        }
    }

    /**
     * @return true means the game has ended and false means that the game is still running
     */
    public boolean isEndOfGame(Side side) {
        // A draw
        if (gameBoard.getWhiteScore() == NUMBER_KORGOOLS_TO_END_GAME && gameBoard.getBlackScore() == NUMBER_KORGOOLS_TO_END_GAME) {
            return true;
        }

        // A win
        int numberKorgoolsToWinTheGame = NUMBER_KORGOOLS_TO_END_GAME+1;
        if (gameBoard.getWhiteScore() >= numberKorgoolsToWinTheGame || gameBoard.getBlackScore() >= numberKorgoolsToWinTheGame) {
            return true;
        }

        // The next player can't make a move
        ArrayList<Hole> holesInBoard = gameBoard.getHoles();
        Side nextPlayer = Side.WHITE;
        int boardIndexStartValue = 0;
        if (side == Side.WHITE) {
            nextPlayer = Side.BLACK;
            boardIndexStartValue = GameBoard.BOARD_HOLES/2;
        }

        for (int i=0; i < (GameBoard.BOARD_HOLES/2); i++) {
            int currentIndex = i+boardIndexStartValue;
            Hole currentHole = holesInBoard.get(currentIndex);

            // If the next player has a hole to make a move from then the game is not over
            if (currentHole.getKorgools() > 0) {
                return false;
            }
        }
        // All of the next players holes have no korgools in, so they cannot make a move, so the game is over
        // move all remaing korgools from holes into the respective sides kazan
        for (Hole hole : holesInBoard) {
            if(hole.getSide() == Side.WHITE) {
                gameBoard.increaseWhiteScore(hole.clearHole());
            } else {
                gameBoard.increaseBlackScore(hole.clearHole());
            }
        }
        return true;
    }

    /**
     * Updates the holes and GameBoard as a result of a move
     * @param holeNumber A number 1-9 (inclusive), representing the hole
     * on the board that a player has chosen to make a move from
     * @param side The side of the board of the player who made the move
     * @return true means the game has ended and false means that the game is still running
     */
    public boolean move(int holeNumber, Side side) {
        ArrayList<Hole> holesInBoard = gameBoard.getHoles();

        int boardHoleIndexChosen = calculateBoardHoleIndexFromHoleNumberAndSide(holeNumber, side);
        Hole holeMoveSelected = holesInBoard.get(boardHoleIndexChosen);

        Hole lastHoleKorgoolsPutInMove = movingTheKorgools(holeMoveSelected, holesInBoard, side);

        // At the end of a move
        if (lastHoleKorgoolsPutInMove!=null) {
            identifyAndSetTuz(lastHoleKorgoolsPutInMove, side);
            identifyAndCaptureKorgools(lastHoleKorgoolsPutInMove, side);
        }

        return isEndOfGame(side);

    }

    /**
     * Calculates the board hole number which is 1 - GameBoard.HOLES/2
     * (1 - 9 inclusive)
     * from the boardHoleIndex
     * @param boardHoleIndex the index of the Hole on the game board ArrayList
     */
    private int calculateBoardHoleNumberFromHoleIndex(int boardHoleIndex) {
        if (boardHoleIndex < GameBoard.BOARD_HOLES/2) {
            return boardHoleIndex + 1;
        }
        return (boardHoleIndex - (GameBoard.BOARD_HOLES/2)) + 1;
    }

    /**
     * Carries out the move of the automated opponent
     * @return true means the game has ended and false means that the game is still running
     */
    public boolean moveAutomatedOpponent() {
        // Might need to test if it is -1
        int boardIndexOfOpponentMove = opponent.move(gameBoard.getHoles());
        int holeNumberOfOpponentMove = calculateBoardHoleNumberFromHoleIndex(boardIndexOfOpponentMove);
        boolean gameEnded = move(holeNumberOfOpponentMove, opponent.getSideColor());
        return gameEnded;

    }

    /**
     * @param side The side of the caller
     * @return returns a string stating if the caller has won, lost or drawn
     */
    public String getWinner(Side side) {
        if(gameBoard.getWhiteScore() == gameBoard.getBlackScore()) {
            return "It's a draw";
        } else {
            return ((side == Side.WHITE) && (gameBoard.getWhiteScore() > gameBoard.getBlackScore()) ||
                    (side == Side.BLACK) && (gameBoard.getBlackScore() > gameBoard.getWhiteScore())) ? "You win!" : "You lose..." ;
        }
    }

}
