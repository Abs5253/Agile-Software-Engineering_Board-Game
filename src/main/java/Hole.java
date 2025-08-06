import java.io.*;

/**
 * Hole class for Toguz Korgool.
 *
 * @author Akshat
 */

public class Hole implements java.io.Serializable {
  private int number;
  private int korgools;
  private Side side;
  private boolean isTuz;
  private boolean canBeTuz;
  private final int CANT_BE_TUZ_NUMBER = 9;
  private final int CAN_BE_TUZ_KARGOOLS = 3;

  // Creates a default hole
  public Hole (int number, Side side) {
    this.number = number;
    this.side = side;
    this.isTuz = false;
    this.korgools = 9;
    this.canBeTuz = (number == CANT_BE_TUZ_NUMBER) ? false : true;
  }

  // Creates a hole with the given parameters
  public Hole(int number, Side side, int korgools, boolean isTuz) {
    this.number = number;
    this.side = side;
    this.isTuz = isTuz;
    this.korgools = korgools;
    this.canBeTuz = (isTuz || number == CANT_BE_TUZ_NUMBER) ? false : true;
  }

  // Gets the current hole number
  public int getNumber() {
    return number;
  }

  // Gets the number of Korgools in the current Hole
  public int getKorgools() {
    return korgools;
  }
  
  // Gets the side which the hole is on
  public Side getSide() {
    return side;
  }

  // Gets whether or not the current hole is a Tuz
  public boolean isTuz() {
    return isTuz;
  }

  // Gets whether the current hole can be a Tuz
  public boolean canBeTuz() {
    return canBeTuz;
  }

  // Sets the number of Korgools for the current hole
  public void setKorgools(int korgools) {
    this.korgools = korgools;
  }

  // Sets whether the current hole can be set to a Tuz
  public void cantBeTuz() {
    //TODO: Change name of the method to setCantBeTuz()
    canBeTuz = false;
  }

  /**
   *  Makes the current Hole a Tuz if possible
   *  @return true if the Hole could successfully be made a Tuz, false otherwise
   */
  public boolean makeTuz() {
    if (canBeTuz && korgools == CAN_BE_TUZ_KARGOOLS) {
      isTuz = true;
      canBeTuz = false;
      return true;
    }
    return false;
  }
  
  // Increments the number of Korgools
  public void increment() {
    ++korgools;
  }

  /**
   *  Remove the all the Korgools of the Hole
   *  @return the number of korgools which were in the hole before clearing it
   */
  public int clearHole() {
    int temp = korgools;
    korgools = 0;
    return temp;
  }
}
