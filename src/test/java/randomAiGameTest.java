import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class randomAiGameTest {

    @Test
    public void checkAiPlayerMoves() {
        Game game = new Game("Easy", Side.BLACK);
        GameBoard board = game.getGameBoard();
        assertFalse(game.move(1,Side.WHITE));
        int whiteAfterPlayerMove = 0;
        for(int i = 0; i < 9; i++) {
            whiteAfterPlayerMove += board.getHoles().get(i).getKorgools();
        }
        int blackAfterPlayerMove = 0;
        for(int i = 9; i < 17; i++) {
            blackAfterPlayerMove += board.getHoles().get(i).getKorgools();
        }
        assertFalse(game.moveAutomatedOpponent());
        int whiteAfterAiMove = 0;
        for(int i = 0; i < 9; i++) {
            whiteAfterAiMove += board.getHoles().get(i).getKorgools();
        }
        int blackAfterAiMove = 0;
        for(int i = 9; i < 17; i++) {
            blackAfterAiMove += board.getHoles().get(i).getKorgools();
        }

        assertFalse(whiteAfterPlayerMove == whiteAfterAiMove);
        assertFalse(blackAfterPlayerMove == blackAfterAiMove);

    }
}
