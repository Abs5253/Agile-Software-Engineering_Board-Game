import static org.junit.Assert.*;
import org.junit.Test;
/**
 * GameBoardTest class
 *
 * @author David
 */

public class GameBoardTest {

    // constructor tests
    @Test
    public void constructStartingGameBoard() {
        GameBoard board = new GameBoard();

        assertEquals(0, board.getWhiteScore());
        assertEquals(0, board.getBlackScore());
        assertEquals(false, board.isWhiteTuzSet());
        assertEquals(false, board.isBlackTuzSet());
        assertEquals(18, board.getHoles().size());
    }

    // increaseWhiteScore and increaseBlackScore tests
    @Test
    public void increaseWhiteScoreByNegativeOr0ShouldNotChangeScore() {
        GameBoard board = new GameBoard();

        board.increaseWhiteScore(-1);
        assertEquals(0, board.getWhiteScore());
        board.increaseWhiteScore(-3);
        assertEquals(0, board.getWhiteScore());
        board.increaseWhiteScore(-100);
        assertEquals(0, board.getWhiteScore());

        board.increaseWhiteScore(0);
        assertEquals(0, board.getWhiteScore());
    }

    @Test
    public void increaseBlackScoreByNegativeOr0ShouldNotChangeScore() {
        GameBoard board = new GameBoard();

        board.increaseBlackScore(-1);
        assertEquals(0, board.getBlackScore());
        board.increaseBlackScore(-3);
        assertEquals(0, board.getBlackScore());
        board.increaseBlackScore(-100);
        assertEquals(0, board.getBlackScore());

        board.increaseBlackScore(0);
        assertEquals(0, board.getBlackScore());
    }

    @Test
    public void increaseWhiteScoreByPositiveIntChangesScore() {
        GameBoard board = new GameBoard();

        board.increaseWhiteScore(1);
        assertEquals(1, board.getWhiteScore());
        board.increaseWhiteScore(5);
        assertEquals(6, board.getWhiteScore());
        board.increaseWhiteScore(10);
        assertEquals(16, board.getWhiteScore());
        board.increaseWhiteScore(100);
        assertEquals(116, board.getWhiteScore());
    }

    @Test
    public void increaseBlackScoreByPositiveIntChangesScore() {
        GameBoard board = new GameBoard();

        board.increaseBlackScore(1);
        assertEquals(1, board.getBlackScore());
        board.increaseBlackScore(5);
        assertEquals(6, board.getBlackScore());
        board.increaseBlackScore(10);
        assertEquals(16, board.getBlackScore());
        board.increaseBlackScore(100);
        assertEquals(116, board.getBlackScore());
    }

    // setPlayerTuz tests
    @Test
    public void setHoleAsTuzReturnsTrueWhenTuzCanBeSet() {
        GameBoard board = new GameBoard();
        // white player hole 1
        board.getHoles().get(0).setKorgools(3);
        // black player hole 2
        board.getHoles().get(10).setKorgools(3);

        assertEquals(true, board.setHoleAsTuz(1, Side.WHITE));

        assertEquals(true, board.setHoleAsTuz(2, Side.BLACK));
    }

    @Test
    public void setHoleAsTuzReturnsFalseWhenTuzCannotBeSet() {
        GameBoard board = new GameBoard();
        // holes can only be set as tuz when kogool in hole = 3
        // all holes should have kogool != 3 after constructor

        assertEquals(false, board.setHoleAsTuz(1, Side.WHITE));
        assertEquals(false, board.setHoleAsTuz(2, Side.BLACK));

        // hole 9 cannot be tuz
        assertEquals(false, board.setHoleAsTuz(9, Side.WHITE));
        assertEquals(false, board.setHoleAsTuz(9, Side.BLACK));
    }

    @Test
    public void setHoleAsTuzReturnsFalseWhenTuzAlreadySet() {
        GameBoard board = new GameBoard();
        // white player hole 1
        board.getHoles().get(0).setKorgools(3);
        // this should mean no other white hole can be tuz
        board.setHoleAsTuz(1, Side.WHITE);

        assertEquals(false, board.setHoleAsTuz(1, Side.WHITE));
        assertEquals(false, board.setHoleAsTuz(3, Side.WHITE));
        assertEquals(false, board.setHoleAsTuz(6, Side.WHITE));
        assertEquals(false, board.setHoleAsTuz(8, Side.WHITE));

        // black player hole 2
        board.getHoles().get(10).setKorgools(3);
        // this should mean white hole 2 cannot be set as tuz
        // and that no other black hole can be tuz
        board.setHoleAsTuz(2, Side.BLACK);

        assertEquals(false, board.setHoleAsTuz(2, Side.BLACK));
        assertEquals(false, board.setHoleAsTuz(3, Side.BLACK));
        assertEquals(false, board.setHoleAsTuz(6, Side.BLACK));
        assertEquals(false, board.setHoleAsTuz(8, Side.BLACK));
    }

    @Test
    public void setHoleAsTuzReturnsFalseWhenOpositeTuzSet() {
        GameBoard board = new GameBoard();
        // white player hole 1
        board.getHoles().get(0).setKorgools(3);
        // this should mean black hole 1 cannot be set as tuz
        board.setHoleAsTuz(1, Side.WHITE);

        assertEquals(false, board.setHoleAsTuz(1, Side.BLACK));

        // black player hole 2
        board.getHoles().get(10).setKorgools(3);
        // this should mean white hole 2 cannot be set as tuz
        board.setHoleAsTuz(2, Side.BLACK);

        assertEquals(false, board.setHoleAsTuz(2, Side.WHITE));
    }

    @Test
    public void toStringReturnsExpectedValuse() {
        GameBoard board = new GameBoard();

        String expected = "\nWHITE holes:\n"+
                          "1,9,false,true,2,9,false,true,3,9,false,true,4,9,false,true,5,9,false,true,6,9,false,true,7,9,false,true,8,9,false,true,9,9,false,false," +
                          "\nBLACK holes:\n"+
                          "1,9,false,true,2,9,false,true,3,9,false,true,4,9,false,true,5,9,false,true,6,9,false,true,7,9,false,true,8,9,false,true,9,9,false,false," +
                          "\nScore (kazan) - white then black:\n"+
                          "0,0"+
                          "\nTuz set - white then black:\n"+
                          "false,false";

        assertEquals(expected, board.toString());

        board.setWhiteScore(20);
        board.getHoles().get(2).setKorgools(3);
        board.setHoleAsTuz(3, Side.WHITE);

        String expected2 = "\nWHITE holes:\n"+
                           "1,9,false,true,2,9,false,true,3,3,true,false,4,9,false,true,5,9,false,true,6,9,false,true,7,9,false,true,8,9,false,true,9,9,false,false," +
                           "\nBLACK holes:\n"+
                           "1,9,false,true,2,9,false,true,3,9,false,false,4,9,false,true,5,9,false,true,6,9,false,true,7,9,false,true,8,9,false,true,9,9,false,false," +
                           "\nScore (kazan) - white then black:\n"+
                           "20,0"+
                           "\nTuz set - white then black:\n"+
                           "true,false";

        assertEquals(expected2, board.toString());
    }
}
