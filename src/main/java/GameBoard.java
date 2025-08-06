import java.util.ArrayList;
import java.io.*;
/**
 * GameBoard class for Toguz Korgool.
 *
 * @author David
 */

public class GameBoard implements java.io.Serializable {

    public static final int BOARD_HOLES = 18;

    private ArrayList<Hole> holes;
    private int blackKazan;
    private int whiteKazan;
    private boolean blackTuzSet;
    private boolean whiteTuzSet;

    /**
     * Constructs a GameBoard for the begining of a new game.
     * White holes are placed at index 0-8 and black from 9-17.
     */
    public GameBoard() {
        this.holes = new ArrayList<>(BOARD_HOLES);
        // initial score is 0
        this.blackKazan = 0;
        this.whiteKazan = 0;
        // no tuz is set at the start
        this.blackTuzSet = false;
        this.whiteTuzSet = false;

        // create the holes for the white side of the board
        for(int i = 1; i <= BOARD_HOLES/2; i++) {
            holes.add(new Hole(i, Side.WHITE));
        }
        // create the holes for the black side of the board
        for(int i = 1; i <= BOARD_HOLES/2; i++) {
            holes.add(new Hole(i, Side.BLACK));
        }
    }

    /**
     * Constructs a GameBoard for the begining of a custom game.
     * White holes are placed at index 0-8 and black from 9-17.
     */
    public GameBoard(int[] whiteHoles, int[] blackHoles, int whiteKazan, int blackKazan) {
        this.holes = new ArrayList(BOARD_HOLES);
        whiteTuzSet = false;
        blackTuzSet = false;

        for (int i = 1; i <= BOARD_HOLES/2; i++) {
          if (whiteHoles[i-1] == -1) {
            holes.add(new Hole(i, Side.WHITE, 0, true));
            whiteTuzSet = true;
          } else {
            holes.add(new Hole(i, Side.WHITE, whiteHoles[i-1], false));
          }
        }

        for (int i = 1; i <= BOARD_HOLES/2; i++) {
          if (blackHoles[i-1] == -1) {
            holes.add(new Hole(i, Side.BLACK, 0, true));
            blackTuzSet = true;
          } else {
            holes.add(new Hole(i, Side.BLACK, blackHoles[i-1], false));
          }
        }

        this.whiteKazan = whiteKazan;
        this.blackKazan = blackKazan;

    }

    // Getter methods
    public int getWhiteScore() {
        return whiteKazan;
    }
    public int getBlackScore() {
        return blackKazan;
    }
    public ArrayList<Hole> getHoles() {
        return holes;
    }
    public Hole getHole(int index) {
        return holes.get(index);
    }
    public boolean isWhiteTuzSet() {
        return whiteTuzSet;
    }

    public boolean isBlackTuzSet() {
        return blackTuzSet;
    }

    // Setter methods
    private void setPlayerTuz(Side side) {
        if(side == Side.WHITE) {
            this.whiteTuzSet = true;
        } else {
            this.blackTuzSet = true;
        }
    }
    /**
     * @param score - The value to set in the white kazan
     */
    public void setWhiteScore(int score) {
        if(score > 0) {
            whiteKazan = score;
        }
    }

    /**
     * @param score - The value to set in the black kazan
     */
    public void setBlackScore(int score) {
        if(score > 0) {
            blackKazan = score;
        }
    }

    /**
     * @param score - The value to increase the white kazan by
     */
    public void increaseWhiteScore(int score) {
        if(score > 0) {
            whiteKazan += score;
        }
    }

    /**
     * @param score - The value to increase the black kazan by
     */
    public void increaseBlackScore(int score) {
        if(score > 0) {
            blackKazan += score;
        }
    }

    /**
     * Attempts to set a hole on the board as tuz for this player. If
     * this hole can be a tuz, then the opposite hole is no longer allowed
     * to be a Tuz.
     * @param hole - The hole to be set as tuz
     * @param side - What player is trying to set their Tuz
     * @return - true if successful and false if unable to set as Tuz
     */
    public boolean setHoleAsTuz(int hole, Side side) {
        // index will be 1 less than the hole number
        int holeIndex;
        // check if the tuz has been set already for this player
        if(side == Side.WHITE) {
            if(whiteTuzSet){
                return false;
            }
            holeIndex = hole - 1;
        } else {
            if(blackTuzSet) {
                return false;
            }
            // black hole index half the number of holes bigger than hole number
            holeIndex = hole + (BOARD_HOLES/2) - 1;
        }
        if(holes.get(holeIndex).makeTuz()) {
            // opposite hole can't be tuz
            holes.get((holeIndex + (BOARD_HOLES/2)) % BOARD_HOLES).cantBeTuz();
            // record that this player's tuz is now set
            setPlayerTuz(side);
            // successful
            return true;
        } else {
            // this hole cannot be tuz
            return false;
        }
    }

    /**
     * @return - A comma seperated string representing the state of the board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // First 9 will be the white holes
        for(Hole hole : holes) {
            if (hole.getNumber() == 1) {
                sb.append("\n");
                sb.append("" + hole.getSide() + " holes:\n");
            }
            sb.append(hole.getNumber());
            sb.append(",");
            sb.append(hole.getKorgools());
            sb.append(",");
            sb.append(hole.isTuz());
            sb.append(",");
            sb.append(hole.canBeTuz());
            sb.append(",");
        }
        sb.append("\nScore (kazan) - white then black:\n");
        sb.append(whiteKazan);
        sb.append(",");
        sb.append(blackKazan);
        sb.append("\nTuz set - white then black:\n");
        sb.append(whiteTuzSet);
        sb.append(",");
        sb.append(blackTuzSet);

        return sb.toString();
    }
}
