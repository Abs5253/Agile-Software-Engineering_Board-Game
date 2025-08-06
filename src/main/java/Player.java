import java.util.ArrayList;

/**
 * Player Abstract Class for Toguz Korgool.
 * This class will keep the color of the player, and will implement a method which helps for any subclasses move methods
 *
 * @author Abs
 */

public abstract class Player {
    public static final int BOARD_HOLES = 18;

    private Side side;

    /**
     * Constructs a player, storing the side of the player.
     * @param side - the side the player will play from
     */
    public Player(Side side){
        this.side = side;
    }

    /**
     * Calculate and return the index of the hole from which to play the move
     * If no move is possible, return -1
     * @param board - the List containing the current state of the board and all the holes within
     * @return the index of the Hole to select for the move, and if no move is possible, return -1.
     */
    public abstract int move(ArrayList<Hole> board);

    /**
     * Calculate and return a list of all the possible moves this player has to play.
     * These moves will be the index of all the holes available.
     * This will consider the side of the player, and will return appropriate indexes
     * @param board - the List containing the current state of the board and all the holes within
     * @return a list of all possible moves this player has to play.
     */
    protected ArrayList<Integer> availableMoves(ArrayList<Hole> board){
        int sideBeginIndex = 0;
        if(side == Side.BLACK){
            sideBeginIndex = BOARD_HOLES/2;
        }

        ArrayList<Integer> availableMoves = new ArrayList<>();

        for(int i = sideBeginIndex; i < sideBeginIndex + BOARD_HOLES/2; i++){
            if(board.get(i).getKorgools() > 0){//meaning at least one move can be played
                availableMoves.add(i);
            }
        }

        return availableMoves;
    }

    /**
     * Return the color of the side the Player is on
     * @return the color of the Player's side
     */
    public Side getSideColor(){
        return side;
    }
}
