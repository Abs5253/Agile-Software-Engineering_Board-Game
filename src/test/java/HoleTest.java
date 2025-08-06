import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Test class for the Hole Class.
 *
 * @author Akshat
 */

public class HoleTest {

  @Test
  public void defaultHoleValuesAreCorrect() {
    Hole hole1 = new Hole(1, Side.WHITE);
    assertEquals(1, hole1.getNumber());
    assertEquals(9, hole1.getKorgools());
    assertEquals(Side.WHITE, hole1.getSide());
    assertEquals(false, hole1.isTuz());
    assertEquals(true, hole1.canBeTuz());

    Hole hole2 = new Hole(7, Side.BLACK);
    assertEquals(7, hole2.getNumber());
    assertEquals(9, hole2.getKorgools());
    assertEquals(Side.BLACK, hole2.getSide());
    assertEquals(false, hole2.isTuz());
    assertEquals(true, hole2.canBeTuz());
  }

  @Test
  public void loadHoleValuesAreCorrect() {
    Hole hole1 = new Hole(8, Side.WHITE, 18, true);
    assertEquals(8, hole1.getNumber());
    assertEquals(18, hole1.getKorgools());
    assertEquals(Side.WHITE, hole1.getSide());
    assertEquals(true, hole1.isTuz());
    assertEquals(false, hole1.canBeTuz());

    Hole hole2 = new Hole(9, Side.BLACK, 5, false);
    assertEquals(9, hole2.getNumber());
    assertEquals(5, hole2.getKorgools());
    assertEquals(Side.BLACK, hole2.getSide());
    assertEquals(false, hole2.isTuz());
    assertEquals(false, hole2.canBeTuz());
  }

  @Test
  public void makeTuzReturnsExpectedValue() {
    Hole hole1 = new Hole(6, Side.WHITE, 3, false);
    boolean check = hole1.makeTuz();
    assertEquals(true, check);
    assertEquals(true, hole1.isTuz());
    assertEquals(false, hole1.canBeTuz());

    Hole hole2 = new Hole(9, Side.BLACK, 3, false);
    check = hole2.makeTuz();
    assertEquals(false, check);
    assertEquals(false, hole2.isTuz());
    assertEquals(false, hole2.canBeTuz());

    Hole hole3 = new Hole(7, Side.WHITE, 19, false);
    check = hole3.makeTuz();
    assertEquals(false, check);
    assertEquals(false, hole3.isTuz());
    assertEquals(true, hole3.canBeTuz());

    Hole hole4 = new Hole(2, Side.BLACK, 7, true);
    check = hole4.makeTuz();
    assertEquals(false, check);
    assertEquals(true, hole4.isTuz());
    assertEquals(false, hole4.canBeTuz());
  }

  @Test
  public void clearHoleReturnsExpectedValue() {
    Hole hole1 = new Hole(6, Side.WHITE, 19, false);
    hole1.increment();
    assertEquals(20, hole1.clearHole());
    }
}
