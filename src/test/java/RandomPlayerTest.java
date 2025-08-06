import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * RandomPlayerTest class
 *
 * @author Abs
 */

public class RandomPlayerTest {

    // Test for constructor
    @Test
    public void constructWhiteRandomPlayer() {
        Player player = new RandomPlayer(Side.WHITE);
        assertEquals(Side.WHITE, player.getSideColor());
    }

    // Test for constructor
    @Test
    public void constructBlackRandomPlayer() {
        Player player = new RandomPlayer(Side.BLACK);
        assertEquals(Side.BLACK, player.getSideColor());
    }

    // Running move method does not change side.
    @Test
    public void movingDoesNotChangeSide() {
        Player playerBlack = new RandomPlayer(Side.BLACK);
        Player playerWhite = new RandomPlayer(Side.WHITE);
        GameBoard defaultBoard = new GameBoard();
        for(int i = 0; i < 1000; i++){
            playerBlack.move(defaultBoard.getHoles());
            playerWhite.move(defaultBoard.getHoles());
            assertEquals(Side.BLACK, playerBlack.getSideColor());
            assertEquals(Side.WHITE, playerWhite.getSideColor());
        }

    }

    // availableMove Returns all values [0,8] for White RandomPlayer, with a Default Board
    @Test
    public void availableMoveMethodReturnsExpectedValuesWhitePlayer(){
        Player playerW = new  RandomPlayer(Side.WHITE);
        GameBoard defaultBoard = new GameBoard();
        ArrayList<Integer> expectedValues = new ArrayList<>(9);
        for(int i = 0; i < 9; i++){
            expectedValues.add(i);
        }

        ArrayList<Integer> availableMoves = playerW.availableMoves(defaultBoard.getHoles());
        for(int i = 0; i < expectedValues.size(); i++){
            assertEquals(expectedValues.get(i), availableMoves.get(i));
        }
    }

    // availableMove Returns all values [9, 17] for Black RandomPlayer, with a Default Board
    @Test
    public void availableMoveMethodReturnsExpectedValuesBlackPlayer(){
        Player playerB = new  RandomPlayer(Side.BLACK);
        GameBoard defaultBoard = new GameBoard();
        ArrayList<Integer> expectedValues = new ArrayList<>(9);
        for(int i = 9; i < 18; i++){
            expectedValues.add(i);
        }

        ArrayList<Integer> availableMoves = playerB.availableMoves(defaultBoard.getHoles());
        for(int i = 0; i < expectedValues.size(); i++){
            assertEquals(expectedValues.get(i), availableMoves.get(i));
        }
    }

    // move method for a RandomPlayer on White side returns a number between [0, 8] for a default board, and all values are returned at least once in 10,000 tries iterations
    @Test
    public void whitePlayerMovesReturnsBetweenZeroAndEight(){
        Player player = new RandomPlayer(Side.WHITE);
        GameBoard defaultBoard = new GameBoard();
        TreeSet<Integer> moveValues = new TreeSet<>();
        for(int i = 0; i < 10000; i++){
            int move = player.move(defaultBoard.getHoles());
            assertTrue(move >= 0);
            assertTrue(move <= 8);
            moveValues.add(move);
        }


        Object[] toCheck = moveValues.toArray();
        ArrayList<Integer> expectedValues = new ArrayList<>(9);
        for(int i = 0; i < 9; i++){
            expectedValues.add(i);
        }
        for(int i = 0; i < 9; i++){
            assertEquals(toCheck[i], expectedValues.get(i));
        }
    }

    // move method for a RandomPlayer on Black side returns a number between [9, 17] for a default board, and all values are returned at least once in 10,000 tries iterations
    @Test
    public void blackPlayerMovesReturnsBetweenNineAndSeventeen(){
        Player player = new RandomPlayer(Side.BLACK);
        GameBoard defaultBoard = new GameBoard();
        TreeSet<Integer> moveValues = new TreeSet<>();
        for(int i = 0; i < 10000; i++){
            int move = player.move(defaultBoard.getHoles());
            assertTrue(move >= 9);
            assertTrue(move <= 17);
            moveValues.add(move);
        }

        Object[] toCheck = moveValues.toArray();
        ArrayList<Integer> expectedValues = new ArrayList<>(9);
        for(int i = 9; i < 18; i++){
            expectedValues.add(i);
        }
        for(int i = 0; i < 9; i++){
            assertEquals(toCheck[i], expectedValues.get(i));
        }
    }

    // move method for a RandomPlayer on White side returns a number between [0, 8] for a custom board in which some moves are favoured, and all values are returned at least once in 10,000 tries iterations
    @Test
    public void whitePlayerMovesReturnsBetweenZeroAndEightCustomBoard(){
        Player player = new RandomPlayer(Side.WHITE);
        GameBoard customBoard = new GameBoard();
        customBoard.getHoles().get(0).setKorgools(13);
        customBoard.getHoles().get(4).setKorgools(7);
        customBoard.getHoles().get(13).setKorgools(5);
        customBoard.getHoles().get(13).setKorgools(55);
        TreeSet<Integer> moveValues = new TreeSet<>();
        for(int i = 0; i < 10000; i++){
            int move = player.move(customBoard.getHoles());
            assertTrue(move >= 0);
            assertTrue(move <= 8);
            moveValues.add(move);
        }


        Object[] toCheck = moveValues.toArray();
        ArrayList<Integer> expectedValues = new ArrayList<>(9);
        for(int i = 0; i < 9; i++){
            expectedValues.add(i);
        }
        for(int i = 0; i < 9; i++){
            assertEquals(toCheck[i], expectedValues.get(i));
        }
    }

    // move method for a RandomPlayer on Black side returns a number between [9, 17] for a custom board in which some moves are favoured, and all values are returned at least once in 10,000 tries iterations
    @Test
    public void blackPlayerMovesReturnsBetweenNineAndSeventeenCustomBoard(){
        Player player = new RandomPlayer(Side.BLACK);
        GameBoard customBoard = new GameBoard();
        TreeSet<Integer> moveValues = new TreeSet<>();
        customBoard.getHoles().get(0).setKorgools(13);
        customBoard.getHoles().get(4).setKorgools(7);
        customBoard.getHoles().get(13).setKorgools(5);
        customBoard.getHoles().get(13).setKorgools(55);
        for(int i = 0; i < 10000; i++){
            int move = player.move(customBoard.getHoles());
            assertTrue(move >= 9);
            assertTrue(move <= 17);
            moveValues.add(move);
        }

        Object[] toCheck = moveValues.toArray();
        ArrayList<Integer> expectedValues = new ArrayList<>(9);
        for(int i = 9; i < 18; i++){
            expectedValues.add(i);
        }
        for(int i = 0; i < 9; i++){
            assertEquals(toCheck[i], expectedValues.get(i));
        }
    }

    //move method for a RandomPlayer returns -1 when no moves are available
    @Test
    public void NoMovesAvailableReturnsMinus1(){
        Player playerB = new RandomPlayer(Side.BLACK);
        Player playerW = new RandomPlayer(Side.WHITE);
        GameBoard emptyBoard = new GameBoard();
        for(int i = 0; i < 18; i++){
            emptyBoard.getHoles().get(i).setKorgools(0);
        }
        int bMove = playerB.move(emptyBoard.getHoles());
        int wMove = playerW.move(emptyBoard.getHoles());
        boolean isEmptyB = playerB.availableMoves(emptyBoard.getHoles()).isEmpty();
        boolean isEmptyW = playerB.availableMoves(emptyBoard.getHoles()).isEmpty();

        assertTrue(isEmptyB);
        assertTrue(isEmptyW);
        assertEquals(-1, bMove);
        assertEquals(-1, wMove);
    }
}
