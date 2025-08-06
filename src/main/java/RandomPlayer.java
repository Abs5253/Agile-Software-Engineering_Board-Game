import java.util.ArrayList;
/**
 * RandomPlayer class for Toguz Korgool.
 * This player will play a random move: any move that it can play according to the rules.
 * It will not attempt to purposefully set a Tuz, even if available
 *
 * @author Abs
 */

public class RandomPlayer extends Player {

    public RandomPlayer(Side side){
        super(side);
    }

    public int move(ArrayList<Hole> board) {
        ArrayList<Integer> availableMoves = availableMoves(board);

        if(availableMoves.size() == 0){//If there are no available moves
            return -1;
        }
        else {//Select a random move from the moves available
            int randIndex = (int)(Math.random() * availableMoves.size());
            return availableMoves.get(randIndex);
        }
    }
}
