import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Test class from the Game class
 *
 * @author Vanessa
 */

public class GameTest {

    @Test
    public void make1MoveFromHole1() {
      // Make sure it only takes the korgools if it is on the other player's side
      Game game = new Game();
      assertFalse(game.move(1,Side.WHITE));
      GameBoard gameBoard = game.getGameBoard();


      assertEquals(1, gameBoard.getHoles().get(0).getKorgools());
      assertEquals(10, gameBoard.getHoles().get(1).getKorgools());
      assertEquals(10, gameBoard.getHoles().get(2).getKorgools());
      assertEquals(10, gameBoard.getHoles().get(3).getKorgools());
      assertEquals(10, gameBoard.getHoles().get(4).getKorgools());
      assertEquals(10, gameBoard.getHoles().get(5).getKorgools());
      assertEquals(10, gameBoard.getHoles().get(6).getKorgools());
      assertEquals(10, gameBoard.getHoles().get(7).getKorgools());
      assertEquals(10, gameBoard.getHoles().get(8).getKorgools());
      assertEquals(0, gameBoard.getWhiteScore());
      assertEquals(0, gameBoard.getBlackScore());
    }

    @Test
    public void make1MoveLastKorgoolLandsInOpponentsHoleAndIsEven() {
        Game game = new Game();
        assertFalse(game.move(3,Side.WHITE));
        GameBoard gameBoard = game.getGameBoard();

        assertEquals(9, gameBoard.getHoles().get(0).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(1).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(2).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(3).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(4).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(5).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(6).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(7).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(8).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(9).getKorgools());
        assertEquals(0, gameBoard.getHoles().get(10).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(11).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(12).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(13).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(14).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(15).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(16).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(17).getKorgools());

        assertEquals(10, gameBoard.getWhiteScore());
        assertEquals(0, gameBoard.getBlackScore());
    }


    @Test
    public void makeTuz() {
        Game game = new Game();

        // Create tuz
        game.move(2,Side.WHITE);
        game.move(9,Side.BLACK);
        game.move(9,Side.WHITE);
        game.move(8,Side.BLACK);
        game.move(1,Side.WHITE);
        game.move(7,Side.BLACK);
        game.move(4,Side.WHITE);

        GameBoard gameBoard = game.getGameBoard();

        assertTrue(gameBoard.getHoles().get(16).isTuz());

        assertEquals(2, gameBoard.getHoles().get(0).getKorgools());
        assertEquals(5, gameBoard.getHoles().get(1).getKorgools());
        assertEquals(14, gameBoard.getHoles().get(2).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(3).getKorgools());
        assertEquals(15, gameBoard.getHoles().get(4).getKorgools());
        assertEquals(15, gameBoard.getHoles().get(5).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(6).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(7).getKorgools());
        assertEquals(3, gameBoard.getHoles().get(8).getKorgools());
        assertEquals(3, gameBoard.getHoles().get(9).getKorgools());
        assertEquals(12, gameBoard.getHoles().get(10).getKorgools());
        assertEquals(11, gameBoard.getHoles().get(11).getKorgools());
        assertEquals(11, gameBoard.getHoles().get(12).getKorgools());
        assertEquals(11, gameBoard.getHoles().get(13).getKorgools());
        assertEquals(11, gameBoard.getHoles().get(14).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(15).getKorgools());
        assertEquals(0, gameBoard.getHoles().get(16).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(17).getKorgools());

        assertEquals(15, gameBoard.getWhiteScore());
        assertEquals(26, gameBoard.getBlackScore());

        //Make moves so korgools land in tuz (and should get put into the player who own's the tuz's kazan)

        game.move(7,Side.BLACK);

        assertEquals(2, gameBoard.getHoles().get(0).getKorgools());
        assertEquals(5, gameBoard.getHoles().get(1).getKorgools());
        assertEquals(14, gameBoard.getHoles().get(2).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(3).getKorgools());
        assertEquals(15, gameBoard.getHoles().get(4).getKorgools());
        assertEquals(15, gameBoard.getHoles().get(5).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(6).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(7).getKorgools());
        assertEquals(3, gameBoard.getHoles().get(8).getKorgools());
        assertEquals(3, gameBoard.getHoles().get(9).getKorgools());
        assertEquals(12, gameBoard.getHoles().get(10).getKorgools());
        assertEquals(11, gameBoard.getHoles().get(11).getKorgools());
        assertEquals(11, gameBoard.getHoles().get(12).getKorgools());
        assertEquals(11, gameBoard.getHoles().get(13).getKorgools());
        assertEquals(11, gameBoard.getHoles().get(14).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(15).getKorgools());
        assertEquals(0, gameBoard.getHoles().get(16).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(17).getKorgools());

        assertEquals(16, gameBoard.getWhiteScore());
        assertEquals(26, gameBoard.getBlackScore());


        game.move(6,Side.WHITE);

        assertEquals(3, gameBoard.getHoles().get(0).getKorgools());
        assertEquals(6, gameBoard.getHoles().get(1).getKorgools());
        assertEquals(14, gameBoard.getHoles().get(2).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(3).getKorgools());
        assertEquals(15, gameBoard.getHoles().get(4).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(5).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(6).getKorgools());
        assertEquals(3, gameBoard.getHoles().get(7).getKorgools());
        assertEquals(4, gameBoard.getHoles().get(8).getKorgools());
        assertEquals(4, gameBoard.getHoles().get(9).getKorgools());
        assertEquals(13, gameBoard.getHoles().get(10).getKorgools());
        assertEquals(12, gameBoard.getHoles().get(11).getKorgools());
        assertEquals(12, gameBoard.getHoles().get(12).getKorgools());
        assertEquals(12, gameBoard.getHoles().get(13).getKorgools());
        assertEquals(12, gameBoard.getHoles().get(14).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(15).getKorgools());
        assertEquals(0, gameBoard.getHoles().get(16).getKorgools());
        assertEquals(3, gameBoard.getHoles().get(17).getKorgools());

        assertEquals(17, gameBoard.getWhiteScore());
        assertEquals(26, gameBoard.getBlackScore());

        // Try and make a tuz on the same hole number on the other side of the board
        // This should not be allowed
        game.move(8,Side.WHITE);
        game.move(7,Side.WHITE);
        assertFalse(game.move(6,Side.BLACK));

        assertFalse(gameBoard.getHoles().get(7).isTuz());

        assertEquals(4, gameBoard.getHoles().get(0).getKorgools());
        assertEquals(7, gameBoard.getHoles().get(1).getKorgools());
        assertEquals(15, gameBoard.getHoles().get(2).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(3).getKorgools());
        assertEquals(16, gameBoard.getHoles().get(4).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(5).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(6).getKorgools());
        assertEquals(3, gameBoard.getHoles().get(7).getKorgools());
        assertEquals(5, gameBoard.getHoles().get(8).getKorgools());
        assertEquals(5, gameBoard.getHoles().get(9).getKorgools());
        assertEquals(13, gameBoard.getHoles().get(10).getKorgools());
        assertEquals(12, gameBoard.getHoles().get(11).getKorgools());
        assertEquals(12, gameBoard.getHoles().get(12).getKorgools());
        assertEquals(12, gameBoard.getHoles().get(13).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(14).getKorgools());
        assertEquals(3, gameBoard.getHoles().get(15).getKorgools());
        assertEquals(0, gameBoard.getHoles().get(16).getKorgools());
        assertEquals(4, gameBoard.getHoles().get(17).getKorgools());

        assertEquals(18, gameBoard.getWhiteScore());
        assertEquals(26, gameBoard.getBlackScore());

    }


    @Test
    public void cannotMakeHole9ATuz() {
        Game game = new Game();
        game.move(9,Side.WHITE);
        game.move(7,Side.BLACK);
        game.move(6,Side.WHITE);
        assertFalse(game.move(9,Side.BLACK));

        GameBoard gameBoard = game.getGameBoard();
        assertFalse(gameBoard.getHoles().get(8).isTuz());
        assertEquals(10, gameBoard.getBlackScore());
        assertEquals(3, gameBoard.getHoles().get(8).getKorgools());
    }

    @Test
    public void moveFromHoleWith1Korgool() {
        Game game = new Game();
        game.move(4,Side.WHITE);
        assertFalse(game.move(4,Side.WHITE));

        GameBoard gameBoard = game.getGameBoard();
        assertEquals(9, gameBoard.getHoles().get(0).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(1).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(2).getKorgools());
        assertEquals(0, gameBoard.getHoles().get(3).getKorgools());
        assertEquals(11, gameBoard.getHoles().get(4).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(5).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(6).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(7).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(8).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(9).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(10).getKorgools());
        assertEquals(0, gameBoard.getHoles().get(11).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(12).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(13).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(14).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(15).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(16).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(17).getKorgools());

        assertEquals(10, gameBoard.getWhiteScore());
        assertEquals(0, gameBoard.getBlackScore());
    }

    @Test
    public void winGame() {
        Game game = new Game();
        GameBoard gameBoard = game.getGameBoard();
        ArrayList<Hole> holesList = gameBoard.getHoles();

        for (int i=0; i<10; i++) {
            holesList.get(i).setKorgools(1);
            gameBoard.increaseBlackScore(8);
        }
        // Black score should be 80
        assertEquals(80,gameBoard.getBlackScore());

        assertTrue(game.move(6,Side.BLACK));
        assertEquals(2, gameBoard.getHoles().get(0).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(1).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(2).getKorgools());
        assertEquals(2, gameBoard.getHoles().get(3).getKorgools());
        assertEquals(0, gameBoard.getHoles().get(4).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(5).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(6).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(7).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(8).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(9).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(10).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(11).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(12).getKorgools());
        assertEquals(9, gameBoard.getHoles().get(13).getKorgools());
        assertEquals(1, gameBoard.getHoles().get(14).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(15).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(16).getKorgools());
        assertEquals(10, gameBoard.getHoles().get(17).getKorgools());

        assertEquals(0, gameBoard.getWhiteScore());
        assertEquals(82, gameBoard.getBlackScore());

    }

    @Test
    public void shortGameFinishes() {
        Game game = new Game();
        GameBoard gameBoard = game.getGameBoard();
        ArrayList<Hole> holesList = gameBoard.getHoles();

        assertFalse(game.move(9,Side.WHITE));
        assertFalse(game.move(2,Side.BLACK));
        assertFalse(game.move(8,Side.WHITE));
        assertFalse(game.move(4,Side.BLACK));

        assertEquals(22, gameBoard.getWhiteScore());
        assertEquals(20, gameBoard.getBlackScore());

        assertFalse(game.move(7,Side.WHITE));
        assertFalse(game.move(4,Side.BLACK));
        assertFalse(game.move(5,Side.WHITE));
        assertFalse(game.move(2,Side.BLACK));
        assertFalse(game.move(9,Side.WHITE));
        assertFalse(game.move(9,Side.BLACK));

        assertTrue(game.move(9,Side.WHITE));

        assertEquals(84, gameBoard.getWhiteScore());
        assertEquals(20, gameBoard.getBlackScore());

    }

    @Test
    public void testDraw() {
        Game game = new Game();
        GameBoard gameBoard = game.getGameBoard();
        ArrayList<Hole> holesList = gameBoard.getHoles();

        gameBoard.setWhiteScore(79);
        gameBoard.setBlackScore(81);

        holesList.get(8).setKorgools(1);
        holesList.get(9).setKorgools(1);

        assertTrue(game.move(9,Side.WHITE));

        String draw = "It's a draw";
        String winner = game.getWinner(Side.WHITE);
        assertEquals(81, gameBoard.getWhiteScore());
        assertEquals(81, gameBoard.getBlackScore());
        assertEquals(draw, winner);

    }

    @Test
    public void gameEndsWhenPlayerCantMakeMoveOnThierTurn() {
        Game game = new Game();
        GameBoard gameBoard = game.getGameBoard();
        ArrayList<Hole> holesList = gameBoard.getHoles();

        gameBoard.setWhiteScore(51);
        gameBoard.setBlackScore(62);
        // clear all holes on the white side
        for(int i = 0; i < 9; i++) {
            holesList.get(i).setKorgools(0);
        }
        // make a move that leaves no korgools on the white side
        // should result in the end of the
        assertTrue(game.move(1,Side.BLACK));

        String expected = "You lose...";
        String result = game.getWinner(Side.WHITE);
        assertEquals(expected, result);
        assertEquals(51, gameBoard.getWhiteScore());
        assertEquals(143, gameBoard.getBlackScore());

    }

}
