import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

/**
 * AIPlayerTest class
 * @author Abs
 */

public class AIPlayerTest {

    // Test for constructor
    @Test
    public void constructWhiteAIPlayer() {
        Player player = new AIPlayer(Side.WHITE);
        assertEquals(Side.WHITE, player.getSideColor());
    }

    // Test for constructor
    @Test
    public void constructBlackAIPlayer() {
        Player player = new AIPlayer(Side.BLACK);
        assertEquals(Side.BLACK, player.getSideColor());
    }

    // Running move method does not change side.
    @Test
    public void movingDoesNotChangeSide() {
        Player playerW = new AIPlayer(Side.WHITE);
        Player playerB = new AIPlayer(Side.BLACK);
        GameBoard defaultBoard = new GameBoard();
        playerW.move(defaultBoard.getHoles());
        playerB.move(defaultBoard.getHoles());
        assertEquals(Side.WHITE, playerW.getSideColor());
        assertEquals(Side.BLACK, playerB.getSideColor());
    }

    // availableMove returns all values [0,8] for White AIPlayer, with a Default Board
    @Test
    public void availableMoveMethodReturnsExpectedValuesWhitePlayer(){
        Player playerW = new AIPlayer(Side.WHITE);
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

    // availableMove returns all values [9, 17] for Black AIPlayer, with a Default Board
    @Test
    public void availableMoveMethodReturnsExpectedValuesBlackPlayer(){
        Player playerB = new AIPlayer(Side.BLACK);
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

    // move method for an AIPlayer on White side returns 1 for a default board, every time
    @Test
    public void whitePlayerMovesReturnsOne(){
        Player player = new AIPlayer(Side.WHITE);
        GameBoard defaultBoard = new GameBoard();
        for(int i = 0; i < 10; i++){
            int move = player.move(defaultBoard.getHoles());
            assertEquals(1, move);
        }
    }

    // move method for an AIPlayer on Black side returns 10 for a default board, every time
    @Test
    public void blackPlayerMovesReturnsTen(){
        Player player = new AIPlayer(Side.BLACK);
        GameBoard defaultBoard = new GameBoard();
        for(int i = 0; i < 10; i++){
            int move = player.move(defaultBoard.getHoles());
            assertEquals(10, move);
        }
    }

    //move method for an AIPlayer on White Side returns 5 for a custom board (in which playing 5 would be best option), every time
    @Test
    public void whitePlayerMoveReturnsFive(){
        Player player = new AIPlayer(Side.WHITE);
        GameBoard customBoard = new GameBoard();
        customBoard.getHoles().get(13).setKorgools(101);
        for(int i = 0; i < 10; i++){
            int move = player.move(customBoard.getHoles());
            assertEquals(5, move);
        }
    }

    //move method for an AIPlayer on Black Side returns 15 for a custom board (in which playing 15 would be best option), every time
    @Test
    public void blackPlayerMoveReturnsFifteen(){
        Player player = new AIPlayer(Side.BLACK);
        GameBoard customBoard = new GameBoard();
        customBoard.getHoles().get(5).setKorgools(101);
        for(int i = 0; i < 10; i++){
            int move = player.move(customBoard.getHoles());
            assertEquals(15, move);
        }
    }

    //move method for an AIPlayer returns -1 when no moves are available
    @Test
    public void noMovesAvailableReturnsMinus1(){
        Player playerB = new AIPlayer(Side.BLACK);
        Player playerW = new AIPlayer(Side.WHITE);
        GameBoard emptyBoard = new GameBoard();
        for(int i = 0; i < 18; i++){
            emptyBoard.getHoles().get(i).setKorgools(0);
        }
        int bMove = playerB.move(emptyBoard.getHoles());
        int wMove = playerW.move(emptyBoard.getHoles());

        assertEquals(-1, bMove);
        assertEquals(-1, wMove);
    }

    //move method returns first available move, which should be 0, when no moves yield any Kargools, for White Player
    @Test
    public void noYieldsReturnsFirstAvailableMoveWhite(){
        Player player = new AIPlayer(Side.WHITE);
        GameBoard noPointsBoard = new GameBoard();
        for(int i = 9; i < 18; i++){
            noPointsBoard.getHoles().get(i).setKorgools(0);
        }
        int move = player.move(noPointsBoard.getHoles());
        int firstAvailableMove = player.availableMoves(noPointsBoard.getHoles()).get(0);

        assertEquals(0, move, firstAvailableMove);
    }

    //move method returns first available move, which should be 9 when no moves yield any Kargools, for Black Player
    @Test
    public void noYieldsReturnsFirstAvailableMoveBlack(){
        Player player = new AIPlayer(Side.BLACK);
        GameBoard noPointsBoard = new GameBoard();
        for(int i = 0; i < 9; i++){
            noPointsBoard.getHoles().get(i).setKorgools(0);
        }
        int move = player.move(noPointsBoard.getHoles());
        int firstAvailableMove = player.availableMoves(noPointsBoard.getHoles()).get(0);

        assertEquals(9, move, firstAvailableMove);
    }

    //move method for an AIPlayer on White Side returns 3 for a custom board (in which playing 3 would be the first best option), every time
    //other moves are equally good in terms of yielding Kargools, but 3 is the first of these
    @Test
    public void whitePlayerMoveReturns3(){
        Player player = new AIPlayer(Side.WHITE);
        GameBoard customBoard = new GameBoard();
        customBoard.getHoles().get(11).setKorgools(101);
        customBoard.getHoles().get(12).setKorgools(101);
        customBoard.getHoles().get(15).setKorgools(101);
        for(int i = 0; i < 10; i++){
            int move = player.move(customBoard.getHoles());
            assertEquals(3, move);
        }
    }

    //move method for an AIPlayer on Black Side returns 13 for a custom board (in which playing 13 would be the first best option), every time
    //other moves are equally good in terms of yielding Kargools, but 13 is the first of these
    @Test
    public void blackPlayerMoveReturns15(){
        Player player = new AIPlayer(Side.BLACK);
        GameBoard customBoard = new GameBoard();
        customBoard.getHoles().get(3).setKorgools(101);
        customBoard.getHoles().get(4).setKorgools(101);
        customBoard.getHoles().get(7).setKorgools(101);
        for(int i = 0; i < 10; i++){
            int move = player.move(customBoard.getHoles());
            assertEquals(13, move);
        }
    }

    //move method for an AIPlayer on White Side returns 4 for a custom board (in which playing 4 would be the first best option), every time
    @Test
    public void whitePlayerMoveReturns4(){
        Player player = new AIPlayer(Side.WHITE);
        GameBoard customBoard = new GameBoard();
        customBoard.getHoles().get(12).setKorgools(101);
        customBoard.getHoles().get(9).setKorgools(99);
        customBoard.getHoles().get(10).setKorgools(99);
        customBoard.getHoles().get(15).setKorgools(200);
        customBoard.getHoles().get(17).setKorgools(240);
        for(int i = 0; i < 10; i++){
            int move = player.move(customBoard.getHoles());
            assertEquals(4, move);
        }
    }

    //move method for an AIPlayer on Black Side returns 14 for a custom board (in which playing 14 would be the first best option), every time
    @Test
    public void blackPlayerMoveReturns14(){
        Player player = new AIPlayer(Side.BLACK);
        GameBoard customBoard = new GameBoard();
        customBoard.getHoles().get(4).setKorgools(101);
        customBoard.getHoles().get(3).setKorgools(200);
        customBoard.getHoles().get(7).setKorgools(99);
        customBoard.getHoles().get(8).setKorgools(99);
        customBoard.getHoles().get(0).setKorgools(1000);
        for(int i = 0; i < 10; i++){
            int move = player.move(customBoard.getHoles());
            assertEquals(14, move);
        }
    }
}
