import java.util.ArrayList;
/**
 * AIPlayer class for Toguz Korgool.
 *
 * @author Abs
 */

public class AIPlayer extends Player {

    AIPlayer(Side side){
        super(side);
    }


    /**
     * Calculates and returns the move that will set a Tuz if possible, else yield the highest number of Korgools going into the players Kazan.
     * If no move is possible, return -1
     * If there are some possible moves, but none yield any Kargools, return the first of the possible moves
     * @param board - a List containing all the holes of the board
     * @return the number of the Hole to select for the move, which will set a Tuz if possible, or be the first best current move available, or -1 if no moves available.
     */
    public int move(ArrayList<Hole> board){
        ArrayList<Integer> availableMoves = availableMoves(board);
        if(availableMoves.isEmpty()){ //No moves available
            return -1;
        }

        int bestMove = availableMoves.get(0);
        int bestMoveKargools = 0;

        for(Integer move: availableMoves){//go through all the available moves.
            int holesForward = board.get(move).getKorgools() - 1; //How many holes this move will jump
            if(holesForward == 0){ //if the number of Korgools was 1,
                holesForward = 1; //then it will go forward one move (not 0), according to the rules
            }

            int resultIndex = (move + holesForward) % BOARD_HOLES;
            Hole resultingHole = board.get(resultIndex); //Get the resulting hole if this move were played

            if(resultingHole.getSide() != getSideColor()){ //Only continue if the whole is not on this players side
                if(resultingHole.canBeTuz() && resultingHole.getKorgools() == 2){ //If the Korgools were previously (would result in 3, making a Tuz)
                    return move;
                }

                if((resultingHole.getKorgools() % 2) == 1){ //If the hole's Kargools are odd in number (thus will be even if move happens)
                    if(resultingHole.getKorgools() > bestMoveKargools){ //If this hole will yield more kargools than the previous bestMove would yield
                        bestMove = move;
                        bestMoveKargools = resultingHole.getKorgools();

                    }
                }
            }
        }
        return bestMove;
    }
}